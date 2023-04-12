package com.everis.language.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.everis.language.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
	public Language findByName(String name);
 
}
