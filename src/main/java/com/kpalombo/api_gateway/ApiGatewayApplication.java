package com.kpalombo.api_gateway;

import com.kpalombo.api_gateway.config.RouteSpotifyCallbackFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	RouteLocator gatewayRoutes(RouteLocatorBuilder builder, RouteSpotifyCallbackFilter routeSpotifyCallbackFilter) {
		return builder.routes()
				.route(route -> route
						.path("/login/oauth2/code/spotify")
						.filters(f -> f.filter(routeSpotifyCallbackFilter.apply(new RouteSpotifyCallbackFilter.Config())))
						.uri("lb://user-service")
				)
				.route(route -> route
						.path("/users/**")
						.uri("lb://user-service")
				)

				.build();

	}
}
