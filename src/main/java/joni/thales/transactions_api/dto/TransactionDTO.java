package joni.thales.transactions_api.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *  Data Transfer Object for transaction.
 */
public class TransactionDTO {

    private static final Logger logger = LoggerFactory.getLogger(TransactionDTO.class.getName());

    @NonNull
    private Integer id;
    @NonNull
    private String timestamp;
    @NonNull
    private String type;
    @NonNull
    private String actor;
    private List<TransactionDataDTO> data;

    private static final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    protected TransactionDTO() {}

    public TransactionDTO(Integer id, Timestamp timestamp, String type, String actor, List<TransactionDataDTO> data) {
        logger.debug("Creating DTO for transaction {}", id);
        this.id = id;
        this.timestamp = dateFormat.format(timestamp);
        this.type = type;
        this.actor = actor;
        this.data = data;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull String timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getActor() {
        return actor;
    }

    public void setActor(@NonNull String actor) {
        this.actor = actor;
    }

    public List<TransactionDataDTO> getData() {
        return data;
    }

    public void setData(List<TransactionDataDTO> data) {
        this.data = data;
    }
}
