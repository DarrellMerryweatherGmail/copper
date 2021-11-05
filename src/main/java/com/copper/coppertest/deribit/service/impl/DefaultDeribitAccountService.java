package com.copper.coppertest.deribit.service.impl;

import com.copper.coppertest.deribit.oauth.OAuthConnectionConfigurer;
import com.copper.coppertest.deribit.oauth.model.OAuthToken;
import com.copper.coppertest.deribit.service.DeribitAccountService;
import com.copper.coppertest.deribit.service.DeribitErrorMessages;
import com.copper.coppertest.deribit.service.OAuthorisationService;
import com.copper.coppertest.deribit.account.model.AccountDto;
import com.copper.coppertest.service.AccountService;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DefaultDeribitAccountService implements DeribitAccountService
{
    private final OAuthorisationService oAuthorisationService;
    private final String apiBaseUri;
    private final String subaccountsPath;
    private final ObjectMapper objectMapper;
    private final AccountService accountService;

    public DefaultDeribitAccountService(
            @Value("${deribit.api-base-uri}") final String apiBaseUri,
            @Value("${deribit.accounts.subaccounts-path}") final String subaccountsPath,
            final OAuthorisationService oAuthorisationService,
            final ObjectMapper objectMapper,
            final AccountService accountService)
    {
        this.apiBaseUri = apiBaseUri;
        this.oAuthorisationService = oAuthorisationService;
        this.subaccountsPath = subaccountsPath;
        this.objectMapper = objectMapper;
        this.accountService = accountService;
    }

    @Override
    public void syncAccounts()
    {
        OAuthToken oauthToken = oAuthorisationService.getAccessToken();

        try {
            JSONRPC2Session session = new JSONRPC2Session(new URL(apiBaseUri));
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(AccountParameters.WITH_PORTFOLIO.getParameterName(), true);

            session.setConnectionConfigurator(new OAuthConnectionConfigurer(oauthToken));

            JSONRPC2Request request = new JSONRPC2Request(subaccountsPath, parameters, 1);
            JSONRPC2Response response = session.send(request);

            if (response.indicatesSuccess())
            {
                final AccountDto[] accounts = objectMapper.convertValue(response.getResult(), AccountDto[].class);
                this.saveAccounts(accounts);
            } else {
                log.error(DeribitErrorMessages.CONNECTION_FAILED_CHECK_LOGS);
            }
        } catch (MalformedURLException | JSONRPC2SessionException e) {
            log.error(DeribitErrorMessages.CONNECTION_FAILED_TEMPLATE, e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    private void saveAccounts(final AccountDto[] accounts)
    {
        Arrays.stream(accounts).forEach(accountService::saveAccount);
    }
    enum AccountParameters
    {
        WITH_PORTFOLIO("with_portfolio");

        private final String parameterName;

        AccountParameters(final String parameterName)
        {
            this.parameterName = parameterName;
        }
        public String getParameterName() { return this.parameterName; }

    }
}
