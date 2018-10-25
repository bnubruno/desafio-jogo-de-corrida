package br.com.neogrid;

import java.math.BigDecimal;

public class Carro {

	public Carro(){
		super();
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
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public Integer getPotencia() {
		return potencia;
	}
	public void setPotencia(Integer potencia) {
		this.potencia = potencia;
	}
	
	private Long id;
	private Long idPiloto;
	private String cor;
	private Integer ano;
	private BigDecimal preco;
	private String marca;
	private Integer potencia;
	
	
}
