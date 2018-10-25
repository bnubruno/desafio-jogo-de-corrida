package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.neogrid.desafio.annotation.Desafio;
import br.com.neogrid.desafio.app.NeedForSpeedInterface;
import br.com.neogrid.desafio.exceptions.*;
import br.com.neogrid.model.Carro;
import br.com.neogrid.model.Piloto;

public class NeedForSpeedApplication implements NeedForSpeedInterface {

    private List<Piloto> listaPiloto = new ArrayList<Piloto>();
    private List<Carro> listaCarro = new ArrayList<Carro>();

    @Override
    @Desafio("novoPiloto")
    public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
        if (buscarPilotoPorId(id).isPresent()) {
            throw new IdentificadorUtilizadoException();
        }
        this.listaPiloto.add(new Piloto(id, nome, dataNascimento, dataInicioCarreira, dinheiro));
    }

    @Override
    @Desafio("comprarCarro")
    public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
        Piloto piloto = listaPiloto
                .stream()
                .filter(p -> p.getId().equals(idPiloto))
                .findFirst()
                .orElseThrow(PilotoNaoEncontradoException::new);
        if (piloto.getDinheiro().compareTo(preco) < 0) {
            throw new SaldoInsuficienteException();
        }
        if (buscarCarroPorId(id).isPresent()) {
            throw new IdentificadorUtilizadoException();
        }
        piloto.setDinheiro(piloto.getDinheiro().subtract(preco));
        this.listaCarro.add(new Carro(id, idPiloto, cor, marca, ano, potencia, preco));

    }

    @Override
    @Desafio("venderCarro")
    public void venderCarro(Long idCarro) {
        Carro vendaCarro = buscarCarroPorId(idCarro).orElseThrow(CarroNaoEncontradoException::new);
        Piloto piloto = this.listaPiloto
                .stream()
                .filter(p -> p.getId().equals(vendaCarro.getIdPiloto()))
                .findFirst()
                .orElseThrow(PilotoNaoEncontradoException::new);
        piloto.setDinheiro(piloto.getDinheiro().add(vendaCarro.getPreco()));
        this.listaCarro.remove(vendaCarro);
    }

    @Override
    @Desafio("buscarCarros")
    public List<Long> buscarCarros(Long idPiloto) {
        if (!buscarPilotoPorId(idPiloto).isPresent()) {
            throw new PilotoNaoEncontradoException();
        } else {
            return this.listaCarro
                    .stream()
                    .filter(c -> c.getIdPiloto().equals(idPiloto))
                    .map(Carro::getId)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Desafio("buscarCarrosPorMarca")
    public List<Long> buscarCarrosPorMarca(String marca) {
        return this.listaCarro
                .stream()
                .filter(c -> c.getMarca().equals(marca))
                .map(Carro::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarCor")
    public String buscarCor(Long idCarro) {
        return this.listaCarro
                .stream()
                .filter(c -> c.getId().equals(idCarro))
                .map(Carro::getCor)
                .findFirst()
                .orElseThrow(CarroNaoEncontradoException::new);
    }

    @Override
    @Desafio("buscarMarcas")
    public List<String> buscarMarcas() {
        return this.listaCarro
                .stream()
                .map(Carro::getMarca)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarNomePiloto")
    public String buscarNomePiloto(Long idPiloto) {
        return this.listaPiloto
                .stream()
                .filter(p -> p.getId().equals(idPiloto))
                .map(Piloto::getNome)
                .findFirst()
                .orElseThrow(PilotoNaoEncontradoException::new);
    }

    @Override
    @Desafio("buscarPilotos")
    public List<Long> buscarPilotos() {
        return this.listaPiloto
                .stream()
                .map(Piloto::getId)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarSaldo")
    public BigDecimal buscarSaldo(Long idPiloto) {
        return this.listaPiloto
                .stream()
                .filter(p -> p.getId().equals(idPiloto))
                .map(Piloto::getDinheiro)
                .findFirst()
                .orElseThrow(PilotoNaoEncontradoException::new);
    }

    @Override
    @Desafio("buscarValorPatrimonio")
    public BigDecimal buscarValorPatrimonio(Long idPiloto) {
        if (!buscarPilotoPorId(idPiloto).isPresent()) {
            throw new PilotoNaoEncontradoException();
        }
        return this.listaCarro
                .stream()
                .filter(c -> c.getIdPiloto().equals(idPiloto))
                .map(Carro::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Desafio("trocarCor")
    public void trocarCor(Long idCarro, String cor) {
        Carro carro = this.listaCarro
                .stream()
                .filter(c -> c.getId().equals(idCarro))
                .findFirst()
                .orElseThrow(CarroNaoEncontradoException::new);
        carro.setCor(cor);
    }

    @Override
    @Desafio("buscarPilotoMaisExperiente")
    public Long buscarPilotoMaisExperiente() {
        Comparator<Piloto> comparator = Comparator.comparing(Piloto::getDataInicioCarreira);
        comparator = comparator.thenComparing(Piloto::getId);
        return this.listaPiloto
                .stream()
                .sorted(comparator)
                .map(Piloto::getId)
                .findFirst()
                .get();
    }

    @Override
    @Desafio("buscarPilotoMenosExperiente")
    public Long buscarPilotoMenosExperiente() {
        Comparator<Piloto> comparator = Comparator.comparing(Piloto::getDataInicioCarreira);
        comparator = comparator.reversed();
        comparator = comparator.thenComparing(Piloto::getId);
        return this.listaPiloto
                .stream()
                .sorted(comparator)
                .map(Piloto::getId)
                .findFirst()
                .get();
    }

    @Override
    @Desafio("buscarCarroMaisCaro")
    public Long buscarCarroMaisCaro() {
        BigDecimal maiorPreco = this.listaCarro
                .stream()
                .max(Comparator.comparing(Carro::getPreco))
                .get()
                .getPreco();
        return this.listaCarro
                .stream()
                .filter(carro -> carro.getPreco().equals(maiorPreco))
                .map(Carro::getId)
                .sorted()
                .findFirst()
                .get();
    }

    @Override
    @Desafio("buscarCarroMaisPotente")
    public Long buscarCarroMaisPotente() {
        BigDecimal maisPotente = this.listaCarro
                .stream()
                .max(Comparator.comparing(Carro::getPotencia))
                .get()
                .getPreco();
        return this.listaCarro
                .stream()
                .filter(carro -> carro.getPotencia().equals(maisPotente))
                .map(Carro::getId)
                .sorted()
                .findFirst()
                .get();
    }

    private Optional<Carro> buscarCarroPorId(Long idCarro) {
        return this.listaCarro
                .stream()
                .filter(c -> c.getId().equals(idCarro))
                .findFirst();
    }

    private Optional<Piloto> buscarPilotoPorId(Long idPiloto) {
        return this.listaPiloto
                .stream()
                .filter(p -> p.getId().equals(idPiloto))
                .findFirst();
    }

}
