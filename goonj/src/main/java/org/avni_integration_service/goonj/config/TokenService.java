package org.avni_integration_service.goonj.config;

import org.apache.log4j.Logger;
import org.avni_integration_service.goonj.domain.AuthResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    private static final Logger logger = Logger.getLogger(TokenService.class);
    private Duration clockSkew = Duration.ofSeconds(60);
    private Clock clock = Clock.systemUTC();
    private final GoonjConfig goonjConfig;
    private final RestTemplate restTemplate;
    private OAuth2AccessToken tokenCache;

    public TokenService(GoonjConfig goonjConfig, RestTemplate restTemplate) {
        this.goonjConfig = goonjConfig;
        this.restTemplate = restTemplate;
    }

    public OAuth2AccessToken getRefreshedToken() {
        if (tokenCache == null || hasTokenExpired(tokenCache)) {
            logger.info("Token expired, fetching new token");
            tokenCache = loginWithCredentials();
        } else {
            logger.debug("Token still valid");
        }

        return tokenCache;
    }

    public OAuth2AccessToken loginWithCredentials() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", goonjConfig.getLoginUserName());
        map.add("password", goonjConfig.getLoginPassword());
        map.add("grant_type", "password");
        map.add("client_id", goonjConfig.getClientId());
        map.add("client_secret", goonjConfig.getClientSecret());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity< AuthResponse > response = restTemplate
                .postForEntity(URI.create(goonjConfig.getSalesForceAuthUrl()), request, AuthResponse.class);
        AuthResponse authResponse = response.getBody();
        Long issuedAt = Long.parseLong(authResponse.getIssuedAt());
        OAuth2AccessToken a2at = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, authResponse.getAccessToken(),
                Instant.ofEpochMilli(issuedAt),
                Instant.ofEpochMilli(issuedAt+goonjConfig.getTokenExpiry()));

        return a2at;
    }

    private boolean hasTokenExpired(OAuth2Token token) {
        return this.clock.instant().isAfter(token.getExpiresAt().minus(this.clockSkew));
    }
}
