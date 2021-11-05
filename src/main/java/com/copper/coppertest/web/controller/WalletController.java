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

@RestController
@RequestMapping("/wallet")
public class WalletController
{
    private final DeribitWalletService deribitWalletService;

    public WalletController(final DeribitWalletService deribitWalletService)
    {
        this.deribitWalletService = deribitWalletService;
    }

    @GetMapping("/deposits/{currency}")
    public ResponseEntity<List<DepositDto>> getDeposits(@PathVariable final String currency)
    {
        return ResponseEntity.ok(deribitWalletService.getDeposits(currency));
    }
    @GetMapping("/withdrawals/{currency}")
    public ResponseEntity<List<WithdrawalDto>> getWithdrawals(@PathVariable final String currency)
    {
        return ResponseEntity.ok(deribitWalletService.getWithdrawals(currency));
    }
    @PostMapping("/transfers/{currency}")
    public ResponseEntity<TransferToSubaccountDto> transfer(
            @PathVariable final String currency,
            @RequestBody final TransferRequest transferRequest)
    {
        return ResponseEntity.ok(deribitWalletService.transfer(currency, transferRequest));
    }
}
