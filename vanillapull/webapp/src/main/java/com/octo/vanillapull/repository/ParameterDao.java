package com.octo.vanillapull.repository;

import com.octo.vanillapull.model.Parameter;
import org.springframework.stereotype.Repository;

@Repository("parameterDao")
public class ParameterDao extends GenericDao<String, Parameter> {
}
