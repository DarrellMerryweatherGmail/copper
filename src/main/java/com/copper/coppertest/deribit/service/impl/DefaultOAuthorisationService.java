package com.copper.coppertest.deribit.service.impl;

import com.copper.coppertest.deribit.oauth.model.OAuthToken;
import com.copper.coppertest.deribit.service.OAuthorisationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DefaultOAuthorisationService implements OAuthorisationService
{
    private final String apiBaseUri;
    private final String authPath;
    private final ObjectMapper objectMapper;
    private final String clientId;
    private final String clientSecret;

    public DefaultOAuthorisationService(
            @Value("${deribit.api-base-uri}") final String apiBaseUri,
            @Value("${deribit.oauth.auth-path}") final String authPath,
            @Value("${deribit.oauth.client-id}") final String clientId,
            @Value("${deribit.oauth.client-secret}") final String clientSecret,
            final ObjectMapper objectMapper)
    {
        this.apiBaseUri = apiBaseUri;
        this.authPath = authPath;
        this.objectMapper = objectMapper;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public OAuthToken getAccessToken() {
        OAuthToken oauthToken = null;
        try {
            JSONRPC2Session session = new JSONRPC2Session(new URL(apiBaseUri));
            final Map<String, Object> parameters = new HashMap<>();

            parameters.put(OAuthParameter.GRANT_TYPE.getParameterName(), GrantType.CLIENT_CREDENTIALS.getGrantType());
            parameters.put(OAuthParameter.CLIENT_ID.getParameterName(), this.clientId);
            parameters.put(OAuthParameter.CLIENT_SECRET.getParameterName(), this.clientSecret);

            JSONRPC2Request request = new JSONRPC2Request(authPath, parameters, 0);
            JSONRPC2Response response = session.send(request);

            if (response.indicatesSuccess())
            {
                oauthToken = objectMapper.convertValue(response.getResult(), OAuthToken.class);
            } else {
                log.debug("*****DEBUG - FAILURE");
            }
        } catch (MalformedURLException | JSONRPC2SessionException e) {
            e.printStackTrace();
        }
        return oauthToken;
    }

    enum OAuthParameter
    {
        GRANT_TYPE("grant_type"),
        CLIENT_ID("client_id"),
        CLIENT_SECRET("client_secret");

        private final String parameterName;
        OAuthParameter(final String parameterName)
        {
            this.parameterName = parameterName;
        }
        public String getParameterName() { return this.parameterName; }
    }
    enum GrantType
    {
        CLIENT_CREDENTIALS("client_credentials");

        private String grantType;
        GrantType(final String grantType)
        {
            this.grantType = grantType;
        }

        public String getGrantType() { return this.grantType; }
    }
}
