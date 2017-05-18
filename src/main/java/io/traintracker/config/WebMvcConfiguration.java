package io.traintracker.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrationsAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieName("LOCALE");
		return localeResolver;
	}

	@Bean
	public WebMvcRegistrationsAdapter webMvcRegistrationsAdapter() {
		return new WebMvcRegistrationsAdapter() {

			@Override
			public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
				RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
				handlerMapping.setUseTrailingSlashMatch(false);
				return handlerMapping;
			}

		};
	}

}
