package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NeedForSpeedApplication test = new NeedForSpeedApplication();
		test.novoPiloto( 2l, "Joao", LocalDate.of(1990, 2, 10), LocalDate.of(2010, 1, 1), BigDecimal.valueOf(100000));
		test.novoPiloto( 1l, "Caio", LocalDate.of(1990, 2, 10), LocalDate.of(2010, 1, 2), BigDecimal.valueOf(90000));
		test.novoPiloto( 5l, "Andre", LocalDate.of(1990, 2, 10), LocalDate.of(2009, 1, 2), BigDecimal.valueOf(100000));
		test.comprarCarro( 3l, 1l, "Azul", "Audi", 1990, 10, BigDecimal.valueOf(10000));
		//test.comprarCarro( 3l, 1l, "Azul", "Audi", 1990, 10, BigDecimal.valueOf(2000));
		//test.comprarCarro( 3l, 3l, "Azul", "Audi", 1990, 10, BigDecimal.valueOf(2000));
		test.comprarCarro( 4l, 1l, "Azul", "Volks", 1990, 20, BigDecimal.valueOf(20000));
		test.comprarCarro( 12l, 2l, "Rosa", "Volks", 1989, 30, BigDecimal.valueOf(20000));
		test.comprarCarro( 13l, 1l, "Rosa", "Volks", 1989, 30, BigDecimal.valueOf(20000));
		//System.out.println(test.buscarCarroMaisCaro());
		//System.out.println(test.buscarCarroMaisPotente());
		System.out.println("Saldo" + test.buscarSaldo(1l));
		System.out.println("Patrimonio" + test.buscarValorPatrimonio(1l));
		System.out.println(test.buscarCarros(1l));
		test.venderCarro(3l);
		System.out.println(test.buscarCarros(1l));
		//System.out.println(test.buscarCarrosPorMarca("Aud"));
		//System.out.println(test.buscarMarcas());
		//System.out.println(test.buscarNomePiloto(2l));
		//System.out.println(test.buscarNomePiloto(10l));
		//System.out.println(test.buscarPilotoMaisExperiente());
		//System.out.println(test.buscarPilotoMenosExperiente());
		//System.out.println(test.buscarPilotos());
		System.out.println(test.buscarSaldo(1l));
		//System.out.println(test.buscarSaldo(10l));
		System.out.println(test.buscarValorPatrimonio(1l));
		//System.out.println(test.buscarCor(3l));
		//test.trocarCor(3l, "Rosa");
		//System.out.println(test.buscarCor(3l));
	}

}
