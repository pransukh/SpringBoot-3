package com.org.let.services;

import com.org.let.entities.EntityChild;
import com.org.let.entities.EntityParent;
import com.org.let.repositories.ParentChildRepo;


import com.org.let.response.DTOChild;
import com.org.let.response.DTOParent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class SerivceParentChild {

    @Autowired
    ParentChildRepo parentChildRepo;

    private static final Logger logger = Logger.getLogger(SerivceParentChild.class.getName());

    public DTOParent findAllChildWithParent(String parentTableName){


        logger.info("Incoming Name: "+parentTableName);
        List <EntityChild> entityChild = parentChildRepo.findAllChildWithParentWithName(parentTableName);

        logger.info("ResultSet size:"+entityChild.size());
        logger.info("Child IDs: "+entityChild.stream().map((entityChild1 -> entityChild1.getChildTableId())).collect(Collectors.toList()).toString());

        List<DTOChild> dtoChildList = entityChild.stream().map((entity) ->{
           DTOChild dtoChild = new DTOChild();
           dtoChild.setChildTableId(entity.getChildTableId());
           dtoChild.setChildTableName(entity.getChildTableName());
           dtoChild.setChildTableType(entity.getChildTableType());
           dtoChild.setChildTableValue(entity.getChildTableValue());
           return dtoChild;
        }).collect(Collectors.toList());
        DTOParent dtoParent = new DTOParent();

        dtoParent.setParentTableId(entityChild.get(0).getFkEntityParent().getParentTableId());
        dtoParent.setParentTableName(entityChild.get(0).getFkEntityParent().getParentTableName());
        dtoParent.setParentTableValue(entityChild.get(0).getFkEntityParent().getParentTableValue());
        dtoParent.setParentTableEnabled(entityChild.get(0).getFkEntityParent().getParentTableEnabled());

        dtoParent.setChildEntries(dtoChildList);
        return dtoParent;
    }

    public DTOParent  findAllChildWithParentWithId(Long parentTableId){
        logger.info("Incoming Id: "+parentTableId);
        EntityParent  entityParent = parentChildRepo.findAllChildWithParentWithId(parentTableId);

        DTOParent dtoParent = new DTOParent();
        dtoParent.setParentTableId(entityParent.getParentTableId());
        dtoParent.setParentTableEnabled(entityParent.getParentTableEnabled());
        dtoParent.setParentTableValue(entityParent.getParentTableValue());
        dtoParent.setParentTableName(entityParent.getParentTableName());

        List<DTOChild> dtoChildren =entityParent.getChildEntries().stream().map((entityChild)->{
            DTOChild dtoChild = new DTOChild();
            dtoChild.setChildTableId(entityChild.getChildTableId());
            dtoChild.setChildTableValue(entityChild.getChildTableValue());
            dtoChild.setChildTableName(entityChild.getChildTableName());
            dtoChild.setChildTableType(entityChild.getChildTableType());
            return dtoChild;
        }).collect(Collectors.toList());
        dtoParent.setChildEntries(dtoChildren);



        return dtoParent;
    }



    @Transactional
    public EntityParent  addParentChild(DTOParent dtoParent){
        EntityParent entityParent = null;
        if(dtoParent == null){

            EntityParent parent = new EntityParent();
            parent.setParentTableName("PARENT_TABLE_FROM_APP");
            parent.setParentTableValue("PARENT_TABLE_VALUE");
            parent.setParentTableEnabled(true);

            EntityChild child1  = new EntityChild();
            child1.setChildTableName("CHILD_1_ADDED_FROM_APP");
            child1.setChildTableValue("CHILD_1_VALUE_ADDED_FROM_APP");
            child1.setChildTableType("CHILD_1_TYPE_ADDED_FROM_APP");
            child1.setFkEntityParent(parent);

            EntityChild child2  = new EntityChild();
            child2.setChildTableName("CHILD_2_ADDED_FROM_APP");
            child2.setChildTableValue("CHILD_2_VALUE_ADDED_FROM_APP");
            child2.setChildTableType("CHILD_2_TYPE_ADDED_FROM_APP");
            child2.setFkEntityParent(parent);

            EntityChild child3  = new EntityChild();
            child3.setChildTableName("CHILD_3_ADDED_FROM_APP");
            child3.setChildTableValue("CHILD_3_VALUE_ADDED_FROM_APP");
            child3.setChildTableType("CHILD_3_TYPE_ADDED_FROM_APP");
            child3.setFkEntityParent(parent);

            parent.setChildEntries(Arrays.asList(child1,child2,child3));
            entityParent = parentChildRepo.save(parent);

        }else{
            EntityParent parent = new EntityParent();
            parent.setParentTableName(dtoParent.getParentTableName());
            parent.setParentTableValue(dtoParent.getParentTableValue());
            parent.setParentTableEnabled(dtoParent.getParentTableEnabled());

            List<DTOChild> dtoChildren = dtoParent.getChildEntries();
            List<EntityChild> entityChildList = new ArrayList<>();
            for(DTOChild child : dtoChildren){
                EntityChild child1  = new EntityChild();
                child1.setChildTableName(child.getChildTableName());
                child1.setChildTableValue(child.getChildTableValue());
                System.out.println(child.getChildTableType());
                child1.setChildTableType(child.getChildTableType());
                child1.setFkEntityParent(parent);
                entityChildList.add(child1);
            }
            parent.setChildEntries(entityChildList);
            entityParent = parentChildRepo.save(parent);
        }





        return entityParent;
    }


    @Transactional
    public Long  upsertParentChild(DTOParent requestPayload){
        EntityParent  entityParent = parentChildRepo.findAllChildWithParentWithId(requestPayload.getParentTableId());
        if(entityParent == null){
            System.err.println("❌❌❌❌❌ If the parent does not exist, then the child does not exist either ❌❌❌❌❌");
            EntityParent saved = addParentChild(requestPayload);
            System.out.println("⛳⛳"+saved.getParentTableId()+"⛳⛳");
            return saved.getParentTableId();
        }else{
            System.out.println("🎉🎉🎉🎉Parent Exists so upserting the parent and child 🎉🎉🎉🎉");

            entityParent.setParentTableValue(requestPayload.getParentTableValue());


            List<DTOChild> dtoChildList_REQ = requestPayload.getChildEntries();
            List<EntityChild> entityChildList_REQ  = dtoChildList_REQ.stream().map(dtoChild -> {
                EntityChild entityChild = new EntityChild();
                entityChild.setChildTableName(dtoChild.getChildTableName());
                entityChild.setChildTableType(dtoChild.getChildTableType());
                entityChild.setChildTableValue(dtoChild.getChildTableValue());
                return entityChild;
            }).toList();
            List<EntityChild> entityChildList_DB = entityParent.getChildEntries();

            // merge the entityChildList_REQ entityChildList_DB
            List<EntityChild> mergedChild = new ArrayList<>();

            Iterator<EntityChild> Itr_ecR = entityChildList_REQ.iterator();

            while (Itr_ecR.hasNext()){
                EntityChild ecR = (EntityChild) Itr_ecR.next();
                Iterator<EntityChild> Itr_ecDB = entityChildList_DB.iterator();
                boolean addNew = true;
                 inner: while (Itr_ecDB.hasNext()){
                        EntityChild ecDB = (EntityChild) Itr_ecDB.next();

                            if(Objects.equals(ecR.getChildTableType(),ecDB.getChildTableType())){
                                ecDB.setChildTableType(ecR.getChildTableType());
                                ecDB.setChildTableValue(ecR.getChildTableValue());
                                ecDB.setChildTableName(ecR.getChildTableName());
                                mergedChild.add(ecDB);// updating DB Entity with request value

                                addNew = false;
                                break inner;

                            }else{
                                addNew = true;
                            }

                 }
                 ecR.setFkEntityParent(entityParent);
                 if(addNew) mergedChild.add(ecR);
            }





            entityParent.setChildEntries(mergedChild);

            parentChildRepo.save(entityParent);






        }



        return entityParent.getParentTableId();
    }
}
