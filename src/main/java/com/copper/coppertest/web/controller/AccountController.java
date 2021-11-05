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

@Slf4j
@RestController
@RequestMapping("/accounts")
public class AccountController
{
    private final AccountService accountService;
    public static final String ACCOUNT_FORMAT = "/accounts/{id}";

    public AccountController(final AccountService accountService)
    {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody @NotNull AccountDto accountDto, final UriComponentsBuilder builder)
    {
        accountService.saveAccount(accountDto);
        final var uri = builder.path(ACCOUNT_FORMAT).buildAndExpand(accountDto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable final String id)
    {
        return Optional.ofNullable(accountService.getAccount(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable final String id)
    {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
