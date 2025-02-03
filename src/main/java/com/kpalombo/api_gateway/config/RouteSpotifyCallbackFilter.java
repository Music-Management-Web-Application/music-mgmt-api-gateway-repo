package com.kpalombo.api_gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteSpotifyCallbackFilter extends AbstractGatewayFilterFactory<RouteSpotifyCallbackFilter.Config> {
    public RouteSpotifyCallbackFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String requestUrl = request.getURI().getPath();
            String newUrl = requestUrl.replace("/login/oauth2/code/spotify", "/users/spotify/callback");
            ServerHttpRequest modifiedRequest = request.mutate()
                    .path(newUrl)
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        };
    }

    public static class Config {
        // Configuration properties (if any) can go here.
    }
}
