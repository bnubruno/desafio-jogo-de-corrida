package br.com.neogrid;

import java.math.BigDecimal;

public class Carro {
	private Long Id, IdPiloto;
	private String cor, marca;
	private Integer ano, potencia;
	private BigDecimal preco;
	
	public Carro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
		super();
		Id = id;
		IdPiloto = idPiloto;
		this.cor = cor;
		this.marca = marca;
		this.ano = ano;
		this.potencia = potencia;
		this.preco = preco;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getIdPiloto() {
		return IdPiloto;
	}
	public void setIdPiloto(Long idPiloto) {
		IdPiloto = idPiloto;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Integer getPotencia() {
		return potencia;
	}
	public void setPotencia(Integer potencia) {
		this.potencia = potencia;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

}
