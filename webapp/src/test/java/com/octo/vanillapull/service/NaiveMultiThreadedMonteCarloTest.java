package com.octo.vanillapull.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Henri Tremblay
 */
public class NaiveMultiThreadedMonteCarloTest {
  @Test
  public void testCalculatePrice() throws Exception {
    NaiveMultiThreadedMonteCarlo c = new NaiveMultiThreadedMonteCarlo();
    c.numberOfIterations = 1_000_000;
    c.interestRate = 0.015;

    // insert into INSTRUMENT(symbol, label, SPOT, VOLATILITY, VARIATION) values('BNP','BNP Paribas', 45.04, 1, 0.89);
    double actual = c.calculatePrice(90, 45.04, 17, 1);
    assertEquals(28.2, actual, 0.1);
  }
}
