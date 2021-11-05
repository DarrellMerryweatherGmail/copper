package com.copper.coppertest.deribit.account.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

/**
 * DTO that represents the Account information that is retrieved from Deribit
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto
{
    private String id;
    private String type;
    private String username;
    private String email;
    private Map<String, PortfolioDto> portfolio;
}
