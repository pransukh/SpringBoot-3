package com.org.let.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CHILD_TABLE")
@Data
public class EntityChild {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "CHILD_TABLE_ID")
    private Long childTableId; // Changed to String for UUID

    @ManyToOne
    @JoinColumn(name = "FK_PARENT_TABLE", nullable = false)
    private EntityParent fkEntityParent;

    @Column(name = "CHILD_TABLE_NAME")
    private String childTableName;

    @Column(name = "CHILD_TABLE_TYPE")
    private String childTableType;

    @Column(name = "CHILD_TABLE_VALUE")
    private String childTableValue;

    @Override
    public String toString() {
        return "ChildEntity{" +
                "childTableId=" + childTableId +
                ", fkEntityParent=" + (fkEntityParent != null ? fkEntityParent.getParentTableName() : "No parent ID") +
                ", childTableName='" + childTableName + '\'' +
                ", childTableType='" + childTableType + '\'' +
                ", childTableValue='" + childTableValue + '\'' +
                '}';
    }
}
