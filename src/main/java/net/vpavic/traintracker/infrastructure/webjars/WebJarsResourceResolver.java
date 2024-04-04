package net.vpavic.traintracker.infrastructure.webjars;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;
import org.webjars.WebJarVersionLocator;

public class WebJarsResourceResolver extends AbstractResourceResolver {

	private static final String WEBJARS_LOCATION = "META-INF/resources/webjars/";

	private static final int WEBJARS_LOCATION_LENGTH = WEBJARS_LOCATION.length();

	private final WebJarVersionLocator webJarVersionLocator = new WebJarVersionLocator();

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

	private String findWebJarResourcePath(String path) {
		int startOffset = (path.startsWith("/") ? 1 : 0);
		int endOffset = path.indexOf('/', 1);
		if (endOffset != -1) {
			String webjar = path.substring(startOffset, endOffset);
			String partialPath = path.substring(endOffset + 1);
			String webJarPath = this.webJarVersionLocator.fullPath(webjar, partialPath);
			if (webJarPath != null) {
				return webJarPath.substring(WEBJARS_LOCATION_LENGTH);
			}
		}
		return null;
	}

}
