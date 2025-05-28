package com.generation.livraria.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//ANOTAÇÕES (que não precisa de ponto e virgula)
@Entity
@Table(name = "tb_produtos") // CREATE TABLE tb_produtos();
public class Produto 
{
	
	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
	private Long id;
	
	@Column(length = 100) 
	@NotBlank(message = "O titulo é obrigatório!")
	@Size(min = 3, max = 100, message = "O atributo nome deve ter no mínimo 3 e no máximo 100 caracteres.")
	private String titulo;
	
	@Column(length = 255) 
	@NotBlank(message = "O atributo descrição é obrigatório!")
	@Size(min = 5, max = 255, message = "O atributo descrição deve ter no mínimo 5 e no máximo 255 caracteres.")
	private String descricao;
	
    @NotNull(message = "O atributo preço é obrigatório!")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @DecimalMax(value = "9999.99", message = "O preço deve ser no máximo R$9999.99.")
    // Anotação @Digits serve p validar a quantidade de digitos de um número
    @Digits(integer = 6, fraction = 2, message = "O preço deve ter no máximo 6 dígitos inteiros, sendo 2 casas decimais.")
    private BigDecimal preco;
    
    //@Column(length = 500)
    //private String imagem;
    
	@UpdateTimestamp
	private LocalDateTime data;
	
	@ManyToOne // lado N da relaçao
	@JsonIgnoreProperties("produto") // pra ignorar o loop do insomnia 
	private Categoria categoria;
	
	/*@ManyToOne 
	@JsonIgnoreProperties("produto") 
	private Usuario usuario;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String nome) {
		this.titulo = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}

