package com.coinshift.indexer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import lombok.Data;

@Data
@Component
public class TxnMessageProducer {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void configurationInit(Transaction txnData) throws JsonProcessingException {
        String queueUrl = "dev-incoming-txns";
        jmsTemplate.convertAndSend(queueUrl, objectMapper.writeValueAsString(txnData));
    }

    public void configurationInitFromBlockTransactions(EthBlock.TransactionResult txnData) throws JsonProcessingException {
        String queueUrl = "dev-incoming-txns";
        jmsTemplate.convertAndSend(queueUrl, objectMapper.writeValueAsString(txnData));
    }

}
