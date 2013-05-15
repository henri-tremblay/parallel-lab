package com.octo.vanillapull.util;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author Henri Tremblay
 */
//@Component
public class DbExplorer {

  @Value("${database.url}")
  private String url;

  @PostConstruct
  public void init() {
    org.hsqldb.util.DatabaseManagerSwing.main(new String[]{"--url", url});
  }
}
