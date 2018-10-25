package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Piloto {

	public Piloto() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public LocalDate getDataInicioCarreira() {
		return dataInicioCarreira;
	}
	public void setDataInicioCarreira(LocalDate dataInicioCarreira) {
		this.dataInicioCarreira = dataInicioCarreira;
	}
	public BigDecimal getDinheiro() {
		return dinheiro;
	}
	public void setDinheiro(BigDecimal dinheiro) {
		this.dinheiro = dinheiro;
	}
	private Long id;
	private String nome;
	private LocalDate dataNascimento;
	private LocalDate dataInicioCarreira;
	private BigDecimal dinheiro;
	
	
		

}
