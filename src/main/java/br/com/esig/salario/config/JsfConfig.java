package br.com.esig.salario.config;

import jakarta.faces.webapp.FacesServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsfConfig {
    @Bean
    public ServletRegistrationBean<FacesServlet> facesServletRegistration() {
        ServletRegistrationBean<FacesServlet> servlet =
                new ServletRegistrationBean<>(new FacesServlet(), "*.xhtml");
        servlet.setLoadOnStartup(1);
        return servlet;
    }
}
