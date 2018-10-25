package br.com.neogrid;

import br.com.neogrid.desafio.app.QuemSouEuInterface;

public class QuemSouEu implements QuemSouEuInterface {

	@Override
	public String getNome() {
		return "Adam e Vinícius";
	}

	@Override
	public String getEmail() {
		return "adammews@gmail.com | ifc.vinicius@gmail.com";
	}

	@Override
	public String getCelular() {
		return "(47) 996869332 | (47) 992095717";
	}

	@Override
	public String getCurso() {
		return "Computação | BSI";
	}

}
