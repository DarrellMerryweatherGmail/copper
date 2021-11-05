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

/**
 * Main spring boot application configuration
 */
@Configuration
public class ApplicationConfiguration
{
    /**
     * A {@link ModelMapper} bean that is used to convert one object to another
     * @return a {@link ModelMapper}
     */
    @Bean
    public ModelMapper getModelMapper()
    {
        final ModelMapper modelMapper = new ModelMapper();

        // Create a custom converter to convert between the AccountDto and the AccountEntity
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
        // Create a custom converter to convert between the AccountEntity and the AccountDto
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
        //Add the converters to the ModelMapper
        modelMapper.addConverter(accountDtoToAccountEntityConverter);
        modelMapper.addConverter(accountEntityToAccountDtoConverter);
        return modelMapper;
    }

    /**
     * Create a Jackson {@link ObjectMapper} that is used to convert an object to JSON or the other way around
     * @return the {@link ObjectMapper}
     */
    @Bean
    public ObjectMapper getObjectMapper() { return new ObjectMapper(); }
}
