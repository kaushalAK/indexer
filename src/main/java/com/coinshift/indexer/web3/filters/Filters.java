package com.coinshift.indexer.web3.filters;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;

@Component
public class Filters {

    final Web3j client = Web3j.build(new HttpService("https://sepolia.infura.io/v3/9d79c32f22084fe2b87591024b1a8078"));

    final EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
            DefaultBlockParameterName.LATEST, "0x5120802FB50d82224BD73DaA562Ff0555D1bee01");


    public void getLogs(){
        client.ethLogFlowable(filter).subscribe(log -> {
        });
    }
}
