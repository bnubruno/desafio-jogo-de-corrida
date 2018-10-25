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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NeedForSpeedApplicationHashMap implements NeedForSpeedInterface {

    private Map<Long, Carro> carroHashMap = new HashMap<>();
    private Map<Long, Piloto> pilotoHashMap = new HashMap<>();

    @Override
    @Desafio("novoPiloto")
    public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
        if (this.pilotoHashMap.containsKey(id)) {
            throw new IdentificadorUtilizadoException();
        }
        this.pilotoHashMap.put(id, new Piloto(id, nome, dataNascimento, dataInicioCarreira, dinheiro));
    }

    @Override
    @Desafio("comprarCarro")
    public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
        Piloto piloto = null;
        if (!this.pilotoHashMap.containsKey(idPiloto)) {
            throw new PilotoNaoEncontradoException();
        } else {
            piloto = this.pilotoHashMap.get(idPiloto);
        }
        if (piloto.getDinheiro().compareTo(preco) < 0) {
            throw new SaldoInsuficienteException();
        }
        if (this.carroHashMap.containsKey(id)) {
            throw new IdentificadorUtilizadoException();
        }
        this.carroHashMap.put(id, new Carro(id, idPiloto, cor, marca, ano, potencia, preco));
        piloto.setDinheiro(piloto.getDinheiro().subtract(preco));

    }

    @Override
    @Desafio("venderCarro")
    public void venderCarro(Long idCarro) {
        if (!this.carroHashMap.containsKey(idCarro)) {
            throw new CarroNaoEncontradoException();
        }
        Piloto piloto = this.pilotoHashMap.get(carroHashMap.get(idCarro).getIdPiloto());
        piloto.setDinheiro(piloto.getDinheiro().add(carroHashMap.get(idCarro).getPreco()));
        this.carroHashMap.remove(idCarro);
    }

    @Override
    @Desafio("buscarSaldo")
    public BigDecimal buscarSaldo(Long idPiloto) {
        if (!this.pilotoHashMap.containsKey(idPiloto)) {
            throw new PilotoNaoEncontradoException();
        }
        return this.pilotoHashMap.get(idPiloto).getDinheiro();
    }

    @Override
    @Desafio("trocarCor")
    public void trocarCor(Long idCarro, String cor) {
        if (!this.carroHashMap.containsKey(idCarro)) {
            throw new CarroNaoEncontradoException();
        }
        this.carroHashMap.get(idCarro).setCor(cor);
    }

    @Override
    @Desafio("buscarNomePiloto")
    public String buscarNomePiloto(Long idPiloto) {
        if (!this.pilotoHashMap.containsKey(idPiloto)) {
            throw new PilotoNaoEncontradoException();
        }
        return this.pilotoHashMap.get(idPiloto).getNome();
    }

    @Override
    @Desafio("buscarCor")
    public String buscarCor(Long idCarro) {
        if (!this.carroHashMap.containsKey(idCarro)) {
            throw new CarroNaoEncontradoException();
        }
        return this.carroHashMap.get(idCarro).getCor();
    }

    @Override
    @Desafio("buscarCarros")
    public List<Long> buscarCarros(Long idPiloto) {
        if (!this.pilotoHashMap.containsKey(idPiloto)) {
            throw new PilotoNaoEncontradoException();
        }
        return this.carroHashMap.values()
                .stream()
                .filter(carro -> (carro.getIdPiloto().equals(idPiloto)))
                .map(Carro::getId)
                .sorted()
                .collect(Collectors.toList());
//        List<Long> carrosList = new ArrayList<>();
//        for (Carro carro : carroHashMap.values()) {
//            if (carro.getIdPiloto().equals(idPiloto)) {
//                carrosList.add(carro.getId());
//            }
//        }
//        return carrosList;
//    }
    }

    @Override
    @Desafio("buscarPilotos")
    public List<Long> buscarPilotos() {
        return this.pilotoHashMap.values()
                .stream()
                .map(Piloto::getId)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarMarcas")
    public List<String> buscarMarcas() {
        return this.carroHashMap.values()
                .stream()
                .map(Carro::getMarca)
                .sorted()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarValorPatrimonio")
    public BigDecimal buscarValorPatrimonio(Long idPiloto) {
        if(!this.pilotoHashMap.containsKey(idPiloto)){
            throw new PilotoNaoEncontradoException();
        }
        return this.carroHashMap.values()
                .stream()
                .filter(carro -> carro.getIdPiloto().equals(idPiloto))
                .map(Carro::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Desafio("buscarCarrosPorMarca")
    public List<Long> buscarCarrosPorMarca(String marca) {
        return this.carroHashMap.values()
                .stream()
                .filter(carro -> carro.getMarca().equals(marca))
                .map(Carro::getId).distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    @Desafio("buscarCarroMaisPotente")
    public Long buscarCarroMaisPotente() {
        BigDecimal maisPotente = this.carroHashMap.values()
                .stream()
                .max(Comparator.comparing(Carro::getPotencia))
                .get()
                .getPreco();
        return this.carroHashMap.values()
                .stream()
                .filter(carro -> carro.getPotencia().equals(maisPotente))
                .map(Carro::getId)
                .sorted()
                .findFirst()
                .get();
    }



    @Override
    @Desafio("buscarCarroMaisCaro")
    public Long buscarCarroMaisCaro() {
        BigDecimal maiorPreco = this.carroHashMap.values()
                .stream()
                .max(Comparator.comparing(Carro::getPreco))
                .get()
                .getPreco();
        return this.carroHashMap.values()
                .stream()
                .filter(carro -> carro.getPreco().equals(maiorPreco))
                .map(Carro::getId)
                .sorted()
                .findFirst()
                .get();
    }

    @Override
    @Desafio("buscarPilotoMaisExperiente")
    public Long buscarPilotoMaisExperiente() {
        return this.pilotoHashMap.values()
                .stream()
                .min(Comparator.comparing(Piloto::getDataInicioCarreira))
                .get()
                .getId();
    }

    @Override
    @Desafio("buscarPilotoMenosExperiente")
    public Long buscarPilotoMenosExperiente() {
        return this.pilotoHashMap.values()
                .stream()
                .max(Comparator.comparing(Piloto::getDataInicioCarreira))
                .get()
                .getId();
    }
}
