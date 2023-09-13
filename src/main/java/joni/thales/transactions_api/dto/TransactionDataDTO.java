package joni.thales.transactions_api.dto;

import jakarta.validation.constraints.Pattern;
import org.springframework.lang.NonNull;

import static joni.thales.transactions_api.validation.ValidationPatterns.REGEX_ALPHANUMERIC;

/**
 *  Data Transfer Object for data key-value pairs.
 */
public class TransactionDataDTO {

    @NonNull
    @Pattern(regexp = REGEX_ALPHANUMERIC)
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
