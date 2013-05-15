package com.octo.vanillapull.repository;

import com.octo.vanillapull.model.Parameter;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository("parameterDao")
public class ParameterDao extends GenericDao<String, Parameter> {

  @Cacheable("param")
  @Override
  public Parameter findById(String id) {
    return super.findById(id);
  }
}
