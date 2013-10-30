package com.octo.vanillapull.web;

import com.octo.vanillapull.model.Instrument;
import com.octo.vanillapull.repository.InstrumentDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Henri Tremblay
 */
@Controller
@RequestMapping("/instrument/*")
public class InstrumentController {

  @Inject
  private InstrumentDao instrumentDao;

  @RequestMapping(value="/list", method= RequestMethod.GET, produces="application/json")
  public @ResponseBody List<InstrumentDisplay> listAllInstruments() {
    // Retrieve instruments configuration
    List<Instrument> insList = instrumentDao.findAllOrderedBySymbol();

    // Retrieve instrument after instrument
    List<InstrumentDisplay> returnList = new ArrayList<>(insList.size());
    for (Instrument ins : insList) {
      InstrumentDisplay insDisplay = new InstrumentDisplay();
      insDisplay.setSymbol(ins.getSymbol());
      insDisplay.setLabel(ins.getLabel());
      insDisplay.setSpot(ins.getSpot());
      insDisplay.setVolatility(ins.getVolatility());
      insDisplay.setVariation(ins.getVariation());
      returnList.add(insDisplay);
    }

    return returnList;
  }
}
