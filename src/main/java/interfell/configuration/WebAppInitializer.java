package interfell.configuration;

import interfell.filters.CORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Set;


/**
 * Created by vsantos on 23/04/2019.
 */
public class WebAppInitializer implements WebApplicationInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        // Register application config
        rootContext.register(AppConfiguration.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Register Encoding Filter
        addEncodingFilter(container);

        // Register Logging Filter
        addLoggingFilter(container);

        // Register CORS Filter
        addCorsFilter(container);

        // Register and map the dispatcher servlet
        addServiceDispatcherServlet(container, rootContext);
    }

    private void addServiceDispatcherServlet(ServletContext container, AnnotationConfigWebApplicationContext rootContext) {
        final String SERVICES_MAPPING = "/api/*";

        ServletRegistration.Dynamic dispatcher = container.addServlet("servicesDispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        Set<String> mappingConflicts = dispatcher.addMapping(SERVICES_MAPPING);

        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                LOG.error("Mapping conflict: " + s);
            }
            throw new IllegalStateException("'ServicesDispatcher' could not be mapped to '" + SERVICES_MAPPING + "'");
        }
    }

    private void addEncodingFilter(ServletContext container) {
        FilterRegistration.Dynamic fr = container.addFilter("encodingFilter", new CharacterEncodingFilter());
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/api/*");
    }

    private void addLoggingFilter(ServletContext container) {
        FilterRegistration.Dynamic fr = container.addFilter("loggingFilter", new CommonsRequestLoggingFilter());
        fr.addMappingForUrlPatterns(null, true, "/api/*");
    }

    private void addCorsFilter(ServletContext context) {
        FilterRegistration.Dynamic fr = context.addFilter("corsFilter", new CORSFilter());
        fr.addMappingForUrlPatterns(null, true, "/api/*");

    }

}

