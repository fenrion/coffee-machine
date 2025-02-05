package com.aisa.test.repository;

import com.aisa.test.domain.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StorageRepository extends JpaRepository<Storage, UUID> {
}
