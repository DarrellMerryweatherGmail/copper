package com.copper.coppertest.deribit.wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The wrapper of the "data" object that is returned when requesting the deposits from Deribit
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositResponse
{
    private DepositDto[] data;
}
