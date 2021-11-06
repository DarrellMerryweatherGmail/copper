package com.copper.coppertest.web.controller;

import com.copper.coppertest.deribit.service.DeribitAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A {@link RestController} that is used to sync the accounts tied to the client-id and client-secret to the persistence
 */
@RestController
public class DeribitController
{
    private final DeribitAccountService deribitService;

    /**
     * Create an instance of the controller, passing in the {@link DeribitAccountService}
     * @param deribitService the {@link DeribitAccountService}
     */
    public DeribitController(final DeribitAccountService deribitService)
    {
        this.deribitService = deribitService;
    }

    /**
     * This is more of a utility method that allows us to synchronise the accounts tied to the client-id and
     * client-secret to the persistence. The name of the API doesn't really conform to standard rest practices, but this
     * synchronisation would probably be done in a different manner in the real world
     * @return no body is returned, but a status of ACCEPTED (202) is returned
     */
    @GetMapping
    @RequestMapping("/deribit/sync-accounts")
    public ResponseEntity<Void> getSubaccount()
    {
        deribitService.syncAccounts();
        return ResponseEntity.accepted().build();
    }
}
