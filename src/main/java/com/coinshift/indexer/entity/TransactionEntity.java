package com.coinshift.indexer.entity;

import java.math.BigInteger;
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
@Table (name = "transactions")
@JsonIgnoreProperties (value = {"id", "createdAt", "updatedAt"},
                       allowGetters = true, ignoreUnknown = true)
public class TransactionEntity {
    @Id
    @Column (nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column (nullable = true)
    private String hash;

    @Column (nullable = true)
    private String nonce;

    @Column (nullable = true)
    private String blockHash;

    @Column (nullable = true)
    private String blockNumber;

    @Column (nullable = true)
    private String transactionIndex;

    @Column (nullable = true)
    private String fromAddress;

    @Column (nullable = true)
    private String toAddress;

    @Column (nullable = true)
    private String value;

    @Column (nullable = true)
    private String gasPrice;

    @Column (nullable = true)
    private String gas;

    @Column (nullable = true)
    private String input;

    @Column (nullable = true)
    private String creates;

    @Column (nullable = true)
    private String publicKey;

    @Column (nullable = true)
    private String raw;
}
