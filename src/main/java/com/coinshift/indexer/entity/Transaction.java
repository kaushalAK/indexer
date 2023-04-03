package com.coinshift.indexer.entity;

import java.math.BigInteger;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties (allowGetters = true, ignoreUnknown = true)
public class Transaction {
    private BigInteger id;

    private String hash;

    private String nonce;

    private String blockHash;

    private String blockNumber;

    private String transactionIndex;

    private String from;

    private String to;

    private String value;

    private String gasPrice;

    private String gas;

    private String input;

    private String creates;

    private String publicKey;

    private String raw;
}
