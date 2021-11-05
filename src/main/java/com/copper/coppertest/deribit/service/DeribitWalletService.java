package com.copper.coppertest.deribit.service;

import com.copper.coppertest.deribit.wallet.model.DepositDto;
import com.copper.coppertest.deribit.wallet.model.TransferRequest;
import com.copper.coppertest.deribit.wallet.model.TransferToSubaccountDto;
import com.copper.coppertest.deribit.wallet.model.WithdrawalDto;

import java.util.List;

/**
 * A definition of the Deribit wallet service and any functionality that uses the Wallet APIs
 */
public interface DeribitWalletService
{
    /**
     * Get the deposits from Deribit for the given currency
     * @param currency the currency to get the deposits for
     * @return a {@link List} of {@link DepositDto}'s that have been made
     */
    List<DepositDto> getDeposits(String currency);

    /**
     * Get the withdrawals for the given currency
     * @param currency the currency to get the withdrawals for
     * @return a {@link List} of {@link WithdrawalDto}'s that have been made
     */
    List<WithdrawalDto> getWithdrawals(String currency);

    /**
     * Transfer from the main account to a subaccount for the given currency
     * @param currency the currency to make the transfer for
     * @param transferRequest the {@link TransferRequest} used for making the transfer
     * @return the {@link TransferToSubaccountDto} response from the transfer operation
     */
    TransferToSubaccountDto transfer(String currency, TransferRequest transferRequest);
}
