package br.com.neogrid;

import java.math.BigDecimal;

public class Carro {
	
	private Long id;
	private Long idPiloto;
	private String cor;
	private String marca;
	private Integer ano;
	
	public Carro() {
		super();
	}
	public Carro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
		super();
		this.id = id;
		this.idPiloto = idPiloto;
		this.cor = cor;
		this.marca = marca;
		this.ano = ano;
		this.potencia = potencia;
		this.preco = preco;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdPiloto() {
		return idPiloto;
	}
	public void setIdPiloto(Long idPiloto) {
		this.idPiloto = idPiloto;
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
	private Integer potencia;
	private BigDecimal preco;
}
