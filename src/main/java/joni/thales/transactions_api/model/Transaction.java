package joni.thales.transactions_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

/**
 *  Transaction from the authentication service.
 *
 *  @author Joni Pohjaniemi
 */

@Entity
public class Transaction {

    @Id
    private Integer id;

    @Column
    private Timestamp timestamp;

    @Column(length = 100)
    private String type;

    @Column(length = 100)
    private String actor;

    protected Transaction() {}

    public Transaction(Integer id, Timestamp timestamp, String type, String actor) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.actor = actor;
    }

}
