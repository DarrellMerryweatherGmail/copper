package com.copper.coppertest.deribit.oauth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * A simple POJO used to represent the OAuth token returned from the /public/oauth endpoint of Deribit
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthToken
{
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private Integer expiresIn;
    private String scope;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
