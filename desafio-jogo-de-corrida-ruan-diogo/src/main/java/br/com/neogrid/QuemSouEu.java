package br.com.neogrid;

import br.com.neogrid.desafio.app.QuemSouEuInterface;

public class QuemSouEu implements QuemSouEuInterface {

	@Override
	public String getNome() {
		return "Diogo e Ruan";
	}

	@Override
	public String getEmail() {
		return "diogoantelo@hotmail.com";
	}

	@Override
	public String getCelular() {
		return "9840700063";
	}

	@Override
	public String getCurso() {
		return "Computação UDESC";
	}

}
