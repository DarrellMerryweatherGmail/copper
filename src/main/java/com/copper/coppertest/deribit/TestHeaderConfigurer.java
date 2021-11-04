package com.copper.coppertest.deribit;

import com.thetransactioncompany.jsonrpc2.client.ConnectionConfigurator;

import java.net.HttpURLConnection;
import java.util.List;

public class TestHeaderConfigurer implements ConnectionConfigurator
{
    @Override
    public void configure(HttpURLConnection connection) {
        System.out.println("*****DEBUG - Configure");
        connection.setRequestProperty("test", "testvalue");
    }
}
