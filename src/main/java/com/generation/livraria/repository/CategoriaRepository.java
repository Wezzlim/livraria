package com.generation.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.livraria.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	List<Categoria> findAllByTipoContainingIgnoreCase(String tipo);
	// buscar 
}

