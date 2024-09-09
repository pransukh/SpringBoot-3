package com.org.let.controllers;


import com.org.let.response.DTOParent;
import com.org.let.services.SerivceParentChild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/pc/")
public class ParentChildController {
    @Autowired
    SerivceParentChild serivceParentChild;

    @GetMapping("getAll/name/{name}")
    public DTOParent getAllChildWithParentName(@PathVariable("name") String name ){
        return serivceParentChild.findAllChildWithParent(name);
    }

    @GetMapping("getAll/id/{id}")
    public DTOParent getAllChildWithParentId(@PathVariable("id") Long id ){

        return serivceParentChild.findAllChildWithParentWithId(id);
    }

    @PostMapping("add/pc/fromApp")
    public Long addParentChild(){

        return serivceParentChild.addParentChild(null).getParentTableId();
    }

    @PostMapping("add/pc/upsert")
    public Long upsertParentChild(@RequestBody DTOParent dtoParent ){

        return serivceParentChild.upsertParentChild(dtoParent);
    }
}
