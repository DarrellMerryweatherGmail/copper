package com.copper.coppertest.persistence.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * The JPA entity that is used for storing the portfolio information in the DB
 */
@Entity
@Table(name = PortfolioEntityDefinition.TABLE_NAME)
public class PortfolioEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = PortfolioEntityDefinition.Column.CURRENCY)
    private String currency;

    @Column(name = PortfolioEntityDefinition.Column.BALANCE)
    private BigDecimal balance;

    @Column(name = PortfolioEntityDefinition.Column.AVAILABLE_WITHDRAWAL_FUNDS)
    private BigDecimal availableWithdrawalFunds;
    @Column(name = PortfolioEntityDefinition.Column.AVAILABLE_FUNDS)
    private BigDecimal availableFunds;

    public String getCurrency() { return this.currency; }
    public void setCurrency(final String currency) { this.currency = currency; }

    public BigDecimal getBalance() { return this.balance; }
    public void setBalance(final BigDecimal balance) { this.balance = balance; }

    public BigDecimal getAvailableWithdrawalFunds() { return this.availableWithdrawalFunds; }
    public void setAvailableWithdrawalFunds(final BigDecimal availableWithdrawalFunds) { this.availableWithdrawalFunds = availableWithdrawalFunds; }

    public BigDecimal getAvailableFunds() { return this.availableFunds; }
    public void setAvailableFunds(final BigDecimal availableFunds) { this.availableFunds = availableFunds; }
}
