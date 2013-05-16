package com.octo.vanillapull.repository;

import com.octo.vanillapull.model.Instrument;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Henri Tremblay
 */
@ContextConfiguration("classpath:spring/root-context.xml")
public class InstrumentDaoTest extends AbstractJUnit4SpringContextTests {

  @Inject
  private InstrumentDao dao;

  @Test
  public void testAccent() throws Exception {
    Instrument i = dao.findById("ACA");
    assertEquals("Credit Agricole", i.getLabel());

    List<Instrument> l = dao.findAllOrderedBySymbol();
    assertEquals("Credit Agricole", l.get(1).getLabel());
  }
}
