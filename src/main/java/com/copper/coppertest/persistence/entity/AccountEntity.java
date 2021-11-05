package com.copper.coppertest.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * The JPA entity that is used for storing the account information in the DB
 */
@Entity
@Table(name = AccountEntityDefinition.TABLE_NAME)
public class AccountEntity {
    @Id
    @Column(name = AccountEntityDefinition.Column.ID)
    private String id;

    @Column(name = AccountEntityDefinition.Column.TYPE)
    private String type;

    @Column(name = AccountEntityDefinition.Column.USERNAME)
    private String username;

    @Column(name = AccountEntityDefinition.Column.EMAIL)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = PortfolioEntityDefinition.Column.ACCOUNT_ID)
    private Set<PortfolioEntity> portfolios;

    public String getId() { return this.id; }
    public void setId(final String id) { this.id = id; }

    public String getType() { return this.type; }
    public void setType(final String type) { this.type = type; }

    public String getUsername() { return this.username; }
    public void setUsername(final String username) { this.username = username; }

    public String getEmail() { return this.email; }
    public void setEmail(final String email) { this.email = email; }

    public Set<PortfolioEntity> getPortfolios() { return this.portfolios; }
    public void setPortfolios(final Set<PortfolioEntity> portfolios) { this.portfolios = portfolios; }
}
