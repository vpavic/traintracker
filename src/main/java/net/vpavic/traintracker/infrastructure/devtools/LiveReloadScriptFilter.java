package net.vpavic.traintracker.infrastructure.devtools;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class LiveReloadScriptFilter extends OncePerRequestFilter {

	private final int liveReloadPort;

	public LiveReloadScriptFilter(int liveReloadPort) {
		this.liveReloadPort = liveReloadPort;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		filterChain.doFilter(request, response);
		if ((response.getContentType() != null) && response.getContentType().toLowerCase().contains("text/html")) {
			response.getWriter().write("<script src=\"/assets/livereload-js/dist/livereload.js?port=" + this.liveReloadPort + "\"></script>");
		}
	}

}
