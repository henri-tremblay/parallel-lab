package com.octo.vanillapull.repository;

import com.octo.vanillapull.model.Instrument;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstrumentDao extends GenericDao<String, Instrument> {

	public List<Instrument> findAllOrderedBySymbol() {
		List<Instrument> res = entityManager.createQuery("select h from Instrument h order by h.symbol").getResultList();
		return res;
	}

  @Cacheable("instrument")
  @Override
  public Instrument findById(String id) {
    return super.findById(id);
  }
}
