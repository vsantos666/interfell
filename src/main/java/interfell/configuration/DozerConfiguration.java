package interfell.configuration;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;

/**
 * Created by vsantos on 23/04/2019.
 */
@Configuration
public class DozerConfiguration {

    @Bean
    public DozerBeanMapper getMapper() {
        return new DozerBeanMapper(
                Arrays.asList("dozer-mapping.xml")
        );
    }

}
