package com.org.let.repositories;

import com.org.let.models.ModelProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ModelProductDetails, Long> {
    Optional<ModelProductDetails> findById(Long id);
}
