package com.coinshift.indexer.web3.subscriptions;

import java.math.BigInteger;
import java.util.Collections;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import com.coinshift.indexer.entity.Blocks;
import com.coinshift.indexer.producer.BlockMessageProducer;
import com.coinshift.indexer.producer.TxnMessageProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.reactivex.Flowable;

@Component
public class BlockSubscription {
    @Autowired
    BlockMessageProducer messageProducer;

    @Autowired
    TxnMessageProducer txnMessageProducer;

    final Web3j client = Web3j.build(new HttpService("${url}"));

    public void subscribeToBlocksOnEth() {
        client.replayPastBlocksFlowable(DefaultBlockParameter.valueOf(BigInteger.ONE), DefaultBlockParameter.valueOf(BigInteger.TEN), true)
              .subscribe(this::submitToQueueForProcessing);
    }


    public void getNewBlocksWdTxns() {
        client.blockFlowable(true)
              .subscribe(this::submitToQueueForProcessing);
    }

    public void getAllPastBlockTillLatest() {
        client.replayPastBlocksFlowable(DefaultBlockParameter.valueOf(BigInteger.ONE), true, (Flowable<EthBlock>) client.blockFlowable(true).subscribe(this::submitToQueueForProcessing))
              .subscribe(this::submitToQueueForProcessing);
    }

    private void submitToQueueForProcessing(EthBlock block) throws JsonProcessingException {

        checkForTransactionsInBlockAndSubmit(block);
        Blocks blocks = removeTxnData(block);

        messageProducer.configurationInit(blocks);
    }

    private Blocks removeTxnData(EthBlock block) {

        Blocks blockEntity = new Blocks();
        blockEntity.setHash(block.getResult().getHash());
        blockEntity.setNonce(String.valueOf(block.getResult().getNonce()));
        blockEntity.setDifficulty(String.valueOf(block.getResult().getDifficulty()));
        blockEntity.setReceiptsRoot(block.getResult().getReceiptsRoot());
        blockEntity.setAuthor(block.getResult().getAuthor());
        blockEntity.setMiner(block.getResult().getMiner());
        blockEntity.setMixHash(block.getResult().getMixHash());
        blockEntity.setGasLimit(String.valueOf(block.getResult().getGasLimit()));
        blockEntity.setGasUsed(String.valueOf(block.getResult().getGasUsed()));
        blockEntity.setTotalDifficulty(String.valueOf(block.getResult().getTotalDifficulty()));
        blockEntity.setExtraData(block.getResult().getExtraData());
        blockEntity.setSize(String.valueOf(block.getResult().getSize()));
        blockEntity.setTimestamp(String.valueOf(block.getResult().getTimestamp()));
        blockEntity.setNumber(String.valueOf(block.getResult().getNumber()));
        blockEntity.setParentHash(block.getResult().getParentHash());
        blockEntity.setSha3Uncles(block.getResult().getSha3Uncles());
        blockEntity.setLogsBloom(block.getResult().getLogsBloom());
        blockEntity.setTransactionsRoot(block.getResult().getTransactionsRoot());
        blockEntity.setStateRoot(block.getResult().getStateRoot());
        blockEntity.setTransactions(Collections.EMPTY_LIST);
        blockEntity.setUncles(block.getResult()
                                   .getUncles());
        blockEntity.setSealFields(block.getResult().getSealFields());

        return blockEntity;
    }

    private void checkForTransactionsInBlockAndSubmit(EthBlock block) throws JsonProcessingException {

        for(EthBlock.TransactionResult txr : block.getBlock()
                                                  .getTransactions()){
            txnMessageProducer.configurationInitFromBlockTransactions(txr);
        }
    }
}
