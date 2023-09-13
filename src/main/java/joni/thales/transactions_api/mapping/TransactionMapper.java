package joni.thales.transactions_api.mapping;

import joni.thales.transactions_api.dto.TransactionDTO;
import joni.thales.transactions_api.dto.TransactionDataDTO;
import joni.thales.transactions_api.model.DataId;
import joni.thales.transactions_api.model.Transaction;
import joni.thales.transactions_api.model.TransactionData;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *   Mappers for converting Transaction and TransactionData to DTO and vice-versa.
 *
 *   DTO -> Entity
 *   Entity -> DTO
 */
public class TransactionMapper {

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(TransactionMapper.class.getName());

    public static TransactionDTO convertToDto(Transaction transaction) {
        logger.debug("Converting transaction {} to DTO. With data {}",
                transaction.getId(),
                transaction.getData());

        // Convert Entity to DTO
        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        // Convert data Entity to data DTO
        List<TransactionDataDTO> dtoData = new ArrayList<>();
        List<TransactionData> entityData = transaction.getData();
        if (entityData != null){
            for (TransactionData transactionData : entityData){
                dtoData.add(convertDataToDTO(transactionData));
            }
            transactionDTO.setData(dtoData);
        }

        return transactionDTO;
    }

    public static Transaction convertToEntity(TransactionDTO transactionDTO) {
        logger.debug("Converting transaction DTO {} to entity. With data {}",
                transactionDTO.getId(),
                transactionDTO.getData());

        // Convert DTO to Entity
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);

        // Convert data DTO to data Entity
        List<TransactionDataDTO> dtoList = transactionDTO.getData();
        if (dtoList != null){
            List<TransactionData> entityData = new ArrayList<>();
            for (TransactionDataDTO transactionDataDTO : dtoList){
                entityData.add(convertDataToEntity(transactionDataDTO, transaction));
            }
            transaction.setData(entityData);
        }

        return transaction;
    }

    public static TransactionData convertDataToEntity(TransactionDataDTO transactionDataDTO, Transaction transaction) {
        logger.debug("Converting data from DTO to entity. BEFORE: Data key {}, data value {}",
                transactionDataDTO.getDataKey(),
                transactionDataDTO.getDataValue()
        );
        final TransactionData transactionData = modelMapper.map(transactionDataDTO, TransactionData.class);
        transactionData.setId(new DataId(
                transaction,
                transactionDataDTO.getDataKey()
        ));

        logger.debug("AFTER: Data key {}, data value {}",
                transactionData.getId().getDataKey(),
                transactionData.getDataValue()
        );

        return transactionData;
    }

    public static TransactionDataDTO convertDataToDTO(TransactionData transactionData){
        logger.debug("Converting data from entity to DTO. BEFORE: Txn id {}, Data key {}, data value {}",
                transactionData.getId().getTransaction().getId(),
                transactionData.getId().getDataKey(),
                transactionData.getDataValue()
        );
        final TransactionDataDTO transactionDataDTO = modelMapper.map(transactionData, TransactionDataDTO.class);
        transactionDataDTO.setDataKey(transactionData.getId().getDataKey());

        logger.debug("AFTER: Data key {}, data value {}",
                transactionDataDTO.getDataKey(),
                transactionDataDTO.getDataValue()
        );

        return transactionDataDTO;
    }

}
