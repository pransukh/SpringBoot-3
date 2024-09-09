package com.org.let.services;

import com.org.let.entities.EntityProf;
import com.org.let.entities.EntitySub;
import com.org.let.repositories.RepoProfessorSub;
import com.org.let.response.DTOm2m;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ServiceProfSub {

    private static final Logger logger = Logger.getLogger(SerivceParentChild.class.getName());

    @Autowired
    RepoProfessorSub repoProfessorSub;

    public List<EntitySub> getAllProfWithId(Long profId) {
        logger.info("INCOMING ID: " + profId);
        EntityProf entityProfList = repoProfessorSub.findAllProfWithId(profId);

        List<EntitySub> rs = new ArrayList<>();

        List<EntitySub> entitySubList = entityProfList.getEntitySubList().stream().map((s) -> {
            EntitySub sub = new EntitySub();
            sub.setSubId(s.getSubId());
            sub.setSubName(s.getSubName());
            return sub;
        }).collect(Collectors.toList());

        logger.info("LIST SIZE: " + entitySubList.size());
        return entitySubList;
    }

    public List<EntityProf> getAllProfWithSubId(Long profId) {
        logger.info("INCOMING SUB ID: " + profId);
        EntitySub entitySub = repoProfessorSub.findAllProfWithSubId(profId);

        List<EntitySub> rs = new ArrayList<>();

        List<EntityProf> entityProfList = entitySub.getEntityProfList().stream().map((p) -> {
            EntityProf prof = new EntityProf();
            prof.setProfId(p.getProfId());
            prof.setProfName(p.getProfName());
            return prof;
        }).collect(Collectors.toList());

        logger.info("LIST SIZE: " + entityProfList.size());
        return entityProfList;
    }

    public Map<String, List<DTOm2m>> getAllProfAndAllSub() {
        logger.info("INCOMING PARAM {NONE} ");

        List<DTOm2m> dtOm2ms = new ArrayList<>();
        List<DTOm2m> subjectsList = new ArrayList<>();
        Map<String, List<DTOm2m>> map = new HashMap<>();
        List<EntityProf> entityProfList = repoProfessorSub.findAllProfWithAllSub();


        entityProfList.stream().forEach((prof) -> {
            DTOm2m response = new DTOm2m();

            response.setKey(prof.getProfName());

            List<String> subs = new ArrayList<>();

            prof.getEntitySubList().stream().forEach((sub) -> {
                subs.add(sub.getSubName());

            });
            response.setEntitySubList(subs);

            dtOm2ms.add(response);

        });

        entityProfList.get(0).getEntitySubList().stream().forEach((sub) -> {
            logger.info("Subject: "+sub.getSubName()+" being tought by:");
            DTOm2m response = new DTOm2m();
            response.setKey(sub.getSubName());
            List<String> proflist = new ArrayList<>();
            sub.getEntityProfList().stream().forEach((prof) -> {
                logger.info("Prof:"+prof.getProfName());
                proflist.add(prof.getProfName());
            });
            response.setEntitySubList(proflist);
            subjectsList.add(response);
        });
        map.put("profTechingSubs", dtOm2ms);
        map.put("subToughtByProfs",subjectsList);

        logger.info("JSON: " + map.toString());
        return map;
    }



}
