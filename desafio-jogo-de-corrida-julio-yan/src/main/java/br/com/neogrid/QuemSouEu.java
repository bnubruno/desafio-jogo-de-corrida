package br.com.neogrid;

import br.com.neogrid.desafio.app.QuemSouEuInterface;

public class QuemSouEu implements QuemSouEuInterface {

	@Override
	public String getNome() {
		return "Julio e Yan";
	}

	@Override
	public String getEmail() {
		return "juliocaye@gmail.com";
	}

	@Override
	public String getCelular() {
		return "asdf";
	}

	@Override
	public String getCurso() {
		return "asdf";
	}

}
