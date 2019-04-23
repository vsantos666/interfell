package interfell.controllers;

import interfell.configuration.*;
import interfell.gateway.FixerApiGateway;
import junit.framework.TestCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebAppInitializer.class, AppConfiguration.class,
        DozerConfiguration.class, PersistConfiguration.class, WebMvcConfiguration.class})
@WebAppConfiguration
@PropertySources({
        @PropertySource("file:${fixer.property.file}")
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixerControllerTest extends TestCase {

    @Autowired
    private FixerApiGateway fixerApiGateway;

    @Autowired
    private Environment env;

    @Test
    public void test1_getCoins() {
        Object res = fixerApiGateway.getCoins(env.getProperty("fixer.url"),env.getProperty("fixer.token"));
        System.out.println(res);
        assertNotNull(res);
    }

    @Test
    public void test2_priceCalculator() {
        Object res = fixerApiGateway.priceCalculator(env.getProperty("fixer.url"),env.getProperty("fixer.token"),"EUR","USD","50");
        System.out.println(res);
        assertNotNull(res);
    }


}
