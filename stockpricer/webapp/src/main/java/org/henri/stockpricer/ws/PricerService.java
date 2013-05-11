package org.henri.cocktailfactory.ws;

import org.henri.stockpricer.service.Pricer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("pricer/*")
public class PricerService {

    @Inject
    private Pricer pricer;

    @RequestMapping(value="/list", method= RequestMethod.GET, produces="application/json")
    public @ResponseBody List<String> getAllTickers() {
        return pricer.listAllTickers();
    }

    @RequestMapping(value="/price", method= RequestMethod.GET, produces="application/json")
    public @ResponseBody BigDecimal price(@RequestParam String ticker, @RequestParam BigDecimal spot, @RequestParam Date maturityDate) {
        return pricer.getPrice(ticker);
    }

}