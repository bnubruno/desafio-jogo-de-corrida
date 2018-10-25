package br.com.neogrid;

import br.com.neogrid.desafio.app.QuemSouEuInterface;

public class QuemSouEu implements QuemSouEuInterface {

	@Override
	public String getNome() {
		return "Ramon e Yuri";
	}

	@Override
	public String getEmail() {
		return "ramon.artner@gmail.com/yurisalvador@hotmail.com";
	}

	@Override
	public String getCelular() {
		return "9999999999";
	}

	@Override
	public String getCurso() {
		return "Academia DEV";
	}

}
