package com.coinshift.indexer.web3.subscriptions;

import java.math.BigInteger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import com.coinshift.indexer.producer.BlockMessageProducer;
import com.coinshift.indexer.producer.TxnMessageProducer;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class TransactionSubscription {
    @Autowired
    TxnMessageProducer messageProducer;

    final Web3j client = Web3j.build(new HttpService("${url}"));

    public void subscribeToTxnsOnEth() {
        client.transactionFlowable()
              .subscribe(this::submitToQueueForProcessing);
    }

    public void subscribePendingTxns() {
        client.pendingTransactionFlowable()
              .subscribe(this::submitToQueueForProcessing);
    }

    public void getAllBlockTxns(DefaultBlockParameter start, DefaultBlockParameter end) {
        client.replayPastTransactionsFlowable(start, end)
              .subscribe(this::submitToQueueForProcessing);
    }

    private void submitToQueueForProcessing(Transaction tx) throws JsonProcessingException {
        messageProducer.configurationInit(tx);
    }
}
