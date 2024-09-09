package com.org.let.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "PARENT_TABLE")
@Data
public class EntityParent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "PARENT_TABLE_ID")
    private Long parentTableId; // Changed to String for UUID

    @Column(name = "PARENT_TABLE_NAME")
    private String parentTableName;

    @Column(name = "PARENT_TABLE_VALUE")
    private String parentTableValue;

    @Column(name = "PARENT_TABLE_ENABLED")
    private Boolean parentTableEnabled; // Changed to Boolean for better readability

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkEntityParent") // this is var name defined in child entity
    private List<EntityChild> childEntries; // this name could be anything


    @Override
    public String toString() {
        return "ParentEntity{" +
                "parentTableId=" + parentTableId +
                ", parentTableName='" + parentTableName + '\'' +
                ", parentTableValue='" + parentTableValue + '\'' +
                ", parentTableEnabled=" + parentTableEnabled +
                ", childEntries count is: " + (childEntries != null ? childEntries.size() : "null") +
                '}';
    }

}
