package com.coinshift.indexer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import com.coinshift.indexer.entity.Blocks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
@Component
public class BlockMessageProducer { @Autowired
JmsTemplate jmsTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void configurationInit(Blocks ethBlock) throws JsonProcessingException {
        String queueUrl = "dev-incoming-blocks";
        jmsTemplate.convertAndSend(queueUrl, objectMapper.writeValueAsString(ethBlock));
    }

}