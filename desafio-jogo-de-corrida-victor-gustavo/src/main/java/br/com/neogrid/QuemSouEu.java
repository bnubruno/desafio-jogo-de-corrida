package br.com.neogrid;

import br.com.neogrid.desafio.app.QuemSouEuInterface;

public class QuemSouEu implements QuemSouEuInterface {

	@Override
	public String getNome() {
		return "Victor e Gustavo";
	}

	@Override
	public String getEmail() {
		return "vlmmafra@gmail.com";
	}

	@Override
	public String getCelular() {
		return "0000000";
	}

	@Override
	public String getCurso() {
		return "123120938";
	}

}
