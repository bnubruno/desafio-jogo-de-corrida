package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Piloto {

    private Long id;
    private String nome;
    private LocalDate dataNasc;
    private LocalDate dataInicioCarreira;
    private BigDecimal dinheiro;

    public Piloto(Long id, String nome, LocalDate dataNasc, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
        this.id = id;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.dataInicioCarreira = dataInicioCarreira;
        this.dinheiro = dinheiro;
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

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
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
