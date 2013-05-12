package com.octo.vanillapull.repository;

import com.octo.vanillapull.model.Quote;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class QuoteMemoryDao implements QuoteDao {

	private final static int NB_QUOTE = 40;

	// No need to concurrent hashmap, put is protected by a lock, it's a treemap to get them ordered
	private final Map<String, List<Quote>> repository = new ConcurrentSkipListMap<String, List<Quote>>();

	// No need to concurrent hashmap, put is protected by a lock
	private final Map<String, Lock> locks = new HashMap<String, Lock>(NB_QUOTE);
	private final Lock globalLock = new ReentrantLock();

	@Override
	public List<Quote> getQuoteList(String symbol) {
		// List is already ordered, later ticker at position 0
		if (this.repository.containsKey(symbol)) {
			return this.repository.get(symbol);
		}
		return new ArrayList<Quote>(0);
	}

	@Override
	public void persist(Quote quote) {
		assert quote != null;

		final String symbol = quote.getSymbol();

		// For performance reason lock only if these is no symbol
		if (!this.repository.containsKey(symbol)) {
			// Prevent adding two locks by two
			try {
				this.globalLock.lock();
				if (!this.repository.containsKey(symbol)) {
					this.repository.put(symbol, new ArrayList<Quote>(2));
					this.locks.put(symbol, new ReentrantLock());
				}
			} finally {
				this.globalLock.unlock();
			}
		}

		Lock l = this.locks.get(symbol);
		try {
			l.lock();
			final List<Quote> qtes = this.repository.get(symbol);
			// Ticker 0 should always be greater (later) than ticker 1
			if (qtes.size() < 2) {
				// Initialize
				qtes.add(quote);
				qtes.add(quote);
			} else {
				final long ticker = quote.getTicker();
				final long ticker1 = qtes.get(1) != null && qtes.get(1).getTicker() != null ? qtes.get(1).getTicker()
						.longValue() : 0;
				final long ticker0 = qtes.get(0) != null && qtes.get(0).getTicker() != null ? qtes.get(0).getTicker()
						.longValue() : 0;
				if (ticker < ticker1) {
					// this quote is too old, forget it
				} else if (ticker1 < ticker && ticker < ticker0) {
					// ticker replace ticker 1
					qtes.set(1, quote);
				} else if (ticker1 < ticker && ticker0 < ticker) {
					// ticker replace ticker 0 and ticker 0 replace ticker 1
					qtes.set(1, qtes.get(0));
					qtes.set(0, quote);
				}
			}
		} finally {
			l.unlock();
		}
		synchronized (this) {
			notifyAll();
		}
	}

	@Override
	public List<Quote> getLastQuoteList() {
		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		List<Quote> result = new ArrayList<Quote>(repository.size());
		for (List<Quote> value : repository.values()) {
			result.add(value.get(0)); // 0 is the most recent one
		}

		return result;
	}
}