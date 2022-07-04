package xyz.vpavic.traintracker.infrastructure.cloud.heroku;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.util.StringUtils;

/**
 * An {@link EnvironmentPostProcessor} that maps Heroku env variables to appropriate Spring Boot configuration
 * properties.
 * <p>
 * Supported env variables and their respective mapped properties are:
 * <ul>
 * <li>{@code PORT}</li>
 * <li>{@code DATABASE_URL}</li>
 * <li>{@code REDIS_URL}</li>
 * </ul>
 */
public class HerokuEnvironmentPostProcessor implements EnvironmentPostProcessor {

	private static final Pattern databaseUrlPattern = Pattern.compile(
			"(?<database>\\w+)://(?<username>\\w+):(?<password>\\w+)@(?<url>.+)");

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		if (CloudPlatform.HEROKU.isActive(environment)) {
			Properties properties = new Properties();
			extractServerPort(environment, properties);
			extractDatabaseUrl(environment, properties);
			extractRedisUrl(environment, properties);
			PropertiesPropertySource herokuPropertySource = new PropertiesPropertySource("heroku", properties);
			MutablePropertySources propertySources = environment.getPropertySources();
			if (propertySources.contains(CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME)) {
				propertySources.addAfter(CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME,
						herokuPropertySource);
			}
			else {
				propertySources.addFirst(herokuPropertySource);
			}
		}
	}

	private static void extractServerPort(ConfigurableEnvironment environment, Properties properties) {
		String port = environment.getProperty("PORT");
		if (StringUtils.hasText(port)) {
			properties.setProperty("server.port", port);
		}
	}

	private static void extractDatabaseUrl(ConfigurableEnvironment environment, Properties properties) {
		String databaseUrl = environment.getProperty("DATABASE_URL");
		if (StringUtils.hasText(databaseUrl)) {
			Matcher matcher = databaseUrlPattern.matcher(databaseUrl);
			if (matcher.matches()) {
				String database = matcher.group("database");
				String url = matcher.group("url");
				String jdbcUrl = "jdbc:" + database.replace("postgres", "postgresql") + "://" + url;
				String username = matcher.group("username");
				String password = matcher.group("password");
				properties.setProperty("spring.datasource.url", jdbcUrl);
				properties.setProperty("spring.datasource.username", username);
				properties.setProperty("spring.datasource.password", password);
			}
		}
	}

	private static void extractRedisUrl(ConfigurableEnvironment environment, Properties properties) {
		String redisUrl = environment.getProperty("REDIS_URL");
		if (StringUtils.hasText(redisUrl)) {
			properties.setProperty("spring.redis.url", redisUrl);
		}
	}

}
