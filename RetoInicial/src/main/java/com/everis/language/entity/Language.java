package com.everis.language.entity;

import javax.validation.constraints.NotEmpty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_language")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Language {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
	@NotEmpty(message = "El nombre no debe ser vacío")
	private String name;
	@NotEmpty(message = "El mensaje no debe ser vacío")
	private String message;
	

}
