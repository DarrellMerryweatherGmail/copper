package com.copper.coppertest.web.controller;

import com.copper.coppertest.deribit.account.model.AccountDto;
import com.copper.coppertest.service.AccountService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * A {@link RestController} that is used to perform any account specific tasks, retrieving (and caching) the data
 * from the DB, and deleting any accounts from the persistence
 */
@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountController
{
    private final AccountService accountService;
    public static final String ACCOUNT_FORMAT = "/accounts/{id}";

    /**
     * Create an instance of the controller, passing in the {@link AccountService}
     * @param accountService
     */
    public AccountController(final AccountService accountService)
    {
        this.accountService = accountService;
    }

    /**
     * Create an {@link AccountDto} in the persistence layer
     * @param accountDto the {@link AccountDto} to create
     * @param builder a Spring {@link UriComponentsBuilder} that is used for generating the Location header for the response
     * @return a blank {@link ResponseEntity} being returned, as the body will be empty, but the Location header will
     * be set for the created resource, and a response status of CREATED (201)
     */
    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody @NotNull AccountDto accountDto, final UriComponentsBuilder builder)
    {
        accountService.saveAccount(accountDto);
        final var uri = builder.path(ACCOUNT_FORMAT).buildAndExpand(accountDto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Get a specific {@link AccountDto} for the given id
     * @param id the id of the {@link AccountDto}
     * @return the {@link AccountDto} that has been found, and a response status of OK (200) if one is found, or a
     * NOT_FOUND (404) if no {@link AccountDto} is found
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable final String id)
    {
        return Optional.ofNullable(accountService.getAccount(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete the {@link AccountDto} for the given id
     * @param id the id of the {@link AccountDto}
     * @return an empty response, and a response status of NO_CONTENT (204)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable final String id)
    {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
