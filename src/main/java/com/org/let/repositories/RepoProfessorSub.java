package com.org.let.repositories;

import com.org.let.entities.EntityProf;
import com.org.let.entities.EntitySub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface RepoProfessorSub extends JpaRepository<EntityProf,Long> {

    @Query("SELECT p FROM EntityProf p JOIN p.entitySubList s WHERE p.profId = :profId")
    EntityProf findAllProfWithId(Long profId);

    @Query("SELECT s FROM EntitySub s JOIN s.entityProfList p WHERE s.subId = :subId")
    EntitySub findAllProfWithSubId(Long subId);

    @Query("SELECT p FROM EntitySub s JOIN s.entityProfList p")
    List<EntityProf> findAllProfWithAllSub();



}
