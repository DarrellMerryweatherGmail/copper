package com.copper.coppertest.deribit.service;

/**
 * An interface that defines the functionality that is used from the account set of API's of Deribit.
 */
public interface DeribitAccountService
{
    /**
     * A way of synchronising the account(s) that have been defined for the account with the client-id and client-secret
     * and the persists this information in the DB
     */
    void syncAccounts();
}
