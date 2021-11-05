package com.copper.coppertest.deribit.service;

import com.copper.coppertest.deribit.oauth.model.OAuthToken;

/**
 * A definition of a service that is used to retrieve {@link OAuthToken}'s from an OAuth service, in this case the Deribit
 * auth endpoint
 */
public interface OAuthService
{
    /**
     * Get a valid {@link OAuthToken} from Deribit
     * @return a value {@link OAuthToken}
     */
    OAuthToken getAccessToken();
}
