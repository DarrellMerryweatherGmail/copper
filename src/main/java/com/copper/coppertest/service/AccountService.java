package com.copper.coppertest.service;

import com.copper.coppertest.deribit.account.model.AccountDto;

public interface AccountService
{
    void saveAccount(final AccountDto account);
    AccountDto getAccount(final String accountId);
    void deleteAccount(final String accountId);
}
