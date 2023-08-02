package com.pruebas.unitarias.app;

import com.pruebas.unitarias.app.services.ServicioA;
import com.pruebas.unitarias.app.services.ServicioB;
import com.pruebas.unitarias.app.services.impl.ServicioAimpl;
import com.pruebas.unitarias.app.services.impl.ServicioBimpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class TestServicioB {

    @Test
    public void testMultiplicar(){
        ServicioB servicioB = new ServicioBimpl();
        Assert.assertEquals(servicioB.multiplicar(2,3),6);
    }

    @Test
    public void testMultiplicarSumar(){
        ServicioA servicioA = new ServicioAimpl();
        ServicioB servicioB = new ServicioBimpl();

        servicioB.setServicioA(servicioA);
        Assert.assertEquals(servicioB.multiplicarSumar(2,3,2), 10);
    }

    @Test
    public void testMultiplicarSumarMockito(){
        ServicioA servicioA = Mockito.mock(ServicioA.class);
        Mockito.when(servicioA.sumar(2,3)).thenReturn(5);

        ServicioB servicioB = new ServicioBimpl();
        servicioB.setServicioA(servicioA);
        Assert.assertEquals(servicioB.multiplicarSumar(2,3,2), 10);
    }

}
