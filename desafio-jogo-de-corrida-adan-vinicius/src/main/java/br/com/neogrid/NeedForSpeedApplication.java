package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import br.com.neogrid.desafio.annotation.Desafio;
import br.com.neogrid.desafio.app.NeedForSpeedInterface;
import br.com.neogrid.desafio.exceptions.CarroNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.neogrid.desafio.exceptions.PilotoNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.SaldoInsuficienteException;

public class NeedForSpeedApplication implements NeedForSpeedInterface {

	Map<Long, Carro> lista_carro = new TreeMap<>();
	Map<Long, Piloto> lista_piloto = new TreeMap<>();
	Carro carroMaisCaro = new Carro(null, null, null, null, null, null, new BigDecimal(0));
	Carro carroMaisPotente = new Carro(null, null, null, null, null, 0, null);

	@Override
	@Desafio("novoPiloto")
	public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira,
			BigDecimal dinheiro) {

		if (lista_piloto.containsKey(id)) {
			throw new IdentificadorUtilizadoException();
		}

		Piloto piloto = new Piloto(id, nome, dataNascimento, dataInicioCarreira, dinheiro);
		lista_piloto.put(id, piloto);
}

	@Override
	@Desafio("comprarCarro")
	public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia,
			BigDecimal preco) {

		if (lista_carro.containsKey(id)) {
			throw new IdentificadorUtilizadoException();
		}
		if (!lista_piloto.containsKey(idPiloto)) {
			throw new PilotoNaoEncontradoException();
		}
		if (lista_piloto.get(idPiloto).getDinheiro().compareTo(preco) < 0) {
			throw new SaldoInsuficienteException();
		}

		Carro carro = new Carro(id, idPiloto, cor, marca, ano, potencia, preco);
		lista_carro.put(id, carro);
		lista_piloto.get(idPiloto).setDinheiro(lista_piloto.get(idPiloto).getDinheiro().subtract(preco));
		
		if ((this.carroMaisCaro.getPreco().compareTo(carro.getPreco()) < 0 ) || (this.carroMaisCaro.getPreco().compareTo(carro.getPreco()) == 0 && this.carroMaisCaro.getId() > carro.getId())) {
			this.carroMaisCaro = carro;
		}
		if ((this.carroMaisPotente.getPotencia() < carro.getPotencia()) || (this.carroMaisPotente.getPotencia() == carro.getPotencia() && this.carroMaisPotente.getId() > carro.getId())) {
			this.carroMaisPotente = carro;
		}
	}

	@Override
	@Desafio("venderCarro")
	public void venderCarro(Long idCarro) {
		if(!lista_carro.containsKey(idCarro)) {
			throw new CarroNaoEncontradoException();
		}
		Long idPiloto = lista_carro.get(idCarro).getIdPiloto();
		lista_piloto.get(idPiloto).setDinheiro(lista_piloto.get(idPiloto).getDinheiro().add(lista_carro.get(idCarro).getPreco()));
		lista_carro.remove(idCarro);
	}

	@Override
	@Desafio("buscarCarroMaisCaro")
	public Long buscarCarroMaisCaro() {
		Long carroCaro = this.carroMaisCaro.getId();
		System.out.print(carroCaro);
		return carroCaro;
	}

	@Override
	@Desafio("buscarCarroMaisPotente")
	public Long buscarCarroMaisPotente() {
		Long carroPotente = this.carroMaisPotente.getId();
		System.out.print(carroPotente);
		return carroPotente;
	}

	@Override
	@Desafio("buscarCarros")
	public List<Long> buscarCarros(Long idPiloto) {
		if(!lista_piloto.containsKey(idPiloto)) {
			throw new PilotoNaoEncontradoException();
		}
		return this.lista_carro.values().stream()
				.filter(c -> (c.getIdPiloto().equals(idPiloto)))
				.map(c -> c.getId()).sorted().collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarCarrosPorMarca")
	public List<Long> buscarCarrosPorMarca(String marca) {
		return this.lista_carro.values().stream()
				.filter(c -> c.getMarca().equals(marca))
				.map(c -> c.getId())
				.collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarCor")
	public String buscarCor(Long idCarro) {
		if (!this.lista_carro.containsKey(idCarro)) {
			throw new CarroNaoEncontradoException();
		}
		return this.lista_carro.get(idCarro).getCor();
	}

	@Override
	@Desafio("buscarMarcas")
	public List<String> buscarMarcas() {
		return this.lista_carro.values().stream().map(c -> c.getMarca()).distinct().collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarNomePiloto")
	public String buscarNomePiloto(Long idPiloto) {
		if (!this.lista_piloto.containsKey(idPiloto)) {
			throw new PilotoNaoEncontradoException();
		}
		return this.lista_piloto.get(idPiloto).getNome();

	}

	@Override
	@Desafio("buscarPilotoMaisExperiente")
	public Long buscarPilotoMaisExperiente() {
		LocalDate maisXp = lista_piloto.values().stream().sorted(Comparator.comparing(Piloto::getDataInicioCarreira)).findFirst().get().getDataInicioCarreira();
		return lista_piloto.values().stream().filter(piloto -> piloto.getDataInicioCarreira().equals(maisXp)).map(piloto -> piloto.getId()).sorted().findFirst().get();
	}

	@Override
	@Desafio("buscarPilotoMenosExperiente")
	public Long buscarPilotoMenosExperiente() {
		LocalDate menosXp = lista_piloto.values().stream().sorted(Comparator.comparing(Piloto::getDataInicioCarreira).reversed()).findFirst().get().getDataInicioCarreira();
		return lista_piloto.values().stream().filter(piloto -> piloto.getDataInicioCarreira().equals(menosXp)).map(piloto -> piloto.getId()).sorted().findFirst().get();		
	}

	@Override
	@Desafio("buscarPilotos")
	public List<Long> buscarPilotos() {
		return lista_piloto.keySet().stream().sorted().collect(Collectors.toList());
	}

	@Override
	@Desafio("buscarSaldo")
	public BigDecimal buscarSaldo(Long idPiloto) {
		if (!this.lista_piloto.containsKey(idPiloto)) {
			throw new PilotoNaoEncontradoException();
		}
		return this.lista_piloto.get(idPiloto).getDinheiro();
	}

	@Override
	@Desafio("buscarValorPatrimonio")
	public BigDecimal buscarValorPatrimonio(Long idPiloto) {
		if (!this.lista_piloto.containsKey(idPiloto)) {
			throw new PilotoNaoEncontradoException();
		}
		return lista_carro.values().stream().filter(carro -> carro.getIdPiloto().equals(idPiloto)).map(carro -> carro.getPreco()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	@Desafio("trocarCor")
	public void trocarCor(Long idCarro, String cor) {
		if (!this.lista_carro.containsKey(idCarro)) {
			throw new CarroNaoEncontradoException();
		}
		this.lista_carro.get(idCarro).setCor(cor);
	}
	
	public static void main(String[] args) {
		
		LocalDate today = LocalDate.now();
//	    LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
//	    LocalDate yesterday = tomorrow.minusDays(2);
//	    LocalDate maisXp = tomorrow.minusDays(50);
//	    LocalDate menosXp = today.plus(6, ChronoUnit.DAYS);
//		
		NeedForSpeedApplication needForSpeedApplication = new NeedForSpeedApplication();
		needForSpeedApplication.novoPiloto(1l, "piloto hoje", today, today, new BigDecimal(45000));
//		needForSpeedApplication.novoPiloto(2l, "piloto amanha", tomorrow, tomorrow, new BigDecimal(45000));
//		needForSpeedApplication.novoPiloto(3l, "piloto anteontem", yesterday, yesterday, new BigDecimal(45000));
//		
//		
//		needForSpeedApplication.novoPiloto(4l, "piloto hoje", today, today, new BigDecimal(45000));
//		needForSpeedApplication.novoPiloto(5l, "piloto amanha", tomorrow, tomorrow, new BigDecimal(45000));
//		needForSpeedApplication.novoPiloto(6l, "piloto anteontem", yesterday, menosXp, new BigDecimal(45000));
//		
//		needForSpeedApplication.novoPiloto(7l, "piloto hoje", today, today, new BigDecimal(45000));
//		needForSpeedApplication.novoPiloto(8l, "piloto amanha", tomorrow, tomorrow, new BigDecimal(45000));
//		needForSpeedApplication.novoPiloto(9l, "piloto 9", yesterday, maisXp, new BigDecimal(45000));
		
//		needForSpeedApplication.buscarPilotoMaisExperiente();
//
//		needForSpeedApplication.buscarPilotoMenosExperiente();

//		needForSpeedApplication.novoPiloto(1l, "piloto hoje", today, today, new BigDecimal(45000));
		needForSpeedApplication.comprarCarro(15l, 1l, "branco", "vw", 2015, 350, new BigDecimal(1500));
		needForSpeedApplication.comprarCarro(2l, 1l, "branco", "vw", 2015, 250, new BigDecimal(2500));
		needForSpeedApplication.comprarCarro(4l, 1l, "branco", "fiat", 2015, 250, new BigDecimal(2500));
		
//		needForSpeedApplication.buscarCarroMaisCaro();
		List<String> bla = needForSpeedApplication.buscarMarcas();
		System.out.print(bla);
	}

}
