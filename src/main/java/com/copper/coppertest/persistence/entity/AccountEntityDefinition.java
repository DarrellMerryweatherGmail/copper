package com.copper.coppertest.persistence.entity;

/**
 * A set of constants that holds the necessary information that relate to the {@link AccountEntity}
 */
public final class AccountEntityDefinition
{
    public static final String TABLE_NAME = "account";

    private AccountEntityDefinition(){}

    public static final class Column {
        public static final String ID = "id";
        public static final String TYPE = "type";
        public static final String USERNAME = "username";
        public static final String EMAIL = "email";

        private Column() {}
    }
}
