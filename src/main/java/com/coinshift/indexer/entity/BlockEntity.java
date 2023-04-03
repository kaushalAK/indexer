package com.coinshift.indexer.entity;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Entity
@Data
@EntityListeners (AuditingEntityListener.class)
@Table (name = "blocks")
@JsonIgnoreProperties (value = { "id", "createdAt", "updatedAt" }, allowGetters = true, ignoreUnknown = true)
public class BlockEntity {
    @Id
    @Column (nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column (nullable = true)
    private String number;

    @Column (nullable = true)
    private String hash;

    @Column (nullable = true)
    private String parentHash;

    @Column (nullable = true)
    private String nonce;

    @Column (nullable = true)
    private String sha3uncles;

    @Column (nullable = true)
    private String logsBloom;

    @Column (nullable = true)
    private String transactionsRoot;

    @Column (nullable = true)
    private String stateRoot;

    @Column (nullable = true)
    private String receiptsRoot;

    @Column (nullable = true)
    private String author;

    @Column (nullable = true)
    private String miner;

    @Column (nullable = true)
    private String mixHash;

    @Column (nullable = true)
    private String difficulty;

    @Column (nullable = true)
    private String totalDifficulty;

    @Column (nullable = true)
    private String extraData;

    @Column (nullable = true)
    private String blockSize;

    @Column (nullable = true)
    private String gasLimit;

    @Column (nullable = true)
    private String gasUsed;

    @Column (nullable = true)
    private String timestamp;

    @Column (nullable = true)
    private String noOfTransactions;

    @Column (nullable = true)
    private String noOfUncles;

}
