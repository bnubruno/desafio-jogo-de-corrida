package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Piloto {
	private Long Id;
	private String nome;
	private LocalDate dataNascimento, dataInicioCarreira;
	private BigDecimal dinheiro;
	
	public Piloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
		super();
		Id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.dataInicioCarreira = dataInicioCarreira;
		this.dinheiro = dinheiro;
	}

	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
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

}
