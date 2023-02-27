package com.oegr.ProgramaLealtadPR.repository;

import com.oegr.ProgramaLealtadPR.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("SELECT t FROM Transaction t WHERE t.business.id = :businessId")
    List<Transaction> findTransactionsByBusinessId(@Param("businessId") Long businessId);
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId")
    List<Transaction> findTransactionsByUserId(@Param("userId") Long userId);
    @Query("SELECT COALESCE(SUM(t.points), 0) FROM Transaction t WHERE t.user.id = :userId")
    Integer sumPointsByUser(@Param("userId") Long userId);
}
