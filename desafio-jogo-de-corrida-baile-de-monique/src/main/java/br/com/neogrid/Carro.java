package br.com.neogrid;

import java.math.BigDecimal;

public class Carro {

    private Long id;
    private Long idPioloto;
    private String corCarro;
    private String marca;
    private Integer anoCarro;
    private Integer potencia;
    private BigDecimal preco;

    public Carro(Long id, Long idPioloto, String corCarro, String marca, Integer anoCarro, Integer potencia, BigDecimal preco) {
        this.id = id;
        this.idPioloto = idPioloto;
        this.corCarro = corCarro;
        this.marca = marca;
        this.anoCarro = anoCarro;
        this.potencia = potencia;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPioloto() {
        return idPioloto;
    }

    public void setIdPioloto(Long idPioloto) {
        this.idPioloto = idPioloto;
    }

    public String getCorCarro() {
        return corCarro;
    }

    public void setCorCarro(String corCarro) {
        this.corCarro = corCarro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAnoCarro() {
        return anoCarro;
    }

    public void setAnoCarro(Integer anoCarro) {
        this.anoCarro = anoCarro;
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
