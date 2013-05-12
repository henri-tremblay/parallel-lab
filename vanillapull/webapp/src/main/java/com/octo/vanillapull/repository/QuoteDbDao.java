package com.octo.vanillapull.repository;

import com.octo.vanillapull.model.Quote;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class QuoteDbDao extends GenericDao<String, Quote> implements QuoteDao {

    @Override
    public List<Quote> getQuoteList(final String symbol) {
        Query query = entityManager
                .createQuery("select q from Quote q where q.symbol=:symbol order by q.ticker desc");
        query.setMaxResults(2);
        query.setParameter("symbol", symbol);

        return query.getResultList();
    }

    @Override
    public List<Quote> getLastQuoteList() {
        Query q = entityManager
                .createQuery("select q from Quote q where q.ticker = (select max(a.ticker) from Quote a where a.symbol = q.symbol)");
        return q.getResultList();
    }
}
