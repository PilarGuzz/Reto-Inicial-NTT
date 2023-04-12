package com.everis.language.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.language.entity.Language;
import com.everis.language.repository.LanguageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LanguageServiceImpl implements LanguageService {
	
	@Autowired
	LanguageRepository languageRepository;
	
	
	@Override
	public List<Language> findLanguageAll() {
		return languageRepository.findAll();
	}

	@Override
	public Language findLanguageByName(String name) {
		return languageRepository.findByName(name);
	}

	@Override
	public Language createLanguage(Language lang) {
		
		Language langDB = languageRepository.findByName(lang.getName());
		if (langDB != null){
            return  langDB;
        }
		
		Language langDBsaved = languageRepository.save ( lang );
        return langDBsaved;
		
	}

	@Override
	public Language updateLanguage(Language lang) {
		Language langDB = getLanguage(lang.getId());
        if (langDB == null){
            return  null;
        }
        langDB.setName(lang.getName());
        langDB.setMessage(lang.getMessage());

        return  languageRepository.save(langDB);
	}

	@Override
	public Language deleteLanguage(Language lang) {
		Language langDB = getLanguage(lang.getId());
		 if (langDB == null){
	            return  null;
	        }
		languageRepository.delete(langDB);
		return lang;
	}

	@Override
	public Language getLanguage(Long id) {
		return languageRepository.findById(id).orElse(null);
	}

}
