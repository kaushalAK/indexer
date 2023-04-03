package com.coinshift.indexer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.DefaultBlockParameter;
import com.coinshift.indexer.component.system.ResponseStatus;
import com.coinshift.indexer.service.BlockService;
import com.coinshift.indexer.service.TxnService;
import com.coinshift.indexer.web3.subscriptions.BlockSubscription;
import com.coinshift.indexer.web3.subscriptions.TransactionSubscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

@Slf4j
@RestController
@RequestMapping ("/indexer/v1")
@RequiredArgsConstructor (onConstructor_ = {@Autowired })
public class IndexerController {

    private final TransactionSubscription transactionSubscription;

    private final BlockSubscription blockSubscription;

    private final TxnService txnService;

    private final BlockService blockService;

    @GetMapping (value = "/start/transaction/subscription")
    public @ResponseBody
    ResponseEntity<?> handle(){
        ResponseStatus responseStatus = new ResponseStatus();
        transactionSubscription.subscribeToTxnsOnEth();
        responseStatus.setStatus(ResponseStatus.Status.STARTED_INDEXING_TRANSACTIONS_FROM_NODE);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @GetMapping (value = "/start/pending/transaction/subscription")
    public @ResponseBody
    ResponseEntity<?> handlePending(){
        ResponseStatus responseStatus = new ResponseStatus();
        transactionSubscription.subscribePendingTxns();
        responseStatus.setStatus(ResponseStatus.Status.STARTED_INDEXING_TRANSACTIONS_PENDING);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }


    @GetMapping (value = "/start/indexing/transaction/subscription/block/range")
    public @ResponseBody
    ResponseEntity<?> handleBlockRange(@RequestParam (required = true,
                                                      name = "start_block") DefaultBlockParameter start, @RequestParam (required = true,
                                                                                                                        name = "end_block") DefaultBlockParameter end){
        ResponseStatus responseStatus = new ResponseStatus();
        transactionSubscription.getAllBlockTxns(start,end);
        responseStatus.setStatus(ResponseStatus.Status.STARTED_INDEXING_TRANSACTIONS_FROM_BLOCK_RANGE);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }


    @GetMapping (value = "/start/block/subscription")
    public @ResponseBody
    ResponseEntity<?> handleBlock(){
        ResponseStatus responseStatus = new ResponseStatus();
        blockSubscription.subscribeToBlocksOnEth();
        responseStatus.setStatus(ResponseStatus.Status.STARTED_INDEXING_BLOCKS_FROM_NODE);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @GetMapping (value = "/start/block/subscription/w/transactions")
    public @ResponseBody
    ResponseEntity<?> handleBlockAndTransactions(){
        ResponseStatus responseStatus = new ResponseStatus();
        blockSubscription.getNewBlocksWdTxns();
        responseStatus.setStatus(ResponseStatus.Status.STARTED_INDEXING_BLOCKS_WITH_TRANSACTIONS);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }


    @GetMapping (value = "/get/transactions")
    public @ResponseBody
    ResponseEntity<?> handleGetTransactions(@RequestParam (required = true,
                                                           name = "address") String address){
        ResponseStatus responseStatus = txnService.getAddressTxns(address);
        responseStatus.setStatus(ResponseStatus.Status.GETTING_TXNS);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }

    @GetMapping (value = "/get/block")
    public @ResponseBody
    ResponseEntity<?> handleGetBlock(@RequestParam (required = true,
                                                           name = "block_hash") String hash){
        ResponseStatus responseStatus = blockService.getBlockDetails(hash);
        responseStatus.setStatus(ResponseStatus.Status.GETTING_BLOCK_DETAILS);
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);
    }


}
