package com.coinshift.indexer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Transaction;
import com.coinshift.indexer.component.system.ResponseStatus;
import com.coinshift.indexer.entity.BlockEntity;
import com.coinshift.indexer.entity.Blocks;
import com.coinshift.indexer.producer.TxnMessageProducer;
import com.coinshift.indexer.repository.BlockRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

@Service
public class BlockService {
    @Autowired
    BlockRepo blockRepo;

    @SneakyThrows
    public void submitBlocksForIndexing(Object payload) {
        Blocks block = new ObjectMapper().readValue((String) payload, Blocks.class);
        BlockEntity blockEntity = createTxEntityForPersistence(block);
        blockRepo.save(blockEntity);
    }

    private BlockEntity createTxEntityForPersistence(Blocks block) {
        BlockEntity blockEntity = new BlockEntity();
        blockEntity.setHash(block.getHash());
        blockEntity.setNonce(block.getNonce());
        blockEntity.setDifficulty(block.getDifficulty());
        blockEntity.setReceiptsRoot(block.getReceiptsRoot());
        blockEntity.setAuthor(block.getAuthor());
        blockEntity.setMiner(block.getMiner());
        blockEntity.setMixHash(block.getMixHash());
        blockEntity.setGasLimit(block.getGasLimit());
        blockEntity.setGasUsed(block.getGasUsed());
        blockEntity.setTotalDifficulty(block.getTotalDifficulty());
        blockEntity.setExtraData(block.getExtraData());
        blockEntity.setBlockSize(block.getSize());
        blockEntity.setTimestamp(block.getTimestamp());
        blockEntity.setNumber(block.getNumber());
        blockEntity.setParentHash(block.getParentHash());
        blockEntity.setSha3uncles(block.getSha3Uncles());
        blockEntity.setLogsBloom(block.getLogsBloom());
        blockEntity.setTransactionsRoot(block.getTransactionsRoot());
        blockEntity.setStateRoot(block.getStateRoot());
        blockEntity.setNoOfTransactions(String.valueOf(block.getTransactions()
                                                          .size()));
        blockEntity.setNoOfUncles(String.valueOf(block.getUncles()
                                                      .size()));
        return blockEntity;
    }

    public ResponseStatus getBlockDetails(String blockHash){
        ResponseStatus status = new ResponseStatus();
        status.setData(blockRepo.getBlockDetails(blockHash));
        return status;
    }
}
