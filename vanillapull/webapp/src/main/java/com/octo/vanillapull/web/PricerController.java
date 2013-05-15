package com.octo.vanillapull.web;

import com.octo.vanillapull.model.Instrument;
import com.octo.vanillapull.repository.InstrumentDao;
import com.octo.vanillapull.service.PricingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * @author Henri Tremblay
 */
@Controller
@RequestMapping("pricer/*")
public class PricerController {

  @Inject
  private PricingService pricingService;

  @Inject
  private InstrumentDao instrumentDao;

  @RequestMapping(value = "/price", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody double calculatePrice(@RequestParam String symbol, @RequestParam double maturity, @RequestParam double strike) {
    Instrument instrument = instrumentDao.findById(symbol);
    return pricingService.calculatePrice(maturity, instrument.getSpot(), strike, instrument.getVolatility());
  }
}
