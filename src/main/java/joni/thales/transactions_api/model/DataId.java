package joni.thales.transactions_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Objects;

import static joni.thales.transactions_api.validation.ValidationPatterns.REGEX_ALPHANUMERIC;

/**
 *  Composite primary key for key-value pairs.
 */
@Embeddable
public class DataId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Transaction transaction;
    @Column
    @Pattern(regexp = REGEX_ALPHANUMERIC)
    private String dataKey;

    public DataId() {}

    public DataId(Transaction transaction, String dataKey) {
        this.transaction = transaction;
        this.dataKey = dataKey;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataId that = (DataId) o;
        return transaction.equals(that.transaction) && dataKey.equals(that.dataKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction, dataKey);
    }

    @Override
    public String toString() {
        return "CompositeDataId{" +
                "transaction=" + transaction +
                ", dataKey='" + dataKey + '\'' +
                '}';
    }
}
