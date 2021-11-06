package com.copper.coppertest.service;

import com.copper.coppertest.deribit.account.model.AccountDto;

import java.util.List;

/**
 * The service that is used to perform operations on the {@link AccountDto}'s, including persisting, retrieving and
 * deleting
 */
public interface AccountService
{
    /**
     * Get all of the current accounts from the persistence
     * @return a {@link List} of {@link AccountDto}'s
     */
    List<AccountDto> getAllAccounts();
    /**
     * Save an {@link AccountDto} to the persistence
     * @param account the {@link AccountDto} to save
     */
    void saveAccount(final AccountDto account);

    /**
     * Retrieve an {@link AccountDto} from the persistence
     * @param accountId the ID of the account to retrieve
     * @return an {@link AccountDto} that matches the give accountId
     */
    AccountDto getAccount(final String accountId);

    /**
     * Delete the {@link AccountDto} for the given accountId
     * @param accountId the ID of the account to delete
     */
    void deleteAccount(final String accountId);
}
