package com.coinshift.indexer.component.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseStatus {

    public enum Status{
        STARTED_INDEXING_TRANSACTIONS_FROM_NODE,
        STARTED_INDEXING_PENDING_TRANSACTIONS_FROM_NODE,
        STARTED_INDEXING_BLOCKS_FROM_NODE,
        STARTED_INDEXING_TRANSACTIONS_FROM_BLOCK_RANGE,
        STARTED_INDEXING_TRANSACTIONS_PENDING,
        STARTED_INDEXING_BLOCKS_WITH_TRANSACTIONS,
        GETTING_TXNS,
        GETTING_BLOCK_DETAILS
    }

    @JsonProperty ("status")
    private Status status;

    @JsonProperty ("data")
    private Object data;

}
