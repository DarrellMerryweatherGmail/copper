package com.copper.coppertest.deribit.account.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

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
