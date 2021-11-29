package modelTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.Producto;

public class AtraccionTests {

	Atraccion atraccion;

	@Before
	public void setUp() {
		atraccion = new Atraccion(1, "Moria", 10, 2, 6, "Aventura");

	}

	@After
	public void tearDown() {
		atraccion = null;
	}

	@Test
	public void creacionAtraccionTest() {
		assertNotNull(atraccion);
	}

	@Test
	public void restarCupoTest() {
		atraccion.restarCupo();

		int cupoObtenido = 5;
		int cupoEsperado = atraccion.getCupo();

		assertEquals(cupoObtenido, cupoEsperado);

	}

	@Test
	public void hayCupoTest() {
		atraccion.restarCupo();
		atraccion.restarCupo();
		atraccion.restarCupo();
		atraccion.restarCupo();
		atraccion.restarCupo();
		assertTrue(atraccion.hayCupo());
	}

	@Test
	public void noHayCupoTest() {
		atraccion.restarCupo();
		atraccion.restarCupo();
		atraccion.restarCupo();
		atraccion.restarCupo();
		atraccion.restarCupo();
		atraccion.restarCupo();
		assertFalse(atraccion.hayCupo());

	}

	@Test
	public void esPromocionTest() {
		assertFalse(atraccion.esPromocion());
	}

	@Test
	public void noEstaIncluidaTest() {
		Producto atraccionIgual = new Atraccion(1, "Moria", 10, 2, 6, "Aventura");
		Producto atraccionNoIgual = new Atraccion(2, "Isengard", 10, 2.5, 23, "Aventura");

		assertTrue(atraccion.esOContiene(atraccionIgual));
		assertFalse(atraccion.esOContiene(atraccionNoIgual));
	}
}
