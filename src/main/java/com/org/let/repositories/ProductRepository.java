package com.org.let.repositories;

import com.org.let.models.ModelProduct;
import com.org.let.models.ModelProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ModelProduct, Long> {
    @Override
    List<ModelProduct> findAll();

    @Override
    Optional<ModelProduct> findById(Long aLong);

    Optional<ModelProduct> findByName(String name);

    @Query("SELECT p FROM ModelProduct p WHERE p.name = :name AND p.id = :id")
    Optional<ModelProduct> findByNameAndId(String name, int id);

    @Query("SELECT p, pt FROM ModelProduct p JOIN p.productTypeId pt WHERE p.id = :id")
    List<Object[]> findProductsWithTypes(Long id);
}