package com.copper.coppertest.configuration;

import com.copper.coppertest.deribit.account.model.AccountDto;
import com.copper.coppertest.deribit.account.model.PortfolioDto;
import com.copper.coppertest.persistence.entity.AccountEntity;
import com.copper.coppertest.persistence.entity.PortfolioEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfiguration
{
    @Bean
    public ModelMapper getModelMapper()
    {
        final ModelMapper modelMapper = new ModelMapper();

        final Converter<AccountDto, AccountEntity> accountDtoToAccountEntityConverter = new AbstractConverter<>() {
            @Override
            protected AccountEntity convert(AccountDto source) {
                final AccountEntity accountEntity = new AccountEntity();
                accountEntity.setEmail(source.getEmail());
                accountEntity.setId(source.getId());
                accountEntity.setType(source.getType());
                accountEntity.setUsername(source.getEmail());
                final Set<PortfolioEntity> portfolioEntities = Optional.ofNullable(source.getPortfolio())
                        .map(portfolios -> portfolios.values().stream()
                                    .map(portfolio -> modelMapper.map(portfolio, PortfolioEntity.class))
                                    .collect(Collectors.toSet()))
                        .orElse(Collections.emptySet());
                accountEntity.setPortfolios(portfolioEntities);
                return accountEntity;
            }
        };
        final Converter<AccountEntity, AccountDto> accountEntityToAccountDtoConverter = new AbstractConverter<>() {
            @Override
            protected AccountDto convert(AccountEntity source) {
                final AccountDto accountDto = new AccountDto();
                accountDto.setEmail(source.getEmail());
                accountDto.setType(source.getType());
                accountDto.setId(source.getId());
                accountDto.setUsername(source.getUsername());

                final Map<String, PortfolioDto> portalfolioDtos = Optional.ofNullable(source.getPortfolios())
                        .map(portfolioEntities -> portfolioEntities
                            .stream()
                            .map(portfolioEntity -> modelMapper.map(portfolioEntity, PortfolioDto.class))
                            .collect(Collectors.toMap(PortfolioDto::getCurrency, Function.identity())))
                        .orElse(Collections.emptyMap());
                accountDto.setPortfolio(portalfolioDtos);

                return accountDto;
            }
        };

        modelMapper.addConverter(accountDtoToAccountEntityConverter);
        modelMapper.addConverter(accountEntityToAccountDtoConverter);
        return modelMapper;
    }

    @Bean
    public ObjectMapper getObjectMapper() { return new ObjectMapper(); }
}
