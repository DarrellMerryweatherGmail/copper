package com.copper.coppertest.deribit.service;

import com.copper.coppertest.deribit.wallet.model.DepositDto;
import com.copper.coppertest.deribit.wallet.model.TransferRequest;
import com.copper.coppertest.deribit.wallet.model.WithdrawalDto;

import java.util.List;

public interface DeribitWalletService
{
    List<DepositDto> getDeposits(String currency);

    List<WithdrawalDto> getWithdrawals(String currency);

    void transfer(String currency, TransferRequest transferRequest);
}
