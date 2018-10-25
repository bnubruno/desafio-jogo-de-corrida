package br.com.neogrid;

import br.com.neogrid.desafio.app.QuemSouEuInterface;

public class QuemSouEu implements QuemSouEuInterface {

	@Override
	public String getNome() {
		return "Helena e Larissa";
	}

	@Override
	public String getEmail() {
		return "hel@gmail.com";
	}

	@Override
	public String getCelular() {
		return "123";
	}

	@Override
	public String getCurso() {
		return "abc";
	}

}
