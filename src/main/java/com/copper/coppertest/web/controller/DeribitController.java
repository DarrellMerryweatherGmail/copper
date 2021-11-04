package com.copper.coppertest.web.controller;

import com.copper.coppertest.deribit.service.DeribitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deribit")
public class DeribitController
{
    private DeribitService deribitService;

    public DeribitController(final DeribitService deribitService)
    {
        this.deribitService = deribitService;
    }

    @GetMapping
    public ResponseEntity<Void> get()
    {
        deribitService.makeCall();
        return ResponseEntity.ok().build();
    }
}
