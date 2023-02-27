package com.oegr.ProgramaLealtadPR.repository;

import com.oegr.ProgramaLealtadPR.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business,Long> {
    @Query("SELECT b FROM Business b WHERE b.user.id = :userId ORDER BY b.id ASC")
    Optional<Business> findFirstByUserId(@Param("userId") Long userId);
}
