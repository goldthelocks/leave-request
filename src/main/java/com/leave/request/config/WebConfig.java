/**
 * 
 */
package com.leave.request.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/**
 * @author Eraine
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
	}
	
	@Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

	  return new EmbeddedServletContainerCustomizer() {
	    @Override
	    public void customize(ConfigurableEmbeddedServletContainer container)       
	    {
	      ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/401.html");
	      ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error/403.html");
	      ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
	      ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html");

	      container.addErrorPages(error401Page, error403Page, error404Page, error500Page);
	    }
	  };
	}

}
