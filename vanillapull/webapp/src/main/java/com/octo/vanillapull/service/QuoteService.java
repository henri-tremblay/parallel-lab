package com.octo.vanillapull.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.octo.vanillapull.repository.QuoteDao;
import com.octo.vanillapull.model.Quote;
import com.octo.vanillapull.shared.QuoteDisplay;

public class QuoteService {

	@Autowired
	private QuoteDao quoteDao;

	public List<QuoteDisplay> getQuoteList() {
		// Retrieve quotes instrument after instrument
		List<QuoteDisplay> returnList = new ArrayList<QuoteDisplay>(40);
		List<Quote> quotes = quoteDao.getLastQuoteList();
		for (Quote quote1 : quotes) {

			// Build quote display
			QuoteDisplay quoteDisplay = new QuoteDisplay();
			quoteDisplay.setTicker(quote1.getTicker());
			quoteDisplay.setSymbol(quote1.getSymbol());
			quoteDisplay.setQuoteLow(quote1.getQuoteLow());
			quoteDisplay.setQuoteHigh(quote1.getQuoteHigh());
			quoteDisplay.setQuoteOpen(quote1.getQuoteOpen());
			quoteDisplay.setQuoteSpot(quote1.getQuoteSpot());
			quoteDisplay.setRateVolatility(quote1.getRateVolatility());
			quoteDisplay.setRateVariation(0);
			// Add timestamps
			quoteDisplay.setCreatedTs(quote1.getCreateTs());
			quoteDisplay.setReceivedTs(quote1.getReceiveTs());
			quoteDisplay.setLoadedTs(System.currentTimeMillis());
			// Add quote to display list
			returnList.add(quoteDisplay);

		}

		return returnList;
	}
}
