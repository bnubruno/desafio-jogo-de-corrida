package br.com.neogrid;

import br.com.neogrid.desafio.app.QuemSouEuInterface;

public class QuemSouEu implements QuemSouEuInterface {

	@Override
	public String getNome() {
		return "Luiz e Marco";
	}

	@Override
	public String getEmail() {
		return "botucacontato@gmail.com;marcoayamada@gmail.com";
	}

	@Override
	public String getCelular() {
		return "214234";
	}

	@Override
	public String getCurso() {
		return "sdfuihwwu4";
	}

}
