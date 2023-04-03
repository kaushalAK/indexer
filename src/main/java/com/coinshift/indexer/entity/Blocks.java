package com.coinshift.indexer.entity;

import java.util.List;
import org.web3j.protocol.core.methods.response.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties (allowGetters = true, ignoreUnknown = true)
public class Blocks {

    private String number;
    private String hash;
    private String parentHash;
    private String nonce;
    private String sha3Uncles;
    private String logsBloom;
    private String transactionsRoot;
    private String stateRoot;
    private String receiptsRoot;
    private String author;
    private String miner;
    private String mixHash;
    private String difficulty;
    private String totalDifficulty;
    private String extraData;
    private String size;
    private String gasLimit;
    private String gasUsed;
    private String timestamp;
    private List<Transaction> transactions;
    private List<String> uncles;
    private List<String> sealFields;

}
