package com.everis.language.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.everis.language.entity.Language;
import com.everis.language.service.LanguageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/languages")
public class LanguageRest {
	
	@Autowired
	LanguageService langserv;
	
 // -------------------Retrieve All Languages--------------------------------------------
	@GetMapping
    public ResponseEntity<List<Language>> listAllLanguages(@RequestParam(name = "name" , required = false) String name ) {
        List<Language> languages =  new ArrayList<>();
        if (null ==  name) {
        	languages = langserv.findLanguageAll();
            if (languages.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }else{

        	if ( null == langserv.findLanguageByName(name.toUpperCase()) ) {
        		log.error("Language with name {} not found.", name);
        		return  ResponseEntity.notFound().build();
        	}else {
        		languages.add(langserv.findLanguageByName(name));
        		
        	}
        }

        return  ResponseEntity.ok(languages);
    }
	
	
    // -------------------Retrieve Single language------------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Language> getLanguage(@PathVariable("id") long id) {
        log.info("Fetching Language with id {}", id);
        Language language = langserv.getLanguage(id);
        if (  null == language) {
            log.error("Language with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(language);
    }

 // -------------------Create a language-------------------------------------------

    @PostMapping
    public ResponseEntity<Language> createLanguage(@Valid @RequestBody Language language, BindingResult result) {
        log.info("Creating Language : {}", language);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        Language languageDB = langserv.createLanguage(language);

        return  ResponseEntity.status( HttpStatus.CREATED).body(languageDB);
    }
    
 // ------------------- Update a language ------------------------------------------------

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateLanguage(@PathVariable("id") long id, @RequestBody Language language) {
        log.info("Updating Customer with id {}", id);

        Language currentCustomer = langserv.getLanguage(id);

        if ( null == currentCustomer ) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        language.setId(id);
        currentCustomer=langserv.updateLanguage(language);
        return  ResponseEntity.ok(currentCustomer);
    }
    
 // ------------------- Delete a language-----------------------------------------

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Language> deleteCustomer(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Customer with id {}", id);

        Language language = langserv.getLanguage(id);
        if ( null == language ) {
            log.error("Unable to delete. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        language = langserv.deleteLanguage(language);
        return  ResponseEntity.ok(language);
    }

	
	
	private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
