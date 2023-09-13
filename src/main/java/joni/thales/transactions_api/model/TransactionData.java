package joni.thales.transactions_api.model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 *  Key-value data related to a transaction.
 */
@Entity
public class TransactionData {

    @EmbeddedId
    DataId id;
    @Column
    private String dataValue;

    protected TransactionData() {}

    public TransactionData(DataId dataId, String dataValue) {
        this.id = dataId;
        this.dataValue = dataValue;
    }

    public DataId getId() {
        return id;
    }

    public void setId(DataId id) {
        this.id = id;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionData that = (TransactionData) o;
        return id.equals(that.id) && dataValue.equals(that.dataValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataValue);
    }

    @Override
    public String toString() {
        return "TransactionData{" +
                "id=" + id +
                ", dataValue='" + dataValue + '\'' +
                '}';
    }
}
