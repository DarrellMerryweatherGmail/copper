package com.copper.coppertest.deribit.service;

import com.copper.coppertest.deribit.oauth.model.OAuthToken;

public interface OAuthorisationService
{
    OAuthToken getAccessToken();
}
