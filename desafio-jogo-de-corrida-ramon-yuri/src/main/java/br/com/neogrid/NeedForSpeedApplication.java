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

    private Map<Long, Piloto> pilotos = new HashMap<>();
    private Map<Long, Carro> carros = new HashMap<>();

    @Override
    @Desafio("novoPiloto")
    public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
        if (this.pilotos.containsKey(id)) {
            throw new IdentificadorUtilizadoException();
        }

        Piloto piloto = new Piloto(id, nome, dataNascimento, dataInicioCarreira, dinheiro);

        this.pilotos.put(id, piloto);
    }

    @Override
    @Desafio("comprarCarro")
    public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
        if (this.carros.containsKey(id)) {
            throw new IdentificadorUtilizadoException();
        }

        Piloto piloto = buscarPilotoPorId(idPiloto).orElseThrow(PilotoNaoEncontradoException::new);

        if (piloto.getDinheiro().compareTo(preco) < 0) {
            throw new SaldoInsuficienteException();
        }

        Carro carro = new Carro(id, idPiloto, cor, marca, ano, potencia, preco);

        this.carros.put(id, carro);

        piloto.getCarros().add(id);
        piloto.diminuiDinheiro(preco);
    }

    @Override
    @Desafio("venderCarro")
    public void venderCarro(Long idCarro) {
        Carro carro = buscarCarroPorId(idCarro).orElseThrow(CarroNaoEncontradoException::new);
        Piloto piloto = buscarPilotoPorId(carro.getIdPiloto()).orElseThrow(PilotoNaoEncontradoException::new);

        piloto.adicionaDinheiro(carro.getPreco());
        piloto.removeCarro(idCarro);

        this.carros.remove(idCarro);
    }

    @Override
    @Desafio("buscarCarroMaisCaro")
    public Long buscarCarroMaisCaro() {
        return this.carros.values().stream()
                .min(Comparator
                        .comparing(Carro::getPreco, Comparator.reverseOrder())
                        .thenComparingLong(Carro::getId))
                .orElse(new Carro())
                .getId();
    }

    @Override
    @Desafio("buscarCarroMaisPotente")
    public Long buscarCarroMaisPotente() {
        return this.carros.values().stream()
                .min(Comparator
                        .comparing(Carro::getPotencia, Comparator.reverseOrder())
                        .thenComparing(Carro::getId))
                .orElse(new Carro())
                .getId();
    }

    @Override
    @Desafio("buscarCarros")
    public List<Long> buscarCarros(Long idPiloto) {
        List<Long> carros = buscarPilotoPorId(idPiloto).orElseThrow(PilotoNaoEncontradoException::new).getCarros();
        carros.sort(Long::compare);

        return carros;
    }

    @Override
    @Desafio("buscarCarrosPorMarca")
    public List<Long> buscarCarrosPorMarca(String marca) {
        return this.carros.values().stream()
                .filter(carro -> carro.getMarca().equals(marca))
                .map(Carro::getId)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarCor")
    public String buscarCor(Long idCarro) {
        Carro carro = buscarCarroPorId(idCarro).orElseThrow(CarroNaoEncontradoException::new);

        return carro.getCor();
    }

    @Override
    @Desafio("buscarMarcas")
    public List<String> buscarMarcas() {
        return this.carros.values().stream().map(Carro::getMarca).sorted().distinct().collect(Collectors.toList());
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
                .min(Comparator
                        .comparing(Piloto::getDataInicioCarreira)
                        .thenComparingLong(Piloto::getId))
                .orElse(new Piloto())
                .getId();
    }

    @Override
    @Desafio("buscarPilotoMenosExperiente")
    public Long buscarPilotoMenosExperiente() {
        return this.pilotos.values().stream()
                .min(Comparator
                        .comparing(Piloto::getDataInicioCarreira, Comparator.reverseOrder())
                        .thenComparingLong(Piloto::getId))
                .orElse(new Piloto())
                .getId();
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
        List<Long> carros = buscarPilotoPorId(idPiloto).orElseThrow(PilotoNaoEncontradoException::new).getCarros();

        return this.carros.values().stream()
                .filter(c -> carros.contains(c.getId()))
                .map(Carro::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Desafio("trocarCor")
    public void trocarCor(Long idCarro, String cor) {
        buscarCarroPorId(idCarro).orElseThrow(CarroNaoEncontradoException::new).setCor(cor);
    }

    private Optional<Carro> buscarCarroPorId(Long idCarro) {
        if (this.carros.containsKey(idCarro)) {
            return Optional.of(this.carros.get(idCarro));
        } else {
            return Optional.empty();
        }
    }

    private Optional<Piloto> buscarPilotoPorId(Long idPiloto) {
        if (this.pilotos.containsKey(idPiloto)) {
            return Optional.of(this.pilotos.get(idPiloto));
        } else {
            return Optional.empty();
        }
    }
}
