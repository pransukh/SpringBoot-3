package com.org.let.repositories;

import com.org.let.entities.EntityChild;
import com.org.let.entities.EntityParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParentChildRepo extends JpaRepository<EntityParent, UUID> {
    @Query("SELECT pt,ct FROM EntityParent pt JOIN pt.childEntries ct where pt.parentTableName = :parentTableName")
    List<Object[]> findAllChildWithParent(@Param("parentTableName") String parentTableName);

    @Query("SELECT p FROM EntityParent p LEFT JOIN FETCH p.childEntries WHERE p.parentTableId = :parentTableId")
    EntityParent  findAllChildWithParentWithId(@Param("parentTableId") Long parentTableId);

    @Query("SELECT ct FROM EntityChild ct JOIN ct.fkEntityParent pt where pt.parentTableName = :parentTableName")
    List<EntityChild> findAllChildWithParentWithName(@Param("parentTableName") String parentTableName);

}
