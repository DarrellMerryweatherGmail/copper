package com.copper.coppertest.deribit.oauth;

import com.copper.coppertest.deribit.oauth.model.OAuthToken;
import com.thetransactioncompany.jsonrpc2.client.ConnectionConfigurator;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;

import java.net.HttpURLConnection;
import java.util.Optional;

public class OAuthConnectionConfigurer implements ConnectionConfigurator
{
    private final OAuthToken oauthToken;

    public OAuthConnectionConfigurer(final OAuthToken oauthToken)
    {
        this.oauthToken = oauthToken;
    }

    @Override
    public void configure(HttpURLConnection connection)
    {
        Optional.of(this.oauthToken)
                .map(OAuthToken::getAccessToken)
                .filter(accessToken -> !ObjectUtils.isEmpty(accessToken))
                .ifPresent(nonEmptyOAuthToken -> connection.setRequestProperty(HttpHeaders.AUTHORIZATION,
                        String.join(" ", "Bearer", nonEmptyOAuthToken))
                );
    }
}
