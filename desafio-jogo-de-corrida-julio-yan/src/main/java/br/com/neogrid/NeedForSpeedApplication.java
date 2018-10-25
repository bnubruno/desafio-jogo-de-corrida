package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.stream.Collectors;

import br.com.neogrid.desafio.annotation.Desafio;
import br.com.neogrid.desafio.app.NeedForSpeedInterface;
import br.com.neogrid.desafio.exceptions.CarroNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.neogrid.desafio.exceptions.PilotoNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.SaldoInsuficienteException;

public class NeedForSpeedApplication implements NeedForSpeedInterface {
	
	private List<Carro> carros = new ArrayList<>();
	private List<Piloto> pilotos = new ArrayList<>();

	public List<Carro> getCarros(){
		return carros;
	}

	public List<Piloto> getPilotos(){
		return pilotos;
	}

	@Override
	@Desafio("novoPiloto")
	public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
		if (pilotos.stream().anyMatch(j -> j.getId().equals(id)))
			throw new IdentificadorUtilizadoException();
		pilotos.add(new Piloto(id, nome, dataNascimento, dataInicioCarreira, dinheiro));
	}

	@Override
	@Desafio("comprarCarro")
	public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
		if (carros.stream().anyMatch(c -> c.getId().equals(id)))
			throw new IdentificadorUtilizadoException();

		Piloto piloto = pilotos.stream()
				.filter(p -> p.getId().equals(idPiloto))
				.findFirst()
				.orElseThrow(PilotoNaoEncontradoException::new);
		
		if (piloto.getDinheiro().compareTo(preco) < 0)
			throw new SaldoInsuficienteException();
		
		Carro carro = new Carro(id, piloto, cor, marca, ano, potencia, preco);
		carros.add(carro);
		piloto.addCarro(carro);
		piloto.setDinheiro(piloto.getDinheiro().subtract(preco));
	}

	@Override
	@Desafio("venderCarro")
	public void venderCarro(Long idCarro) {
		Carro carro = carros.stream()
				.filter(c -> c.getId().equals(idCarro))
				.findFirst()
				.orElseThrow(CarroNaoEncontradoException::new);
		
		Piloto piloto = carro.getPiloto();
		
		piloto.setDinheiro(piloto.getDinheiro().add(carro.getPreco()));
		
		piloto.rmvCarro(carro);
		carros.remove(carro);
	}

	@Override
	@Desafio("buscarCarroMaisCaro")
	public Long buscarCarroMaisCaro() {
		return carros.stream()
				.sorted(Comparator.comparing(Carro::getPreco).reversed().thenComparing(Carro::getId))
				.findFirst()
				.orElseThrow(UnsupportedOperationException::new)
				.getId();
	}

	@Override
	@Desafio("buscarCarroMaisPotente")
	public Long buscarCarroMaisPotente() {
		return carros.stream()
				.sorted(Comparator.comparing(Carro::getPotencia).reversed().thenComparing(Carro::getId))
				.findFirst()
				.orElseThrow(UnsupportedOperationException::new)
				.getId();
	}

	@Override
	@Desafio("buscarCarros")
	public List<Long> buscarCarros(Long idPiloto) {
		return pilotos.stream()
				.filter(p -> p.getId().equals(idPiloto))
				.findFirst()
				.orElseThrow(PilotoNaoEncontradoException::new)
				.getCarro()
				.stream()
				.map(c -> c.getId())
				.sorted()
				.collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarCarrosPorMarca")
	public List<Long> buscarCarrosPorMarca(String marca) {
		return carros.stream()
				.filter(c -> c.getMarca().equals(marca))
				.map(c -> c.getId())
				.collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarCor")
	public String buscarCor(Long idCarro) {
		return carros.stream()
				.filter(c -> c.getId().equals(idCarro))
				.findFirst()
				.orElseThrow(CarroNaoEncontradoException::new)
				.getCor();
	}

	@Override
	@Desafio("buscarMarcas")
	public List<String> buscarMarcas() {
		return carros.stream()
				.map(c -> c.getMarca())
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarNomePiloto")
	public String buscarNomePiloto(Long idPiloto) {
		return pilotos.stream()
				.filter(p -> p.getId().equals(idPiloto))
				.findFirst()
				.orElseThrow(PilotoNaoEncontradoException::new)
				.getNome();
	}

	@Override
	@Desafio("buscarPilotoMaisExperiente")
	public Long buscarPilotoMaisExperiente() {
		return pilotos.stream()
				.sorted(Comparator.comparing(Piloto::getDataInicioCarreira).thenComparing(Piloto::getId))
				.findFirst()
				.orElseThrow(UnsupportedOperationException::new)
				.getId();
	}

	@Override
	@Desafio("buscarPilotoMenosExperiente")
	public Long buscarPilotoMenosExperiente() {
		return pilotos.stream()
				.sorted(Comparator.comparing(Piloto::getDataInicioCarreira).reversed().thenComparing(Piloto::getId))
				.findFirst()
				.orElseThrow(UnsupportedOperationException::new)
				.getId();
	}

	@Override
	@Desafio("buscarPilotos")
	public List<Long> buscarPilotos() {
		return pilotos.stream()
				.map(p -> p.getId())
				.sorted()
				.collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarSaldo")
	public BigDecimal buscarSaldo(Long idPiloto) {
		return pilotos.stream()
				.filter(p -> p.getId().equals(idPiloto))
				.findFirst()
				.orElseThrow(PilotoNaoEncontradoException::new)
				.getDinheiro();
	}

	@Override
	@Desafio("buscarValorPatrimonio")
	public BigDecimal buscarValorPatrimonio(Long idPiloto) {
		return pilotos.stream()
				.filter(p -> p.getId().equals(idPiloto))
				.findFirst()
				.orElseThrow(PilotoNaoEncontradoException::new)
				.getCarro()
				.stream()
				.map(c -> c.getPreco())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	@Desafio("trocarCor")
	public void trocarCor(Long idCarro, String cor) {
		carros.stream()
		.filter(c -> c.getId().equals(idCarro))
		.findFirst()
		.orElseThrow(CarroNaoEncontradoException::new)
		.setCor(cor);
	}

}
