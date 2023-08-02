package com.pruebas.unitarias.app;

import com.pruebas.unitarias.app.services.ServicioA;
import com.pruebas.unitarias.app.services.impl.ServicioAimpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PracticasConMockitoApplicationTests {

	@Test
	public void testSumar() {
		int a = 2;
		int b = 2;
		ServicioA servicioA = new ServicioAimpl();
		Assert.assertEquals(servicioA.sumar(a,b), 4);
	}

}
