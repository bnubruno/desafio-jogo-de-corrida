package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import br.com.neogrid.desafio.annotation.Desafio;
import br.com.neogrid.desafio.app.NeedForSpeedInterface;
import br.com.neogrid.desafio.exceptions.CarroNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.neogrid.desafio.exceptions.PilotoNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.SaldoInsuficienteException;

public class NeedForSpeedApplication implements NeedForSpeedInterface {

	private List<Piloto> pilotos = new ArrayList<>();
	private List<Carro> carros = new ArrayList<>();

	@Override
	@Desafio("novoPiloto")
	public void novoPiloto(Long id, String nome, LocalDate dataNascimento, LocalDate dataInicioCarreira,
			BigDecimal dinheiro) {

		for (Piloto p1 : pilotos) {
			if (p1.getId() == id) {
				throw new IdentificadorUtilizadoException();
			}
		}

		Piloto p = new Piloto(id, nome, dataNascimento, dataInicioCarreira, dinheiro);
		pilotos.add(p);
	}

	@Override
	@Desafio("comprarCarro")
	public void comprarCarro(Long id, Long idPiloto, String cor, String marca, Integer ano, Integer potencia,
			BigDecimal preco) {

		for (Carro c1 : carros) {
			if (c1.getId().equals(id)) {
				throw new IdentificadorUtilizadoException();
			}
		}

		Carro c = new Carro(id, idPiloto, cor, marca, ano, potencia, preco);

		boolean encontrado = false;
		for (Piloto p1 : pilotos) {
			if (p1.getId().equals(idPiloto)) {
				if (p1.getDinheiro().compareTo(preco) == -1) {
					throw new SaldoInsuficienteException();
				}
				encontrado = true;
				p1.setDinheiro(p1.getDinheiro().subtract(preco));
				break;
			}
		}

		if (!encontrado) {
			throw new PilotoNaoEncontradoException();
		}

		carros.add(c);
	}

	@Override
	@Desafio("venderCarro")
	public void venderCarro(Long idCarro) {
		Long idPiloto = 0l;
		BigDecimal preco = new BigDecimal(0);
		boolean existeCarro = false;

		for (Carro c1 : carros) {
			if (c1.getId().equals(idCarro)) {
				existeCarro = true;
				idPiloto = c1.getIdPiloto();
				preco = c1.getPreco();
				carros.remove(c1);
				break;
			}
		}

		if (!existeCarro) {
			throw new CarroNaoEncontradoException();
		}

		for (Piloto p1 : pilotos) {
			if (p1.getId() == idPiloto) {
				p1.setDinheiro(p1.getDinheiro().add(preco));
				break;
			}
		}

	}

	@Override
	@Desafio("buscarCarroMaisCaro")
	public Long buscarCarroMaisCaro() {
		Long maiorId = 0l;
		BigDecimal maiorPreco = new BigDecimal(0);

		for (Carro c1 : carros) {
			if (c1.getPreco().compareTo(maiorPreco) == 0) {
				if (c1.getId().compareTo(maiorId) == -1) {
					maiorPreco = c1.getPreco();
					maiorId = c1.getId();
				}
			} else if (c1.getPreco().compareTo(maiorPreco) == 1) {
				maiorPreco = c1.getPreco();
				maiorId = c1.getId();
			}
		}
		return maiorId;
	}

	@Override
	@Desafio("buscarCarroMaisPotente")
	public Long buscarCarroMaisPotente() {
		Long maiorId = 0l;
		int maiorPotencia = 0;

		for (Carro c1 : carros) {
			if (c1.getPotencia() == maiorPotencia) {
				if (c1.getId().compareTo(maiorId) == -1) {
					maiorPotencia = c1.getPotencia();
					maiorId = c1.getId();
				}
			} else if (c1.getPotencia() > maiorPotencia) {
				maiorPotencia = c1.getPotencia();
				maiorId = c1.getId();
			}
		}
		return maiorId;
	}

