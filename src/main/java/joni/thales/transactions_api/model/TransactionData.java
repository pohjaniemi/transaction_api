package joni.thales.transactions_api.model;

import jakarta.persistence.*;

/**
 *  Key-value data related to a transaction.
 *
 *  @author Joni Pohjaniemi
 */

@Entity
public class TransactionData {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer dataId;

    @Column
    private String dataKey;

    @Column
    private String dataValue;

    @ManyToOne
    private Transaction transaction;

    protected TransactionData() {}

    public TransactionData(String dataKey, String dataValue, Transaction transaction) {
        this.dataKey = dataKey;
        this.dataValue = dataValue;
        this.transaction = transaction;
    }
}
