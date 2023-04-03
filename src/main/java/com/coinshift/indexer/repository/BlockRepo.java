package com.coinshift.indexer.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.coinshift.indexer.entity.BlockEntity;
import com.coinshift.indexer.entity.TransactionEntity;

public interface BlockRepo extends JpaRepository<BlockEntity,Long> {

    @Modifying
    @Transactional
    @Query (value = "select * from blocks bk WHERE bk.hash = :hash", nativeQuery = true)
    List<BlockEntity> getBlockDetails(@Param ("hash") String hash);

}