	@Override
	@Desafio("buscarCarros")
	public List<Long> buscarCarros(Long idPiloto) {
		boolean encontrado = false;

		for (Piloto p : pilotos) {
			if (p.getId() == idPiloto) {
				encontrado = true;
				break;
			}
		}

		if (!encontrado) {
			throw new PilotoNaoEncontradoException();
		}

		List<Long> x = new ArrayList<>();

		for (Carro c1 : carros) {
			if (c1.getIdPiloto() == idPiloto) {
				x.add(c1.getId());
			}
		}

		Collections.sort(x);
		return x;
	}

	@Override
	@Desafio("buscarCarrosPorMarca")
	public List<Long> buscarCarrosPorMarca(String marca) {
		List<Long> x = new ArrayList<>();

		for (Carro c1 : carros) {
			if (c1.getMarca() == marca) {
				x.add(c1.getId());
			}
		}

		Collections.sort(x);
		return x;
	}

	@Override
	@Desafio("buscarMarcas")
	public List<String> buscarMarcas() {
		HashSet<String> x = new HashSet<>();

		for (Carro c1 : carros) {
			x.add(c1.getMarca());
		}

		return new ArrayList<String>(x);
	}

	@Override
	@Desafio("buscarNomePiloto")
	public String buscarNomePiloto(Long idPiloto) {

		for (Piloto p : pilotos) {
			if (p.getId() == idPiloto) {
				return p.getNome();
			}
		}

		throw new PilotoNaoEncontradoException();
	}

	@Override
	@Desafio("buscarPilotoMaisExperiente")
	public Long buscarPilotoMaisExperiente() {
		Long id = 0l;
		LocalDate maior = LocalDate.of(9999, 10, 10);
		boolean encontrou = false;

		for (Piloto p : pilotos) {
			if (p.getDataInicioCarreira().compareTo(maior) < 0) {
				maior = p.getDataInicioCarreira();
				id = p.getId();
				encontrou = true;
			}
		}

		if (!encontrou) {
			throw new PilotoNaoEncontradoException();
		}
		return id;
	}

	@Override
	@Desafio("buscarPilotoMenosExperiente")
	public Long buscarPilotoMenosExperiente() {
		Long id = 0l;
		LocalDate maior = LocalDate.of(1000, 10, 10);
		boolean encontrou = false;

		for (Piloto p : pilotos) {
			if (p.getDataInicioCarreira().compareTo(maior) > 0) {
				maior = p.getDataInicioCarreira();
				id = p.getId();
				encontrou = true;
			}
		}

		if (!encontrou) {
			throw new PilotoNaoEncontradoException();
		}
		return id;
	}

	@Override
	@Desafio("buscarPilotos")
	public List<Long> buscarPilotos() {
		HashSet<Long> x = new HashSet<>();

		for (Piloto p : pilotos) {
			x.add(p.getId());
		}

		return new ArrayList<Long>(x);

	}

	@Override
	@Desafio("buscarSaldo")
	public BigDecimal buscarSaldo(Long idPiloto) {

		for (Piloto p : pilotos) {
			if (p.getId() == idPiloto) {
				return p.getDinheiro();
			}
		}

		throw new PilotoNaoEncontradoException();
	}

	@Override
	@Desafio("buscarValorPatrimonio")
	public BigDecimal buscarValorPatrimonio(Long idPiloto) {

		boolean encontrado = false;
		for (Piloto p : pilotos) {
			if (p.getId().equals(idPiloto)) {
				encontrado = true;
			}
		}

		if (!encontrado) {
			throw new PilotoNaoEncontradoException();
		}

		BigDecimal valorTotal = new BigDecimal(0);

		for (Carro c : carros) {
			if (c.getIdPiloto().equals(idPiloto)) {
				valorTotal = valorTotal.add(c.getPreco());
			}
		}

		return valorTotal;
	}

	@Override
	@Desafio("buscarCor")
	public String buscarCor(Long idCarro) {
		for (Carro c : carros) {
			if (c.getId().equals(idCarro)) {
				return c.getCor();
			}
		}

		throw new CarroNaoEncontradoException();
	}

	@Override
	@Desafio("trocarCor")
	public void trocarCor(Long idCarro, String cor) {

		boolean encontrado = false;

		for (Carro c : carros) {
			if (c.getId().equals(idCarro)) {
				c.setCor(cor);
				encontrado = true;
				break;
			}
		}

		if (!encontrado) {
			throw new CarroNaoEncontradoException();
		}

	}

}
