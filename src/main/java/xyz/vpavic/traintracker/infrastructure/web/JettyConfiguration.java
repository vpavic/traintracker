package xyz.vpavic.traintracker.infrastructure.web;

import java.nio.ByteBuffer;

import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.util.BufferUtil;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class JettyConfiguration {

	@Bean
	JettyServerCustomizer jettyServerCustomizer() {
		return server -> server.setErrorHandler(new ErrorHandler() {

			@Override
			public ByteBuffer badMessageError(int status, String reason, HttpFields.Mutable fields) {
				return BufferUtil.EMPTY_BUFFER;
			}

		});
	}

}
