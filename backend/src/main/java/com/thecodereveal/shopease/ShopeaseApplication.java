package com.thecodereveal.shopease;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@SpringBootApplication
public class ShopeaseApplication {

	@Value("${stripe.secret}")
	private String stripeSecret;

	public static void main(String[] args) {
		SpringApplication.run(ShopeaseApplication.class, args);
	}

	@PostConstruct
	public void init() {
		Stripe.apiKey = stripeSecret;
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // Allow credentials (cookies, authorization headers)
		config.addAllowedOriginPattern("*"); // Allow all origins
		config.addAllowedHeader("*"); // Allow all headers
		config.addAllowedMethod("*"); // Allow all HTTP methods
		config.addExposedHeader("*"); // Expose all headers

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}
}
