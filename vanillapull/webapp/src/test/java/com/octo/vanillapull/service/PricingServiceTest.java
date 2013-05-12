package com.octo.vanillapull.service;

import com.octo.vanillapull.repository.ParameterDao;
import com.octo.vanillapull.model.Parameter;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class PricingServiceTest {

	private PricingService service = new PricingService();

	@Test
	public void testCalculatePrice() throws Exception {
		ParameterDao parameterDao = createMock(ParameterDao.class);
		expect(parameterDao.findById("INTEREST_RATE")).andStubReturn(
				new Parameter("INTEREST_RATE", "0.015"));
		replay(parameterDao);

		service.setParameterDao(parameterDao);

		double d = service.calculatePrice(270.0, 71.41, 75.0, 0.20);
		// Calculated with http://www.espenhaug.com/BS_applet.html
		assertEquals(3.7664, d, 0.0001);
	}

}
