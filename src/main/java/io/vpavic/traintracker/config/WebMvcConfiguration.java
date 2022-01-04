package io.vpavic.traintracker.config;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

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
	FilterRegistrationBean<RequestDumperFilter> requestDumperFilter() {
		FilterRegistrationBean<RequestDumperFilter> requestDumperFilter = new FilterRegistrationBean<>(
				new RequestDumperFilter());
		requestDumperFilter.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return requestDumperFilter;
	}

}
