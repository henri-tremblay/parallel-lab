package com.octo.vanillapull.service;

import com.octo.vanillapull.repository.InstrumentDao;
import com.octo.vanillapull.model.Instrument;
import com.octo.vanillapull.shared.InstrumentDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("pricer/*")
public class InstrumentService {

	@Autowired
	private InstrumentDao instrumentDao;

    @RequestMapping(value="/list", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody List<InstrumentDisplay> getInstrumentList() {
		// Retrieve instruments configuration
		List<Instrument> insList = instrumentDao.findAll();

		// Retrieve instrument after instrument
		List<InstrumentDisplay> returnList = new ArrayList<>();
		for (Instrument ins : insList) {
			InstrumentDisplay insDisplay = new InstrumentDisplay();
			insDisplay.setSymbol(ins.getSymbol());
			insDisplay.setLabel(ins.getLabel());
			returnList.add(insDisplay);
		}

		return returnList;
	}
}
