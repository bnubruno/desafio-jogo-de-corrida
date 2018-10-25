package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import br.com.neogrid.desafio.annotation.Desafio;
import br.com.neogrid.desafio.app.NeedForSpeedInterface;
import br.com.neogrid.desafio.exceptions.CarroNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.neogrid.desafio.exceptions.PilotoNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.SaldoInsuficienteException;

public class NeedForSpeedApplication implements NeedForSpeedInterface {

	HashMap<Long, Piloto> listaPiloto = new HashMap<Long, Piloto>();
	HashMap<Long, Carro> listaCarro = new HashMap<Long, Carro>();

	@Override
	@Desafio("novoPiloto")
	public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira, BigDecimal dinheiro) {
		if(listaPiloto.containsKey(id)){
			throw new IdentificadorUtilizadoException();
		}
		listaPiloto.put(id, new Piloto(id, nome, dataNascimento, dataInicioCarreira, dinheiro));
	}

	@Override
	@Desafio("comprarCarro")
	public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia, BigDecimal preco) {
		if(listaCarro.containsKey(id)){
			throw new IdentificadorUtilizadoException();
		}
		if(!listaPiloto.containsKey(idPiloto)){
			throw new PilotoNaoEncontradoException();
		}
		if(listaPiloto.get(idPiloto).getDinheiro().compareTo(preco) < 0){
			throw new SaldoInsuficienteException();
		}
		listaCarro.put(id, new Carro(id, idPiloto, cor, marca, ano, potencia, preco));
		listaPiloto.get(idPiloto).setDinheiro(listaPiloto.get(idPiloto).getDinheiro().subtract(preco));

	}

	@Override
	@Desafio("venderCarro")
	public void venderCarro(Long idCarro) {
		if(!listaCarro.containsKey(idCarro)){
			throw new CarroNaoEncontradoException();
		}
		listaPiloto.get(listaCarro.get(idCarro).getIdPiloto()).setDinheiro(listaPiloto.get(listaCarro.get(idCarro).getIdPiloto()).getDinheiro().add(listaCarro.get(idCarro).getPreco()));
		listaCarro.remove(idCarro);
	}

	@Override
	@Desafio("buscarCarroMaisCaro")
	public Long buscarCarroMaisCaro() {
//		Long id = null;
//		BigDecimal preco = null;
//
//		for(Long idCarro : listaCarro.keySet()){
//			if(id == null){
//				id = idCarro;
//				preco = listaCarro.get(idCarro).getPreco();
//			}
//			if((listaCarro.get(idCarro).getPreco().compareTo(preco) > 0) || (listaCarro.get(idCarro).getPreco().compareTo(preco) >= 0 && idCarro.compareTo(id) < 0)){
//				preco = listaCarro.get(idCarro).getPreco();
//				id = idCarro;
//			}
//		}

		BigDecimal maiorPreco = listaCarro.values().stream().sorted(Comparator.comparing(Carro::getPreco).reversed()).findFirst().get().getPreco();
		return listaCarro.values().stream().filter(carro -> carro.getPreco().equals(maiorPreco)).map(carro -> carro.getId()).sorted().findFirst().get();
//		return id;
	}

	@Override
	@Desafio("buscarCarroMaisPotente")
	public Long buscarCarroMaisPotente() {
//		Long id = null;
//		Integer potencia = null;
//		//erro
//		for(Long idCarro : listaCarro.keySet()){
//			if(id == null){
//				id = idCarro;
//				potencia = listaCarro.get(idCarro).getPotencia();
//			}
//			if((listaCarro.get(idCarro).getPotencia().compareTo(potencia) > 0) || (listaCarro.get(idCarro).getPotencia().compareTo(potencia) >= 0 && idCarro.compareTo(id) < 0)){
//				potencia = listaCarro.get(idCarro).getPotencia();
//				id = idCarro;
//			}
//		}
//		return id;

		Integer maiorPotencia = listaCarro.values().stream().sorted(Comparator.comparing(Carro::getPotencia).reversed()).findFirst().get().getPotencia();
		return listaCarro.values().stream().filter(carro -> carro.getPotencia().equals(maiorPotencia)).map(carro -> carro.getId()).sorted().findFirst().get();
	}

	@Override
	@Desafio("buscarCarros")
	public List<Long> buscarCarros(Long idPiloto) {
		if(!listaPiloto.containsKey(idPiloto)){
			throw new PilotoNaoEncontradoException();
		}
		return listaCarro
				.values().stream()
				.filter(carro -> carro.getIdPiloto().equals(idPiloto))
				.map(carro -> carro.getId()).sorted().collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarCarrosPorMarca")
	public List<Long> buscarCarrosPorMarca(String marca) {
		return listaCarro.values().stream().filter(carro -> carro.getMarca().equals(marca)).map(carro -> carro.getId()).collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarCor")
	public String buscarCor(Long idCarro) {
		if(!listaCarro.containsKey(idCarro)){
			throw new CarroNaoEncontradoException();
		}
		return listaCarro.get(idCarro).getCor();
	}

	@Override
	@Desafio("buscarMarcas")
	public List<String> buscarMarcas() {
		return listaCarro.values().stream().map(carro -> carro.getMarca()).distinct().collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarNomePiloto")
	public String buscarNomePiloto(Long idPiloto) {
		if(!listaPiloto.containsKey(idPiloto)){
			throw new PilotoNaoEncontradoException();
		}
		return listaPiloto.get(idPiloto).getNome();
	}

	@Override
	@Desafio("buscarPilotoMaisExperiente")
	public Long buscarPilotoMaisExperiente() {
		LocalDate maisXp = listaPiloto.values().stream().sorted(Comparator.comparing(Piloto::getDataInicioCarreira)).findFirst().get().getDataInicioCarreira();
		return listaPiloto.values().stream().filter(piloto -> piloto.getDataInicioCarreira().equals(maisXp)).map(piloto -> piloto.getId()).sorted().findFirst().get();

	}

	@Override
	@Desafio("buscarPilotoMenosExperiente")
	public Long buscarPilotoMenosExperiente() {
		LocalDate menosXp = listaPiloto.values().stream().sorted(Comparator.comparing(Piloto::getDataInicioCarreira).reversed()).findFirst().get().getDataInicioCarreira();
		return listaPiloto.values().stream().filter(piloto -> piloto.getDataInicioCarreira().equals(menosXp)).map(piloto -> piloto.getId()).sorted().findFirst().get();
	}

	@Override
	@Desafio("buscarPilotos")
	public List<Long> buscarPilotos() {
		return listaPiloto.keySet().stream().sorted().collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarSaldo")
	public BigDecimal buscarSaldo(Long idPiloto) {
		if(!listaPiloto.containsKey(idPiloto)){
			throw new PilotoNaoEncontradoException();
		}
		return listaPiloto.get(idPiloto).getDinheiro();
	}

	@Override
	@Desafio("buscarValorPatrimonio")
	public BigDecimal buscarValorPatrimonio(Long idPiloto) {
		if(!listaPiloto.containsKey(idPiloto)){
			throw new PilotoNaoEncontradoException();
		}
		return listaCarro.values().stream().filter(carro -> carro.getIdPiloto().equals(idPiloto)).map(carro -> carro.getPreco()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	@Desafio("trocarCor")
	public void trocarCor(Long idCarro, String cor) {
		if(!listaCarro.containsKey(idCarro)){
			throw new CarroNaoEncontradoException();
		}
		listaCarro.get(idCarro).setCor(cor);
	}

}
