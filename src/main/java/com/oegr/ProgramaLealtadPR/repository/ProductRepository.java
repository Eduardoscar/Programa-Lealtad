package com.oegr.ProgramaLealtadPR.repository;

import com.oegr.ProgramaLealtadPR.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.business.id = :businessId")
    List<Product> findAllByBusiness(@Param("businessId") Long businessId);
}
