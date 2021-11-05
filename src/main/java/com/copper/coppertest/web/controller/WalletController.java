package com.copper.coppertest.web.controller;

import com.copper.coppertest.deribit.service.DeribitWalletService;
import com.copper.coppertest.deribit.wallet.model.DepositDto;
import com.copper.coppertest.deribit.wallet.model.TransferRequest;
import com.copper.coppertest.deribit.wallet.model.TransferToSubaccountDto;
import com.copper.coppertest.deribit.wallet.model.WithdrawalDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * A {@link RestController} that is used for any Deribit wallet operations
 */
@RestController
@RequestMapping("/wallet")
public class WalletController
{
    private final DeribitWalletService deribitWalletService;

    /**
     * Construct an instance of the controller, passing in the {@link DeribitWalletService}
     * @param deribitWalletService the {@link DeribitWalletService}
     */
    public WalletController(final DeribitWalletService deribitWalletService)
    {
        this.deribitWalletService = deribitWalletService;
    }

    /**
     * Get the deposits associated with the account tied to the client-id and client-secret configured in the properties
     * @param currency the currency to retrieve the deposits for
     * @return a {@link List} of {@link DepositDto} that have been found
     */
    @GetMapping("/deposits/{currency}")
    public ResponseEntity<List<DepositDto>> getDeposits(@PathVariable final String currency)
    {
        return ResponseEntity.ok(deribitWalletService.getDeposits(currency));
    }

    /**
     * Get the withdrawals assocaited with the account tied to the client-id and client-secret configured in the properties
     * @param currency the currency to retrieve the withdrawals for
     * @return a {@link List} of {@link WithdrawalDto} that have been found
     */
    @GetMapping("/withdrawals/{currency}")
    public ResponseEntity<List<WithdrawalDto>> getWithdrawals(@PathVariable final String currency)
    {
        return ResponseEntity.ok(deribitWalletService.getWithdrawals(currency));
    }

    /**
     * Create a transfer to a sub-account for the given currency
     * @param currency currency
     * @param transferRequest the {@link TransferRequest} that details the destination and amount of the transfer
     * @return the {@link TransferToSubaccountDto} that details the response of the transfer
     */
    @PostMapping("/transfers/{currency}")
    public ResponseEntity<TransferToSubaccountDto> transfer(
            @PathVariable final String currency,
            @RequestBody final TransferRequest transferRequest)
    {
        return ResponseEntity.ok(deribitWalletService.transfer(currency, transferRequest));
    }
}
