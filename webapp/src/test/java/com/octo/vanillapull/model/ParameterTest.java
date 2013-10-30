package com.octo.vanillapull.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Henri Tremblay
 */
public class ParameterTest {
  @Test
  public void testGetDoubleValue() throws Exception {
    Parameter p = new Parameter("a", "123.45");
    assertEquals(123.45, p.getDoubleValue(), 0.01);
  }
}
