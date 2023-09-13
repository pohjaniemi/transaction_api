package joni.thales.transactions_api.dto;

import org.springframework.lang.NonNull;

/**
 *  Data Transfer Object for data key-value pairs.
 */
public class TransactionDataDTO {

    @NonNull
    private String dataKey;
    @NonNull
    private String dataValue;

    protected TransactionDataDTO() {}

    public TransactionDataDTO(String dataKey, String dataValue) {
        this.dataKey = dataKey;
        this.dataValue = dataValue;
    }

    @NonNull
    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(@NonNull String dataKey) {
        this.dataKey = dataKey;
    }

    @NonNull
    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(@NonNull String dataValue) {
        this.dataValue = dataValue;
    }

    @Override
    public String toString() {
        return "TransactionDataDTO{" +
                "dataKey='" + dataKey + '\'' +
                ", dataValue='" + dataValue + '\'' +
                '}';
    }
}
