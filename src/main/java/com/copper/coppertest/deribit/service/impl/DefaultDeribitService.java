package com.copper.coppertest.deribit.service.impl;

import com.copper.coppertest.deribit.model.oauth.OAuthToken;
import com.copper.coppertest.deribit.service.DeribitService;
import com.copper.coppertest.deribit.service.OAuthorisationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DefaultDeribitService implements DeribitService
{
    private final OAuthorisationService oAuthorisationService;

    public DefaultDeribitService(final OAuthorisationService oAuthorisationService)
    {
        this.oAuthorisationService = oAuthorisationService;
    }

    @Override
    public void makeCall()
    {
        OAuthToken oauthToken = oAuthorisationService.getAccessToken();
        log.debug("*****DEBUG - DefaultDeribitService - oauthToken - {}", oauthToken);
    }
}
