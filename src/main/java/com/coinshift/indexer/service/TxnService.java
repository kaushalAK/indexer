package com.coinshift.indexer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coinshift.indexer.component.system.ResponseStatus;
import com.coinshift.indexer.entity.TransactionEntity;
import com.coinshift.indexer.entity.Transaction;
import com.coinshift.indexer.repository.TxnRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

@Service
public class TxnService {

    @Autowired
    TxnRepo txnRepo;


    @SneakyThrows
    public void submitTxnForIndexing(Object payload){
        Transaction tx = new ObjectMapper().readValue((String) payload, Transaction.class);
        TransactionEntity txEntity = createTxEntityForPersistence(tx);
        System.out.println(tx);
        txnRepo.save(txEntity);
    }

    private TransactionEntity createTxEntityForPersistence(Transaction tx) {
        TransactionEntity txEntity = new TransactionEntity();
        txEntity.setCreates(tx.getCreates());
        txEntity.setBlockHash(tx.getBlockHash());
        txEntity.setHash(tx.getHash());
        txEntity.setNonce(String.valueOf(tx.getNonce()));
        txEntity.setBlockNumber(String.valueOf(tx.getBlockNumber()));
        txEntity.setTransactionIndex(String.valueOf(tx.getTransactionIndex()));
        txEntity.setFromAddress(tx.getFrom());
        txEntity.setToAddress(tx.getTo());
        txEntity.setValue(String.valueOf(tx.getValue()));
        txEntity.setGasPrice(String.valueOf(tx.getGasPrice()));
        txEntity.setGas(String.valueOf(tx.getGas()));
        txEntity.setInput(tx.getInput());
        txEntity.setPublicKey(tx.getPublicKey());
        txEntity.setRaw(tx.getRaw());
        return txEntity;
    }

    public ResponseStatus getAddressTxns(String address){
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setData(txnRepo.getTxnByAddress(address));
        return responseStatus;
    }

}
