package com.generation.livraria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.livraria.model.Categoria;
import com.generation.livraria.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias") 
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll()  
	// BUSCA SEM PARAMETROS RETORNANDO TUDO
	{
		// SELECT * FROM tb_categorias; 
		return ResponseEntity.ok(categoriaRepository.findAll());
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Categoria> getById(@PathVariable Long id) 
	// BUSCANDO PELO ID
	{
		return categoriaRepository.findById(id) 
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/tipo/{tipo}") 
	public ResponseEntity<List<Categoria>> getAllByTipo(@PathVariable String tipo) 
	// BUSCANDO PELO TIPO 
	{
		return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo)); 
	}
	
	@PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria) 
	// CRIAR
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria)); 
	}
	
	@PutMapping
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria) 
	// ALTERAR
	{
		if(categoria.getId() == null) 
			return ResponseEntity.badRequest().build();
		
		if(categoriaRepository.existsById(categoria.getId())) 
			return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria)); 
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping("/{id}")
	//DELETAR
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id)
	{
		Optional<Categoria> categoria = categoriaRepository.findById(id); 
		
		if(categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		categoriaRepository.deleteById(id); 
		
		// DELETE FROM tb_categorias WHERE id = ?; 
	}
	

}
