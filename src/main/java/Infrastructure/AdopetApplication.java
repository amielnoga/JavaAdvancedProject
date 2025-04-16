package Infrastructure;

import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import java.util.EnumSet;


/**
 * Main application class for the Adopet Application.
 * This class sets up the Spring Boot application along with configurations
 * for JSF support and URL rewriting.
 */
@RestController
@EnableAutoConfiguration
@ComponentScan({"Infrastructure", "BackEnd"})
public class AdopetApplication extends SpringBootServletInitializer {
    /**
     * Entry point for the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AdopetApplication.class, args);
    }

    /**
     * Registers the RewriteFilter, used to handle clean URLs and URL rewriting.
     * Supports multiple dispatcher types to ensure filter execution in different phases.
     *
     * @return a FilterRegistrationBean configured for the RewriteFilter
     */
    @Bean
    public FilterRegistrationBean rewriteFilter() {
        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
        rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
                DispatcherType.ASYNC, DispatcherType.ERROR));
        rwFilter.addUrlPatterns("/*"); // relevant to all URL links
        return rwFilter;
    }

    /**
     * Registers the JSF FacesServlet for handling .xhtml files.
     * This is necessary for JSF lifecycle handling with .xhtml views.
     *
     * @return a ServletRegistrationBean configured for .xhtml files
     */
    @Bean
    public ServletRegistrationBean<FacesServlet> facesServlet() {
        ServletRegistrationBean<FacesServlet> servlet = new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
        servlet.setLoadOnStartup(1);
        return servlet;
    }


}
