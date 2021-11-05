package com.copper.coppertest.deribit.oauth;

import com.copper.coppertest.deribit.oauth.model.OAuthToken;
import com.thetransactioncompany.jsonrpc2.client.ConnectionConfigurator;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;

import java.net.HttpURLConnection;
import java.util.Optional;

/**
 * A Object that is used to configure the outgoing  {@link HttpURLConnection} used by the JSON-RPC library used in
 * communicating with the Deribit API's. This allows the OAuth token to be added to the request for any /private/*
 * API's
 */
public class OAuthConnectionConfigurer implements ConnectionConfigurator
{
    private final OAuthToken oauthToken;

    /**
     * Create an instance of the OAuthConnectionConfigurer passing in a valid {@link OAuthToken}
     * @param oauthToken a valid {@link OAuthToken}
     */
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
