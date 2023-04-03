package com.coinshift.indexer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.coinshift.indexer.service.BlockService;

@Component
public class BlockMessageConsumer {

    @Autowired
    BlockService service;

    @JmsListener (destination = "dev-incoming-blocks", containerFactory = "abc", concurrency = "1")
    public void receiveBlocksAndProcess(@Payload Message<?> msg) {
        service.submitBlocksForIndexing(msg.getPayload());
    }
}
