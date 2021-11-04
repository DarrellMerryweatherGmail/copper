package com.copper.coppertest.model;

import lombok.Data;

import java.util.List;

@Data
public class AccountDto
{
    private String id;
    private String type;
    private String username;
    private String email;
    private List<PortfolioDto> portfolios;
}
