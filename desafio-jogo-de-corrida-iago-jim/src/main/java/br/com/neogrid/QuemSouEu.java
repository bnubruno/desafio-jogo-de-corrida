package br.com.neogrid;

import br.com.neogrid.desafio.app.QuemSouEuInterface;

public class QuemSouEu implements QuemSouEuInterface {

	@Override
	public String getNome() {
		return "Iago e Jim";
	}

	@Override
	public String getEmail() {
		return "jim_chien_br@hotmail.com - go.marinheiro@gmail.com";
	}

	@Override
	public String getCelular() {
		return "9";
	}

	@Override
	public String getCurso() {
		return "sldkja";
	}

}
