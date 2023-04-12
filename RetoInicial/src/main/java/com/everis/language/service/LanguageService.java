package com.everis.language.service;

import java.util.List;

import com.everis.language.entity.Language;

public interface LanguageService {
	public List<Language> findLanguageAll();
    public Language findLanguageByName(String name);

    public Language createLanguage(Language language);
    public Language updateLanguage(Language language);
    public Language deleteLanguage(Language language);
    public Language getLanguage(Long id);

}
