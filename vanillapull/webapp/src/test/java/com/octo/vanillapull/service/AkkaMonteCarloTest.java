package com.octo.vanillapull.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Henri Tremblay
 */
public class AkkaMonteCarloTest {
  @Test
  public void testCalculatePrice() throws Exception {
    AkkaMonteCarlo c = new AkkaMonteCarlo();
    c.numberOfIterations = 1_000_000;
    c.interestRate = 0.015;
    c.init();

    // insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('BNP','BNP Paribas', 45.04, 1, 0.89);
    double actual = c.calculatePrice(90, 45.04, 17, 1);
    c.cleanUp();

    assertEquals(28.2, actual, 0.1);
  }
}
