package br.com.neogrid;

import br.com.neogrid.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.neogrid.desafio.exceptions.PilotoNaoEncontradoException;
import br.com.neogrid.desafio.exceptions.SaldoInsuficienteException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;

public class NeedForSpeedApplicationTest {
    public NeedForSpeedApplication settingTest(){
        NeedForSpeedApplication needForSpeedApplication = new NeedForSpeedApplication();

        needForSpeedApplication.novoPiloto(1l, "Goiaba", LocalDate.of(1992, 10 ,31),
                LocalDate.of(2015, 03 ,02), BigDecimal.valueOf(15000));

        needForSpeedApplication.novoPiloto(2l, "Jasmin", LocalDate.of(1994, 12 ,05),
                LocalDate.of(2017, 03 ,20), BigDecimal.valueOf(20000));

        needForSpeedApplication.novoPiloto(3l, "Chineque", LocalDate.of(1994, 12 ,05),
                LocalDate.of(2017, 8 ,27), BigDecimal.valueOf(5000));


        return needForSpeedApplication;
    }

    @Test
    public void givenPilotoWhenCriarPilotoThenIdentificadorUtilizado(){
        NeedForSpeedApplication needForSpeedApplication = settingTest();

        try {
            needForSpeedApplication.novoPiloto(1l, "Goiaba", LocalDate.of(1992, 10 ,31),
                    LocalDate.of(2017, 03 ,27), BigDecimal.valueOf(15000));
            Assert.fail("Expected IdentificadorUtilizadoException");
        }catch (Exception e){
            Assert.assertTrue("Expected IdentificadorUtilizadoException found " + e.toString(),
                    e instanceof IdentificadorUtilizadoException);
        }
    }

    @Test
    public void givenPilotoWhenCriarPilotoThenJogadorAdicionado(){
        NeedForSpeedApplication needForSpeedApplication = settingTest();

        try {
            needForSpeedApplication.novoPiloto(4l, "Carambola", LocalDate.of(1992, 10 ,31),
                    LocalDate.of(2017, 03 ,27), BigDecimal.valueOf(15000));
            Assert.fail("Expected IdentificadorUtilizadoException");
        }catch (Exception e){
            Assert.assertFalse(e instanceof IdentificadorUtilizadoException);
        }

        Assert.assertEquals("Expected Piloto Carambola",
                needForSpeedApplication.buscarNomePiloto(4l), "Carambola");

    }

    @Test
    public void givenPilotoWhenComprarCarroThenSaldoInsuficiente(){
        NeedForSpeedApplication needForSpeedApplication = settingTest();

        try {
            needForSpeedApplication.comprarCarro(1l, 1l, "vermelho",
                    "audi", 2018, 50, BigDecimal.valueOf(50000));
            Assert.fail("Expected SaldoInsuficienteException");
        }catch (Exception e){
            Assert.assertTrue("Expected SaldoInsuficienteException found " + e.toString(),
                    e instanceof SaldoInsuficienteException);
        }

    }


    @Test
    public void givenPilotoWhenComprarCarroThenPilotoNaoEncontrado(){
        NeedForSpeedApplication needForSpeedApplication = settingTest();

        try {
            needForSpeedApplication.comprarCarro(1l, 10l, "vermelho",
                    "audi", 2018, 50, BigDecimal.valueOf(50000));
            Assert.fail("Expected IdentificadorUtilizadoException");
        }catch (Exception e){
            Assert.assertTrue("Expected PilotoNãoEncontradoException" + e.toString(),
                    e instanceof PilotoNaoEncontradoException);
        }

    }


    @Test
    public void givenPilotoWhenComprarCarroThenIdentificadorUtilizado(){
        NeedForSpeedApplication needForSpeedApplication = settingTest();

        try {
            needForSpeedApplication.comprarCarro(1l, 1l, "vermelho",
                    "audi", 2018, 50, BigDecimal.valueOf(1000));

            needForSpeedApplication.comprarCarro(1l, 1l, "vermelho",
                    "audi", 2018, 50, BigDecimal.valueOf(1000));
            Assert.fail("Expected IdentificadorUtilizadoException");
        }catch (Exception e){
            Assert.assertTrue("Expected IdentificadorUtilizadoException" + e.toString(),
                    e instanceof IdentificadorUtilizadoException);
        }

    }

    @Test
    public void givenPilotoWhenComprarCarroThenCarroComprado(){
        NeedForSpeedApplication needForSpeedApplication = settingTest();

        try {
            needForSpeedApplication.comprarCarro(1l, 1l, "vermelho",
                    "audi", 2018, 50, BigDecimal.valueOf(1000));

        }catch (Exception e){
            Assert.assertTrue(e instanceof IdentificadorUtilizadoException);
        }
        Assert.assertTrue("Expected Car",
                needForSpeedApplication.getCarros().stream().anyMatch(j -> j.getId().equals(1l)));
    }

}
