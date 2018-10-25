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
import br.com.neogrid.desafio.exceptions.CarroNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.neogrid.desafio.exceptions.PilotoNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.SaldoInsuficienteException;

public class NeedForSpeedApplication implements NeedForSpeedInterface {

    private List<Piloto> pilotos = new ArrayList<>();
    private List<Carro> carros = new ArrayList<>();

    public boolean verificaIdPiloto(Long id){
        return pilotos.stream().anyMatch(p -> p.getId().equals(id));
    }

    public boolean verificaIdCarro(Long id){
        return carros.stream().anyMatch(c -> c.getId().equals(id));
    }

    public Optional<Piloto> buscarPiloto(Long id){
        return pilotos.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

	public Optional<Carro> buscarCarro(Long id){
		return carros.stream().filter(c -> c.getId().equals(id)).findFirst();
	}

	@Override
	@Desafio("novoPiloto")
	public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
        if (verificaIdPiloto(id))
            throw new IdentificadorUtilizadoException();
        this.pilotos.add(new Piloto(id, nome, dataNascimento, dataInicioCarreira, dinheiro));
	}

	@Override
	@Desafio("comprarCarro")
	public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
        if (verificaIdCarro(id))
            throw new IdentificadorUtilizadoException();
        Piloto piloto = buscarPiloto(idPiloto).orElseThrow(PilotoNaoEncontradoException::new);
        if (piloto.getDinheiro().compareTo(preco) < 0){
            throw new SaldoInsuficienteException();
        }
		this.carros.add(new Carro(id, idPiloto, cor, marca, ano, potencia, preco));
		piloto.setDinheiro(piloto.getDinheiro().subtract(preco));
    }

	@Override
	@Desafio("venderCarro")
	public void venderCarro(Long idCarro) {
		Carro carro = buscarCarro(idCarro).orElseThrow(CarroNaoEncontradoException::new);
		Piloto piloto = buscarPiloto(carro.getIdPioloto()).orElseThrow(PilotoNaoEncontradoException::new);
		if (piloto.getDinheiro().compareTo(carro.getPreco()) < 0) {
			piloto.setDinheiro(piloto.getDinheiro().add(carro.getPreco()));
			this.carros.remove(carro);
		}
	}

	@Override
	@Desafio("buscarCarroMaisCaro")
	public Long buscarCarroMaisCaro() {
    	BigDecimal preco = this.carros.stream().max(Comparator.comparing(Carro::getPreco)).get().getPreco();
		return this.carros.stream().filter(c -> c.getPreco().equals(preco)).min(Comparator.comparing(Carro::getId)).get().getId();
    }

	@Override
	@Desafio("buscarCarroMaisPotente")
	public Long buscarCarroMaisPotente() {
		Integer potencia = this.carros.stream().max(Comparator.comparing(Carro::getPotencia)).get().getPotencia();
		return this.carros.stream().filter(c -> c.getPotencia().equals(potencia)).min(Comparator.comparing(Carro::getId)).get().getId();
	}

	@Override
	@Desafio("buscarCarros")
	public List<Long> buscarCarros(Long idPiloto) {
    	if (!verificaIdPiloto(idPiloto)) throw new PilotoNaoEncontradoException();
		return this.carros.stream().filter(c -> c.getIdPioloto().equals(idPiloto)).sorted((c1, c2) -> c1.getId().compareTo(c2.getId())).map(c -> c.getId()).collect(Collectors.toList());	}

	@Override
	@Desafio("buscarCarrosPorMarca")
	public List<Long> buscarCarrosPorMarca(String marca) {
		return carros.stream().filter(c -> c.getMarca().equals(marca)).map(Carro::getId).collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarCor")
	public String buscarCor(Long idCarro) {
    	return buscarCarro(idCarro).orElseThrow(CarroNaoEncontradoException::new).getCorCarro();
	}

	@Override
	@Desafio("buscarMarcas")
	public List<String> buscarMarcas() {
		return this.carros.stream().map(Carro::getMarca).distinct().collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarNomePiloto")
	public String buscarNomePiloto(Long idPiloto) {
		return buscarPiloto(idPiloto).orElseThrow(PilotoNaoEncontradoException::new).getNome();
	}

	@Override
	@Desafio("buscarPilotoMaisExperiente")
	public Long buscarPilotoMaisExperiente() {
		return this.pilotos.stream().min(Comparator.comparing(Piloto::getDataInicioCarreira)).get().getId();
	}

	@Override
	@Desafio("buscarPilotoMenosExperiente")
	public Long buscarPilotoMenosExperiente() {
		return this.pilotos.stream().max(Comparator.comparing(Piloto::getDataInicioCarreira)).get().getId();
	}

	@Override
	@Desafio("buscarPilotos")
	public List<Long> buscarPilotos() {
		return this.pilotos.stream().sorted(Comparator.comparing(Piloto::getId)).map(Piloto::getId).collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarSaldo")
	public BigDecimal buscarSaldo(Long idPiloto) {
		return buscarPiloto(idPiloto).orElseThrow(PilotoNaoEncontradoException::new).getDinheiro();
	}

	@Override
	@Desafio("buscarValorPatrimonio")
	public BigDecimal buscarValorPatrimonio(Long idPiloto) {
		if (!verificaIdPiloto(idPiloto)) throw new PilotoNaoEncontradoException();
		return this.carros.stream().filter(c -> c.getIdPioloto().equals(idPiloto)).map(Carro::getPreco).reduce(new BigDecimal(0), BigDecimal::add);
	}

	@Override
	@Desafio("trocarCor")
	public void trocarCor(Long idCarro, String cor) {
		Carro carro = buscarCarro(idCarro).orElseThrow(CarroNaoEncontradoException::new);
		carro.setCorCarro(cor);
	}

}
