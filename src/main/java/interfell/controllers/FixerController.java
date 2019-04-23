package interfell.controllers;

import interfell.bean.JsonResult;
import interfell.gateway.FixerApiGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by vsantos on 23/04/2019.
 */
@Controller
@RequestMapping(value = "/fixer")
@PropertySources({
        @PropertySource("file:${fixer.property.file}")
})
public class FixerController {

    private final Logger logger = LoggerFactory.getLogger(FixerController.class);

    @Autowired
    private Environment env;

    @Autowired
    private FixerApiGateway fixerApiGateway;

    @RequestMapping(path = "/rates",method = RequestMethod.GET)
    public @ResponseBody
    Object getMonedas() {
        try{
            Object res = fixerApiGateway.getCoins(env.getProperty("fixer.url"),env.getProperty("fixer.token"));
            return res;
        }catch (Exception ex){
            return new JsonResult(false, null, ex.getMessage());
        }
    }

}
