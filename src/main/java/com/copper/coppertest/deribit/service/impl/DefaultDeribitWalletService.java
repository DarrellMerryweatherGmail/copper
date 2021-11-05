package com.copper.coppertest.deribit.service.impl;

import com.copper.coppertest.deribit.oauth.OAuthConnectionConfigurer;
import com.copper.coppertest.deribit.oauth.model.OAuthToken;
import com.copper.coppertest.deribit.service.DeribitErrorMessages;
import com.copper.coppertest.deribit.service.DeribitWalletService;
import com.copper.coppertest.deribit.service.OAuthService;
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

/**
 * Concrete implementation of the {@link DeribitWalletService}
 */
@Slf4j
@Service
public class DefaultDeribitWalletService implements DeribitWalletService
{
    private final OAuthService oAuthService;
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
            final OAuthService oAuthService,
            final ObjectMapper objectMapper)
    {
        this.oAuthService = oAuthService;
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
            parameters.put(WalletParameters.CURRENCY.getParameterName(), currency.toUpperCase());

            final JSONRPC2Response response = sendRequestToDeribit(depositsPath, parameters, 2);

            if (response.indicatesSuccess())
            {
                final DepositResponse depositResponse = objectMapper.convertValue(response.getResult(), DepositResponse.class);
                return List.of(depositResponse.getData());
            } else {
                log.error(DeribitErrorMessages.CONNECTION_FAILED_CHECK_LOGS);
            }
        } catch (MalformedURLException | JSONRPC2SessionException e) {
            log.error(DeribitErrorMessages.CONNECTION_FAILED_TEMPLATE, e.getLocalizedMessage());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public List<WithdrawalDto> getWithdrawals(final String currency)
    {
        try {
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put(WalletParameters.CURRENCY.getParameterName(), currency.toUpperCase());

            final JSONRPC2Response response = sendRequestToDeribit(withdrawalsPath, parameters, 2);

            if (response.indicatesSuccess())
            {
                final WithdrawalResponse
                        withdrawalResponse = objectMapper.convertValue(response.getResult(), WithdrawalResponse.class);
                return List.of(withdrawalResponse.getData());
            } else {
                log.error(DeribitErrorMessages.CONNECTION_FAILED_CHECK_LOGS);
            }
        } catch (MalformedURLException | JSONRPC2SessionException e) {
            log.error(DeribitErrorMessages.CONNECTION_FAILED_TEMPLATE, e.getLocalizedMessage());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    @Override
    public TransferToSubaccountDto transfer(final String currency, final TransferRequest transferRequest)
    {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(WalletParameters.CURRENCY.getParameterName(), currency.toUpperCase());
        parameters.put(WalletParameters.DESTINATION.getParameterName(), transferRequest.getDestination());
        parameters.put(WalletParameters.AMOUNT.getParameterName(), transferRequest.getAmount());

        try {
            final JSONRPC2Response response = sendRequestToDeribit(submitTransferToSubaccountPath, parameters, 3);

            if (response.indicatesSuccess())
            {
                return objectMapper.convertValue(response.getResult(), TransferToSubaccountDto.class);
            } else {
                log.error(DeribitErrorMessages.CONNECTION_FAILED_CHECK_LOGS);
            }
        } catch (MalformedURLException | JSONRPC2SessionException e) {
            log.error(DeribitErrorMessages.CONNECTION_FAILED_TEMPLATE, e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Send the JSON-RPC request to Deribit
     * @param path the specific JSON-RPC path to be used
     * @param parameters the parameters that are passed as part of the request
     * @param requestId the id of the request
     * @return the {@link JSONRPC2Response} returned from making the request
     * @throws MalformedURLException an exception in case the apiBaseUri is incorrectly formed
     * @throws JSONRPC2SessionException any exception that is raised when making the request to Deribit
     */
    private JSONRPC2Response sendRequestToDeribit(final String path, final Map<String, Object> parameters, final int requestId)
            throws MalformedURLException, JSONRPC2SessionException {
        JSONRPC2Session session = new JSONRPC2Session(new URL(apiBaseUri));

        session.setConnectionConfigurator(new OAuthConnectionConfigurer(this.getOAuthToken()));

        JSONRPC2Request request = new JSONRPC2Request(path, parameters, requestId);
        return session.send(request);
    }

    /**
     * Get a valid {@link OAuthToken} from the {@link OAuthService}
     * @return a valid {@link OAuthToken}
     */
    private OAuthToken getOAuthToken()
    {
        return oAuthService.getAccessToken();
    }

    /**
     * A list of possible parameters that can be used in the wallet API's
     */
    enum WalletParameters
    {
        CURRENCY("currency"),
        DESTINATION("destination"),
        AMOUNT("amount");

        private final String parameterName;

        WalletParameters(final String parameterName)
        {
            this.parameterName = parameterName;
        }
        public String getParameterName() { return this.parameterName; }
    }
}
