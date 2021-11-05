package com.copper.coppertest.web.controller;

import com.copper.coppertest.deribit.service.DeribitAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deribit")
public class DeribitController
{
    private final DeribitAccountService deribitService;

    public DeribitController(final DeribitAccountService deribitService)
    {
        this.deribitService = deribitService;
    }

    @GetMapping
    @RequestMapping("/sync-accounts")
    public ResponseEntity<Void> getSubaccount()
    {
        deribitService.syncAccounts();
        return ResponseEntity.accepted().build();
    }
}
