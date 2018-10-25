package br.com.neogrid;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        NeedForSpeedApplication classe = new NeedForSpeedApplication();

        classe.novoPiloto(1L, "Bruno", LocalDate.of(1998, 7,21), LocalDate.of(2018, 10, 10), BigDecimal.valueOf(100000));
        classe.novoPiloto(2L, "Bruno2", LocalDate.of(1998,7,21), LocalDate.of(2018, 10, 10), BigDecimal.valueOf(100000));
        classe.comprarCarro(1L, 1L, "vermelha", "bmw", 2018, 20001, BigDecimal.valueOf(2001));
        classe.comprarCarro(2L, 1L, "vermelha", "bmw", 2018, 20001, BigDecimal.valueOf(2001));
        classe.comprarCarro(3L, 1L, "vermelha", "bmw", 2018, 20001, BigDecimal.valueOf(20011));
        System.out.println(classe.buscarCarros(1L));
    }

}
