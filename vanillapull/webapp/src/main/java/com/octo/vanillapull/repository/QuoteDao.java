package com.octo.vanillapull.repository;

import com.octo.vanillapull.model.Quote;

import java.util.List;

public interface QuoteDao {

	List<Quote> getQuoteList(final String symbol);

	void persist(final Quote quote);

	List<Quote> getLastQuoteList();
}