package io.traintracker.interfaces.voyage;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.traintracker.domain.model.voyage.Station;
import io.traintracker.domain.model.voyage.Voyage;
import io.traintracker.application.VoyageFetcher;
import io.traintracker.application.VoyageFetcherResolver;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class WebController {

	private final VoyageFetcherResolver resolver;

	private final Map<String, String> logins;

	public WebController(VoyageFetcherResolver resolver) {
		Objects.requireNonNull(resolver, "VoyageFetcherResolver must not be null");
		this.resolver = resolver;
		this.logins = prepareLogins();
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> prepareLogins() {
		return Stream.of("google").collect(Collectors.toMap(s -> s, s -> "/oauth2/authorization/" + s));
	}

	@ModelAttribute("countries")
	public Set<String> countries() {
		return this.resolver.supportedCountries();
	}

	@ModelAttribute("logins")
	public Map<String, String> logins() {
		return this.logins;
	}

	@ModelAttribute("principal")
	public OAuth2User authentication(@AuthenticationPrincipal OAuth2User principal) {
		return principal;
	}

	@GetMapping(path = "/")
	public String home(@ModelAttribute("countries") Set<String> countries) {
		return "redirect:/" + countries.iterator().next() + "/";
	}

	@GetMapping(path = "/{country:[a-z]{2}}")
	public String country(@PathVariable String country) {
		return "redirect:/" + country + "/";
	}

	@GetMapping(path = "/{country:[a-z]{2}}/")
	public String country(@PathVariable String country, Model model,
			@ModelAttribute("countries") Set<String> countries) {
		model.addAttribute("country", country);
		if (!countries.contains(country)) {
			return "not-found :: fragment";
		}
		return "home";
	}

	@GetMapping(path = "/{country:[a-z]{2}}/{train}", produces = MediaType.TEXT_HTML_VALUE)
	public String voyage(@PathVariable String country, @PathVariable String train,
			@RequestHeader(name = "X-PJAX", required = false) boolean pjax, Model model) {
		model.addAttribute("country", country);
		VoyageFetcher fetcher = this.resolver.getVoyageFetcher(country);
		if (fetcher == null) {
			return "not-found" + (pjax ? " :: fragment" : "");
		}
		model.addAttribute("train", train);
		Voyage voyage = fetcher.getVoyage(train);
		if (voyage == null) {
			return "not-found" + (pjax ? " :: fragment" : "");
		}
		model.addAttribute("delayLevel", calculateDelayLevel(voyage.getCurrentStation()));
		model.addAttribute("voyage", voyage);
		return "voyage" + (pjax ? " :: fragment" : "");
	}

	private String calculateDelayLevel(Station station) {
		Integer delay = station.getDepartureDelay() != null
				? station.getDepartureDelay() : station.getArrivalDelay();
		if (delay == null) {
			return "info";
		}
		else if (delay < 1) {
			return "success";
		}
		else if (delay < 20) {
			return "warning";
		}
		else {
			return "danger";
		}
	}

}
