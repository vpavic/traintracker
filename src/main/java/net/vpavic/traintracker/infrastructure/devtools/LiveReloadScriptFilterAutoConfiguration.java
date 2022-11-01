package net.vpavic.traintracker.infrastructure.devtools;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.devtools.autoconfigure.DevToolsProperties;
import org.springframework.boot.devtools.restart.ConditionalOnInitializedRestarter;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(afterName = "org.springframework.boot.devtools.autoconfigure.LocalDevToolsAutoConfiguration")
@ConditionalOnInitializedRestarter
class LiveReloadScriptFilterAutoConfiguration {

	@Bean
	@RestartScope
	@ConditionalOnProperty(prefix = "spring.devtools.livereload", name = "enabled", matchIfMissing = true)
	LiveReloadScriptFilter liveReloadScriptFilter(DevToolsProperties properties) {
		return new LiveReloadScriptFilter(properties.getLivereload().getPort());
	}

}
