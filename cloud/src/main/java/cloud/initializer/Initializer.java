package cloud.initializer;

import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import cloud.config.AppConfig;
import cloud.config.WebMvcConfig;

public class Initializer implements WebApplicationInitializer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("onStartup");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        // Manage the lifecycle of the root appcontext
        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.setInitParameter("defaultHtmlEscape", "true");
        servletContext.setInitParameter("encoding", "UTF-8");
        
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter",new CharacterEncodingFilter());
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");

        FilterRegistration encodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter("UTF-8"));
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
        encodingFilter.setInitParameter("encoding", "UTF-8");

//        System.out.println("LOGGİNG FİLTER REGİSTERING");
//        javax.servlet.FilterRegistration.Dynamic corsFilter = servletContext.addFilter("logfilter", LoggingFilter.class);
//        corsFilter.addMappingForUrlPatterns(null, true, "/*");
        
        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();

        mvcContext.register(WebMvcConfig.class);

        // The main Spring MVC servlet.
        ServletRegistration.Dynamic appServlet = servletContext.addServlet("appServlet", new DispatcherServlet(mvcContext));

//        HttpConstraintElement httpConstraintElement = new HttpConstraintElement(ServletSecurity.TransportGuarantee.NONE);
//        ServletSecurityElement servletSecurityElement = new ServletSecurityElement(httpConstraintElement);
//        appServlet.setServletSecurity(servletSecurityElement);

        appServlet.setLoadOnStartup(1);
        Set<String> mappingConflicts = appServlet.addMapping("/") ;

        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                System.out.println("Mapping conflict: " + s);
            }
            throw new IllegalStateException(
                    "'appServlet' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
        }
    }

}
