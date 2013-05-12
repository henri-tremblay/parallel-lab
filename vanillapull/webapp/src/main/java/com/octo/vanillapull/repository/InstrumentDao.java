package com.octo.vanillapull.repository;

import com.octo.vanillapull.model.Instrument;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstrumentDao extends GenericDao<String, Instrument> {

	public List<Instrument> findAllOrderedBySymbol() {
		List<Instrument> res = entityManager.createQuery("select h from Instrument h order by h.symbol").getResultList();
		return res;
	}
}
