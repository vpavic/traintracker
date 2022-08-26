package xyz.vpavic.traintracker.infrastructure.webjars;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

/**
 * See <a href="https://github.com/spring-projects/spring-framework/issues/27619">spring-projects/spring-framework#27619</a>.
 */
public class WebJarsVersionResourceResolver extends AbstractResourceResolver {

	private static final String PROPERTIES_ROOT = "META-INF/maven/";

	private static final String NPM = "org.webjars.npm/";

	private static final String PLAIN = "org.webjars/";

	private static final String POM_PROPERTIES = "/pom.properties";

	@Override
	protected Resource resolveResourceInternal(HttpServletRequest request, @NonNull String requestPath,
			@NonNull List<? extends Resource> locations, ResourceResolverChain chain) {
		Resource resolved = chain.resolveResource(request, requestPath, locations);
		if (resolved == null) {
			String webJarResourcePath = findWebJarResourcePath(requestPath);
			if (webJarResourcePath != null) {
				return chain.resolveResource(request, webJarResourcePath, locations);
			}
		}
		return resolved;
	}

	@Override
	protected String resolveUrlPathInternal(@NonNull String resourceUrlPath,
			@NonNull List<? extends Resource> locations, ResourceResolverChain chain) {
		String path = chain.resolveUrlPath(resourceUrlPath, locations);
		if (path == null) {
			String webJarResourcePath = findWebJarResourcePath(resourceUrlPath);
			if (webJarResourcePath != null) {
				return chain.resolveUrlPath(webJarResourcePath, locations);
			}
		}
		return path;
	}

	protected String findWebJarResourcePath(String path) {
		int startOffset = (path.startsWith("/") ? 1 : 0);
		int endOffset = path.indexOf('/', 1);
		if (endOffset != -1) {
			String webjar = path.substring(startOffset, endOffset);
			String partialPath = path.substring(endOffset + 1);
			String version = resolveWebJarVersion(webjar);
			if (version != null) {
				return webjar + File.separator + version + File.separator + partialPath;
			}
		}
		return null;
	}

	private String resolveWebJarVersion(String webjar) {
		if (webjar.length() < 1) {
			return null;
		}
		Resource resource = new ClassPathResource(PROPERTIES_ROOT + NPM + webjar + POM_PROPERTIES);
		if (!resource.isReadable()) {
			resource = new ClassPathResource(PROPERTIES_ROOT + PLAIN + webjar + POM_PROPERTIES);
		}
		// TODO consider supporting Bower variant
		if (resource.isReadable()) {
			try {
				Properties properties = PropertiesLoaderUtils.loadProperties(resource);
				return properties.getProperty("version");
			}
			catch (IOException ignored) {
			}
		}
		return null;
	}

}