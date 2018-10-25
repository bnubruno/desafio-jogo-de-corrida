package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.neogrid.desafio.annotation.Desafio;
import br.com.neogrid.desafio.app.NeedForSpeedInterface;
import br.com.neogrid.desafio.exceptions.CarroNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.PilotoNaoEncontradoException;

public class NeedForSpeedApplication implements NeedForSpeedInterface {
	
	private Map<Long, Piloto> pilotos;
	private Map<Long, Carro> carros;
	
	public NeedForSpeedApplication() {
		pilotos= new HashMap<Long, Piloto>(); 
		carros = new HashMap<Long, Carro>();
	}

	@Override
	@Desafio("novoPiloto")
	public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
		if(pilotos.containsKey(id)){
			throw new br.com.neogrid.desafio.exceptions.IdentificadorUtilizadoException();
		}else{
			
			Piloto piloto = new Piloto();
			piloto.setNome(nome);
			piloto.setDataInicioCarreira(dataInicioCarreira);
			piloto.setDataNascimento(dataNascimento);
			piloto.setDinheiro(dinheiro);
			piloto.setId(id);
			pilotos.put(id, piloto);
		}
	}

	@Override
	@Desafio("comprarCarro")
	public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
		if(carros.containsKey(id))
			throw new br.com.neogrid.desafio.exceptions.IdentificadorUtilizadoException(); 
		if(!pilotos.containsKey(idPiloto))
			throw new br.com.neogrid.desafio.exceptions.PilotoNaoEncontradoException();
		if(pilotos.get(idPiloto).getDinheiro().compareTo(preco) < 0)
			throw new br.com.neogrid.desafio.exceptions.SaldoInsuficienteException();
			
		Carro carro = new Carro();
		carro.setAno(ano);
		carro.setCor(cor);
		carro.setMarca(marca);
		carro.setPotencia(potencia);
		carro.setPreco(preco);
		carro.setIdPiloto(idPiloto);
		carro.setId(id);
		carros.put(id, carro);
		
		pilotos.get(idPiloto).setDinheiro(pilotos.get(idPiloto).getDinheiro().subtract(carro.getPreco()));
	}

	@Override
	@Desafio("venderCarro")
	public void venderCarro(Long idCarro) {
		if(!carros.containsKey(idCarro)) {
			throw new br.com.neogrid.desafio.exceptions.CarroNaoEncontradoException();
		}
		
		Piloto piloto_ = pilotos.get(carros.get(idCarro).getIdPiloto());
		pilotos.get(piloto_.getId()).setDinheiro(piloto_.getDinheiro().add(carros.get(idCarro).getPreco()));
		
		carros.remove(idCarro);

	}

	@Override
	@Desafio("buscarCarroMaisCaro")
	public Long buscarCarroMaisCaro() {
		return carros.values().stream()
				.sorted(Comparator.comparing(Carro::getPotencia).reversed().thenComparing(Carro::getId))
				.findFirst()
			    .get().getId();
	}

	@Override
	@Desafio("buscarCarroMaisPotente")
	public Long buscarCarroMaisPotente() {
		return carros.values().stream()
				.max(Comparator.comparing(Carro::getPotencia))
			    .get().getId();
	}

	@Override
	@Desafio("buscarCarros")
	public List<Long> buscarCarros(Long idPiloto) {
		if(!pilotos.containsKey(idPiloto)) {
			throw new PilotoNaoEncontradoException();
		}
	
		return carros.entrySet().stream()
				.filter(f -> f.getValue().getIdPiloto().equals(idPiloto))
				.map(m -> m.getKey())
				.collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarCarrosPorMarca")
	public List<Long> buscarCarrosPorMarca(String marca) {
		
		return carros.entrySet().stream()
				.filter(f -> f.getValue().getMarca().equals(marca)).map(m -> m.getKey())
				.collect(Collectors.toList());		
	}

	@Override
	@Desafio("buscarCor")
	public String buscarCor(Long idCarro) {
		if(!carros.containsKey(idCarro))
			throw new CarroNaoEncontradoException();
		
		return carros.entrySet().stream().filter(f -> f.getKey().equals(idCarro)).map(m -> m.getValue().getCor()).findFirst().get();
	}

	@Override
	@Desafio("buscarMarcas")
	public List<String> buscarMarcas() {
		return carros.entrySet().stream()
				.map(m -> m.getValue().getMarca())
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarNomePiloto")
	public String buscarNomePiloto(Long idPiloto) {
		if(!pilotos.containsKey(idPiloto))
			throw new PilotoNaoEncontradoException();
		
		return pilotos.entrySet().stream().filter(f -> f.getKey().equals(idPiloto)).map(m -> m.getValue().getNome()).findFirst().get();
			
	}

	@Override
	@Desafio("buscarPilotoMaisExperiente")
	public Long buscarPilotoMaisExperiente() {
		return pilotos.values().stream()
				.max(Comparator.comparing(Piloto::getDataInicioCarreira).reversed())
			    .get().getId();
	}

	@Override
	@Desafio("buscarPilotoMenosExperiente")
	public Long buscarPilotoMenosExperiente() {
		return pilotos.values().stream()
				.max(Comparator.comparing(Piloto::getDataInicioCarreira))
			    .get().getId();
	}

	@Override
	@Desafio("buscarPilotos")
	public List<Long> buscarPilotos() {
		return	pilotos.entrySet().stream().map(m -> m.getKey())
				.sorted()
				.collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarSaldo")
	public BigDecimal buscarSaldo(Long idPiloto) {
		if(!pilotos.containsKey(idPiloto))
			throw new PilotoNaoEncontradoException();
		
		return pilotos.entrySet().stream().filter(f -> f.getKey().equals(idPiloto))
				.map(m -> m.getValue().getDinheiro())
				.findFirst()
				.get();
	}

	@Override
	@Desafio("buscarValorPatrimonio")
	public BigDecimal buscarValorPatrimonio(Long idPiloto) {
		if(!pilotos.containsKey(idPiloto))
			throw new PilotoNaoEncontradoException();
		return carros.entrySet().stream()
				.filter(f -> f.getValue().getIdPiloto().equals(idPiloto))
				.map(m -> m.getValue().getPreco())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	@Desafio("trocarCor")
	public void trocarCor(Long idCarro, String cor) {
		if(!carros.containsKey(idCarro))
			throw new CarroNaoEncontradoException();
		
		carros.get(idCarro).setCor(cor);
	}
	
	public static void main(String args[]) {
		NeedForSpeedApplication need = new NeedForSpeedApplication();
		
		need.novoPiloto(2L, "piloto1", LocalDate.of(1900,1,1), LocalDate.of(1900,1,1), new BigDecimal(10000));
		need.novoPiloto(1L, "piloto1", LocalDate.now(), LocalDate.now(), new BigDecimal(10000));
		need.novoPiloto(3L, "piloto1", LocalDate.now(), LocalDate.now(), new BigDecimal(10000));
		
		System.out.println(need.pilotos.get(1L).getDinheiro());
		
		need.comprarCarro(5L, 1L, "asdsd", "bbb", 1900, 100, new BigDecimal(300));
		need.comprarCarro(6L, 1L, "aaa", "bbb", 1900, 100, new BigDecimal(700));
		need.comprarCarro(7L, 2L, "aaa", "bbb", 1900, 100, new BigDecimal(10));
		
		System.out.println(need.pilotos.get(1L).getDinheiro());
		need.venderCarro(5L);
		System.out.println(need.pilotos.get(1L).getDinheiro());
		
		System.out.println(need.buscarValorPatrimonio(1L));
		
	}
	

}
