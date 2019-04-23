package interfell.gateway;

/**
 * Created by vsantos on 23/04/2019.
 */
public interface FixerApiGateway {

    Object getCoins(String url, String token);

    Object priceCalculator (String url, String token,String from,String to,String amount);

}
