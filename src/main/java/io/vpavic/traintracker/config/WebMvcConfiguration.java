package io.vpavic.traintracker.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration(proxyBeanMethods = false)
class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Bean
	CookieLocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieName("LOCALE");
		return localeResolver;
	}

	@Bean
	WebMvcRegistrations webMvcRegistrations() {
		return new WebMvcRegistrations() {

			@Override
			public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
				RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
				handlerMapping.setUseTrailingSlashMatch(false);
				return handlerMapping;
			}

		};
	}

}
