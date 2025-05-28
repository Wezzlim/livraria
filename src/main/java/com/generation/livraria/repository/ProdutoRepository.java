package com.generation.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.livraria.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	List<Produto> findAllByTituloContainingIgnoreCase(String titulo);
	
	/* SELECT * FROM tb_produtos WHERE titulo LIKE '%?%' */
	
}
