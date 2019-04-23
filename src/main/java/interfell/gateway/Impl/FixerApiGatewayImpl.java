package interfell.gateway.Impl;

import interfell.gateway.FixerApiGateway;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by vsantos on 23/04/2019.
 */
@Service
public class FixerApiGatewayImpl implements FixerApiGateway {

    private final Logger logger = LoggerFactory.getLogger(FixerApiGatewayImpl.class);

    /**
     * Method to call fixer api to get the rates
     * @param url
     * @param token
     * @return
     */
    @Override
    public Object getCoins(String url, String token) {
        RestTemplate restTemplate = new RestTemplate();
        url = url + "latest?access_key=";
        url = url + token;
        logger.info("URL REST SERVICE: " + url);
        Object a = restTemplate.exchange(url, HttpMethod.GET, null, Object.class);
        return ((ResponseEntity) a).getBody();
    }

    /**
     * Method to exchange currency
     * @param url
     * @param token
     * @param from
     * @param to
     * @param amount
     * @return
     */
    @Override
    public Object priceCalculator(String url, String token,String from,String to,String amount) {
        RestTemplate restTemplate = new RestTemplate();
        url = url + "convert?access_key=";
        url = url + token+"&from="+from+"&to="+to+"&amount="+amount;
        logger.info("URL REST SERVICE: " + url);
        Object a = restTemplate.exchange(url, HttpMethod.GET, null, Object.class);
        return ((ResponseEntity) a).getBody();
    }


}
