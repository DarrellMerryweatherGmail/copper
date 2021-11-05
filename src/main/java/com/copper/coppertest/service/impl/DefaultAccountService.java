package com.copper.coppertest.service.impl;

import com.copper.coppertest.deribit.account.model.AccountDto;
import com.copper.coppertest.persistence.entity.AccountEntity;
import com.copper.coppertest.persistence.repository.AccountRepository;
import com.copper.coppertest.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@CacheConfig(cacheNames = {"accounts"})
@Slf4j
@Service
public class DefaultAccountService implements AccountService
{
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public DefaultAccountService(
            final AccountRepository accountRepository,
            final ModelMapper modelMapper)
    {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(key = "#account.id")
    public void saveAccount(final AccountDto account)
    {
        Optional.ofNullable(account)
                .map(nonNullAccount -> modelMapper.map(nonNullAccount, AccountEntity.class))
                .ifPresent(accountRepository::save);
    }

    @Cacheable(unless="#result == null")
    @Override
    public AccountDto getAccount(final String accountId)
    {
        log.debug("Retrieving the account from the DB.");
        return Optional.ofNullable(accountId)
                .flatMap(accountRepository::findById)
                .map(account -> modelMapper.map(account, AccountDto.class))
                .orElse(null);
    }

    @Override
    @CacheEvict
    public void deleteAccount(final String accountId)
    {
        Optional.ofNullable(accountId)
                .ifPresent(accountRepository::deleteById);
    }
}
