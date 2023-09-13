package joni.thales.transactions_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *  Transaction from the authentication service.
 */
@Entity
public class Transaction {

    @Id
    @NaturalId
    private Integer id;

    @Column
    private Timestamp timestamp;

    @Column(length = 100)
    private String type;

    @Column(length = 100)
    private String actor;

    @OneToMany(
            mappedBy = "id.transaction",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<TransactionData> data;

    protected Transaction() {}

    public Transaction(Integer id, Timestamp timestamp, String type, String actor) {
        this(id, timestamp, type, actor, new ArrayList<>());
    }

    public Transaction(Integer id, Timestamp timestamp, String type, String actor, List<TransactionData> data) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.actor = actor;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public List<TransactionData> getData() {
        return data;
    }

    public void setData(List<TransactionData> data) {
        this.data = data;
    }
}
