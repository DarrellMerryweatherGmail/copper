package com.copper.coppertest.persistence.entity;


/**
 * A set of constants that holds the necessary information that relate to the {@link PortfolioEntity}
 */
public final class PortfolioEntityDefinition
{
    public static final String TABLE_NAME = "portfolio";

    private PortfolioEntityDefinition() {}

    public static final class Column
    {
        public static final String CURRENCY = "currency";
        public static final String ACCOUNT_ID = "account_id";
        public static final String BALANCE = "balance";
        public static final String AVAILABLE_WITHDRAWAL_FUNDS = "available_withdrawal_funds";
        public static final String AVAILABLE_FUNDS = "available_funds";

        private Column() {}
    }
}
