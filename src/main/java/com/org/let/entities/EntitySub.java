package com.org.let.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JoinFormula;

import java.util.List;

@Entity
@Table(name = "SUBJECTS")
@Data
public class EntitySub {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUBJECT_ID")
    Long subId;

    @Column(name = "SUBJECT_NAME")
    String subName;

    @ManyToMany(mappedBy = "entitySubList")
    List<EntityProf> entityProfList;




}
