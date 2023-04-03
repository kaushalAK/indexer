package com.coinshift.indexer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import org.springframework.messaging.Message;
import com.coinshift.indexer.service.TxnService;

@Component
public class TxnMessageConsumer {

    @Autowired
    TxnService service;

    @JmsListener(destination = "dev-incoming-txns", containerFactory = "abc", concurrency = "1")
    public void receiveTxnAndProcess(@Payload Message<?> msg) {
        service.submitTxnForIndexing(msg.getPayload());
    }
}
