package com.copper.coppertest.deribit.service.impl;

import com.copper.coppertest.deribit.oauth.OAuthConnectionConfigurer;
import com.copper.coppertest.deribit.oauth.model.OAuthToken;
import com.copper.coppertest.deribit.service.DeribitWalletService;
import com.copper.coppertest.deribit.service.OAuthorisationService;
import com.copper.coppertest.deribit.wallet.model.DepositDto;
import com.copper.coppertest.deribit.wallet.model.DepositResponse;
import com.copper.coppertest.deribit.wallet.model.TransferRequest;
import com.copper.coppertest.deribit.wallet.model.TransferToSubaccountDto;
import com.copper.coppertest.deribit.wallet.model.WithdrawalDto;
import com.copper.coppertest.deribit.wallet.model.WithdrawalResponse;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DefaultDeribitWalletService implements DeribitWalletService
{
    private final OAuthorisationService oAuthorisationService;
    private final ObjectMapper objectMapper;
    private final String apiBaseUri;
    private final String depositsPath;
    private final String withdrawalsPath;
    private final String submitTransferToSubaccountPath;


    public DefaultDeribitWalletService(
            @Value("${deribit.api-base-uri}") final String apiBaseUri,
            @Value("${deribit.wallet.deposits-path}") final String depositsPath,
            @Value("${deribit.wallet.withdrawals-path}") final String withdrawalsPath,
            @Value("${deribit.wallet.submit-transfer-to-subaccount-path}") final String submitTransferToSubaccountPath,
            final OAuthorisationService oAuthorisationService,
            final ObjectMapper objectMapper)
    {
        this.oAuthorisationService = oAuthorisationService;
        this.apiBaseUri = apiBaseUri;
        this.depositsPath = depositsPath;
        this.withdrawalsPath = withdrawalsPath;
        this.objectMapper = objectMapper;
        this.submitTransferToSubaccountPath = submitTransferToSubaccountPath;
    }

    @Override
    public List<DepositDto> getDeposits(final String currency)
    {
        try {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("currency", currency.toUpperCase());

            final JSONRPC2Response response = sendRequestToDeribit(depositsPath, parameters, 2);

            if (response.indicatesSuccess())
            {
                log.debug("*****DEBUG - response - {}", response.getResult());
                final DepositResponse depositResponse = objectMapper.convertValue(response.getResult(), DepositResponse.class);
                log.debug("******DEBUG - depositResponse - {}", depositResponse);
                return List.of(depositResponse.getData());
            } else {
                log.error("Connection to Deribit has failed, please check the logs");
            }
        } catch (MalformedURLException | JSONRPC2SessionException e) {
            log.error("Connection to Deribit failed - {}", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<WithdrawalDto> getWithdrawals(final String currency)
    {
        try {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("currency", currency.toUpperCase());

            final JSONRPC2Response response = sendRequestToDeribit(withdrawalsPath, parameters, 2);

            if (response.indicatesSuccess())
            {
                log.debug("*****DEBUG - response - {}", response.getResult());
                final WithdrawalResponse
                        withdrawalResponse = objectMapper.convertValue(response.getResult(), WithdrawalResponse.class);
                log.debug("******DEBUG - depositResponse - {}", withdrawalResponse);
                return List.of(withdrawalResponse.getData());
            } else {
                log.error("Connection to Deribit has failed, please check the logs");
            }
        } catch (MalformedURLException | JSONRPC2SessionException e) {
            log.error("Connection to Deribit failed - {}", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    @Override
    public TransferToSubaccountDto transfer(final String currency, final TransferRequest transferRequest)
    {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("currency", currency.toUpperCase());
        parameters.put("destination", transferRequest.getDestination());
        parameters.put("amount", transferRequest.getAmount());

        try {
            final JSONRPC2Response response = sendRequestToDeribit(submitTransferToSubaccountPath, parameters, 3);

            if (response.indicatesSuccess())
            {
                log.debug("*****DEBUG - response - {}", response.getResult());
                return objectMapper.convertValue(response.getResult(), TransferToSubaccountDto.class);
            } else {
                log.error("Connection to Deribit has failed, please check the logs");
            }
        } catch (MalformedURLException | JSONRPC2SessionException e) {
            log.error("Connection to Deribit failed - {}", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }
    private JSONRPC2Response sendRequestToDeribit(final String path, final Map<String, Object> parameters, final int requestId)
            throws MalformedURLException, JSONRPC2SessionException {
        JSONRPC2Session session = new JSONRPC2Session(new URL(apiBaseUri));

        session.setConnectionConfigurator(new OAuthConnectionConfigurer(this.getOAuthToken()));

        JSONRPC2Request request = new JSONRPC2Request(path, parameters, requestId);
        return session.send(request);
    }
    private OAuthToken getOAuthToken()
    {
        return oAuthorisationService.getAccessToken();
    }
}
