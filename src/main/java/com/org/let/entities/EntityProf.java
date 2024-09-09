package com.org.let.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "PROFESSORS")
@Data
public class EntityProf {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PROFESSOR_ID")
    Long profId;

    @Column(name = "PROFESSOR_NAME")
    String profName;

    @ManyToMany
    @JoinTable(
            name = "PRO_SUB_RELATION",
            joinColumns = @JoinColumn(name = "PROFESSOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "SUBJECT_ID"))
    List<EntitySub> entitySubList;


}
