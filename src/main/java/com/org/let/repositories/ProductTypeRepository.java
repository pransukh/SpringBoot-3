package com.org.let.repositories;

import com.org.let.entities.ModelProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ModelProductType, Long> {

    List<ModelProductType> findAll();

    @Override
    Optional<ModelProductType> findById(Long aLong);


    Optional<ModelProductType> findByProductTypeName(String name);

    @Query("SELECT p FROM ModelProductType p LEFT JOIN FETCH p.modalProducts WHERE p.productTypeId = :productTypeId")
    ModelProductType findByProductTypeId(Long productTypeId);

}
