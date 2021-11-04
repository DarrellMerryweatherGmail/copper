package com.copper.coppertest.deribit.service;

import com.copper.coppertest.deribit.model.oauth.OAuthToken;

public interface OAuthorisationService
{
    OAuthToken getAccessToken();
}
