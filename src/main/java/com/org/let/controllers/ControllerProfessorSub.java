package com.org.let.controllers;

import com.org.let.entities.EntityProf;
import com.org.let.entities.EntitySub;
import com.org.let.response.DTOm2m;
import com.org.let.services.ServiceProfSub;
import jakarta.websocket.server.PathParam;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prof/sub/m2m")
public class ControllerProfessorSub {

    @Autowired
    ServiceProfSub serviceProfSub;

    @GetMapping("/prof/{profId}")
    public List<EntitySub> getAllSubWithProfId(@PathParam("profId") Long profId){
        return serviceProfSub.getAllProfWithId(profId);
    }

    @GetMapping("/sub/{subId}")
    public List<EntityProf> getAllProfWithSubId(@PathParam("subId") Long subId){
        return serviceProfSub.getAllProfWithSubId(subId);
    }

    @GetMapping("/allProfAllSub")
    public Map<String, List<DTOm2m>> getAllProfWithAllSub(){

        Map<String, List<DTOm2m>> response = serviceProfSub.getAllProfAndAllSub();
       return response;
    }
}
