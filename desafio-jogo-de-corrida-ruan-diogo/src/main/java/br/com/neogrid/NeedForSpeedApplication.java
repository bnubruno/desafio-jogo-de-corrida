package br.com.neogrid;

import br.com.neogrid.desafio.annotation.Desafio;
import br.com.neogrid.desafio.app.NeedForSpeedInterface;
import br.com.neogrid.desafio.exceptions.CarroNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.neogrid.desafio.exceptions.PilotoNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.SaldoInsuficienteException;
import br.com.neogrid.model.Carro;
import br.com.neogrid.model.Piloto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class NeedForSpeedApplication implements NeedForSpeedInterface {

    private Map<Long, Piloto> pilotos = new TreeMap<>();
    private Map<Long, Carro> carros = new TreeMap<>();

    @Override
    @Desafio("novoPiloto")
    public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
        if (this.pilotos.containsKey(id)) throw new IdentificadorUtilizadoException();

        this.pilotos.put(id, new Piloto(id, nome, dataNascimento, dataInicioCarreira, dinheiro));
    }

    @Override
    @Desafio("comprarCarro")
    public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
        if (this.carros.containsKey(id)) throw new IdentificadorUtilizadoException();

        Piloto piloto = buscarPilotoPorId(idPiloto).orElseThrow(PilotoNaoEncontradoException::new);

        if (piloto.getDinheiro().compareTo(preco) >= 0) {
            this.carros.put(id, new Carro(id, idPiloto, cor, marca, ano, potencia, preco));

            piloto.setDinheiro(piloto.getDinheiro().subtract(preco));
        } else {
            throw new SaldoInsuficienteException();
        }
    }

    @Override
    @Desafio("venderCarro")
    public void venderCarro(Long idCarro) {
            Carro carro = buscarCarroPorId(idCarro).orElseThrow(CarroNaoEncontradoException::new);
            Piloto piloto = buscarPilotoPorId(carro.getIdPiloto()).orElseThrow(PilotoNaoEncontradoException::new);

            piloto.setDinheiro(piloto.getDinheiro().add(carro.getPreco()));

            this.carros.remove(idCarro);
    }

    @Override
    @Desafio("buscarCarroMaisCaro")
    public Long buscarCarroMaisCaro() {
        return this.carros.values().stream()
                .max(Comparator.comparing(Carro::getPreco))
                .map(Carro::getId).orElse(null);
    }

    @Override
    @Desafio("buscarCarroMaisPotente")
    public Long buscarCarroMaisPotente() {
        return this.carros.values().stream()
                .max(Comparator.comparing(Carro::getPotencia))
                .map(Carro::getId).orElse(null);
    }

    @Override
    @Desafio("buscarCarros")
    public List<Long> buscarCarros(Long idPiloto) {
        buscarPilotoPorId(idPiloto).orElseThrow(PilotoNaoEncontradoException::new);

        return this.carros.values().stream()
                .filter(carro -> carro.getIdPiloto().equals(idPiloto))
                .map(Carro::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarCarrosPorMarca")
    public List<Long> buscarCarrosPorMarca(String marca) {
        return this.carros.values().stream()
                .filter(carro -> carro.getMarca().equals(marca))
                .map(Carro::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarCor")
    public String buscarCor(Long idCarro) {
        return buscarCarroPorId(idCarro).orElseThrow(CarroNaoEncontradoException::new).getCor();
    }

    @Override
    @Desafio("buscarMarcas")
    public List<String> buscarMarcas() {
        return this.carros.values().stream()
                .map(Carro::getMarca)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarNomePiloto")
    public String buscarNomePiloto(Long idPiloto) {
        return buscarPilotoPorId(idPiloto).orElseThrow(PilotoNaoEncontradoException::new).getNome();
    }

    @Override
    @Desafio("buscarPilotoMaisExperiente")
    public Long buscarPilotoMaisExperiente() {
        return this.pilotos.values().stream()
                .min(Comparator.comparing(Piloto::getDataInicioCarreira))
                .map(Piloto::getId).orElse(null);
    }

    @Override
    @Desafio("buscarPilotoMenosExperiente")
    public Long buscarPilotoMenosExperiente() {
        return this.pilotos.values().stream()
                .max(Comparator.comparing(Piloto::getDataInicioCarreira))
                .map(Piloto::getId).orElse(null);
    }

    @Override
    @Desafio("buscarPilotos")
    public List<Long> buscarPilotos() {
        return new ArrayList<>(this.pilotos.keySet());
    }

    @Override
    @Desafio("buscarSaldo")
    public BigDecimal buscarSaldo(Long idPiloto) {
        return buscarPilotoPorId(idPiloto).orElseThrow(PilotoNaoEncontradoException::new).getDinheiro();
    }

    @Override
    @Desafio("buscarValorPatrimonio")
    public BigDecimal buscarValorPatrimonio(Long idPiloto) {
        buscarPilotoPorId(idPiloto).orElseThrow(PilotoNaoEncontradoException::new);

        return this.carros.values().stream()
                .filter(carro -> carro.getIdPiloto().equals(idPiloto))
                .map(Carro::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Desafio("trocarCor")
    public void trocarCor(Long idCarro, String cor) {
        buscarCarroPorId(idCarro).orElseThrow(CarroNaoEncontradoException::new).setCor(cor);
    }

    private Optional<Piloto> buscarPilotoPorId(Long idPiloto) {
        return this.pilotos.containsKey(idPiloto) ? Optional.of(this.pilotos.get(idPiloto)) : Optional.empty();
    }

    private Optional<Carro> buscarCarroPorId(Long idCarro) {
        return this.carros.containsKey(idCarro) ? Optional.of(this.carros.get(idCarro)) : Optional.empty();
    }

}
