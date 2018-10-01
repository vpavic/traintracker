package io.traintracker.interfaces;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute("principal")
	public OAuth2User authentication(@AuthenticationPrincipal OAuth2User principal) {
		return principal;
	}

}
