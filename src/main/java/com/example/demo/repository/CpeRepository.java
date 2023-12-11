package com.example.demo.repository;

import com.example.demo.model.db.CpeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface CpeRepository extends JpaRepository<CpeEntity, UUID> {

    List<CpeEntity> findByCpeNameIn(List<String> cpeNames);
    Page<CpeEntity> findByCpeNameContaining(String cpeName, Pageable pageable);

}