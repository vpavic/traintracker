package net.vpavic.traintracker.infrastructure.web;

import java.time.Duration;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import net.vpavic.traintracker.infrastructure.webjars.WebJarsVersionResourceResolver;

@Configuration(proxyBeanMethods = false)
class WebMvcConfiguration implements WebMvcConfigurer {

	private final WebProperties.Resources.Cache resourcesCacheProperties;

	WebMvcConfiguration(WebProperties webProperties) {
		this.resourcesCacheProperties = webProperties.getResources().getCache();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**")
				.addResourceLocations("classpath:META-INF/resources/webjars/")
				.setCachePeriod(getSeconds(this.resourcesCacheProperties.getPeriod()))
				.setCacheControl(this.resourcesCacheProperties.getCachecontrol().toHttpCacheControl())
				.setUseLastModified(this.resourcesCacheProperties.isUseLastModified())
				.resourceChain(true)
				.addResolver(new WebJarsVersionResourceResolver());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/web");
	}

	@Bean
	CookieLocaleResolver localeResolver() {
		return new CookieLocaleResolver("locale");
	}

	private static Integer getSeconds(Duration cachePeriod) {
		return (cachePeriod != null) ? (int) cachePeriod.getSeconds() : null;
	}

}
