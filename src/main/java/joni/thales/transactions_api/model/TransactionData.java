package joni.thales.transactions_api.model;

import jakarta.persistence.*;

/**
 *  Key-value data related to a transaction.
 *
 *  Created by Joni Pohjaniemi
 */

@Entity
public class TransactionData {

    @Id
    @GeneratedValue
    private Integer dataId;

    @Column
    private String key;

    @Column
    private String value;

    @ManyToOne
    private Transaction transaction;

    protected TransactionData() {}

    public TransactionData(String key, String value, Transaction transaction) {
        this.key = key;
        this.value = value;
        this.transaction = transaction;
    }
}
