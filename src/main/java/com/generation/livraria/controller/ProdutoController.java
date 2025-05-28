package com.generation.livraria.controller;

import java.math.BigDecimal;
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

import com.generation.livraria.model.Produto;
import com.generation.livraria.repository.CategoriaRepository;
import com.generation.livraria.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos") //endpoint
@CrossOrigin(origins = "*", allowedHeaders = "*") // controla quais origens (domínios) externos podem acessar a API
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll()
	{
		// SELECT * FROM tb_produtos;
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) // o patchvariable diz ao Spring para extrair o valor da variável da URL e atribuí-lo ao parâmetro do método.
	{
		return produtoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Produto>> getAllByTitulo(@PathVariable String titulo)
	{
		return ResponseEntity.ok(produtoRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto)
	{
		// Verifica se a categoria existe antes de persistir o produto no Banco de dados 
		if (categoriaRepository.existsById(produto.getCategoria().getId())) 
		{
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto)); 
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria não existe!", null);
	}
	
	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto)
	{
		if(produto.getId() == null) 
			
			return ResponseEntity.badRequest().build();
		
		if(produtoRepository.existsById(produto.getId())) 
		{
			if (categoriaRepository.existsById(produto.getCategoria().getId())) 
			return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto)); 
		
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria não existe!", null); 
		}
				
		// Se a postagem não existir, retorna o HTTP Status 404 - NOT_FOUND
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id)
	{
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if(produto.isEmpty()) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		produtoRepository.deleteById(id);
	}
	
}

