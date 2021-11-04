create table if not exists portfolio (
    id character varying(255) NOT NULL,
    currency character varying(10),
    account_id character varying(20),
    balance numeric default 0,
    available_withdrawal_funds numeric default 0,
    available_funds numeric default 0,
constraint currency_account_id unique (currency, account_id),
constraint account_id foreign key(account_id) references account(id)
)
