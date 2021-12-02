package modelTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.Producto;
import model.Promocion;
import model.PromocionAXB;

public class PromocionAXBTest {

	Atraccion atraccion1;
	Atraccion atraccion2;
	Atraccion atraccion3;

	List<Atraccion> pack = new ArrayList<Atraccion>();
	Promocion promo;

	@Before
	public void setup() {
		atraccion1 = new Atraccion(1, "Rivendel", 10, 2, 6, 1, 1);
		atraccion2 = new Atraccion(2, "Hobbiton", 40, 3.5, 8, 1, 1);
		atraccion3 = new Atraccion(3, "Edoras", 60, 0.5, 9, 1, 1);

		pack.add(atraccion1);
		pack.add(atraccion2);
		pack.add(atraccion3);

		promo = new PromocionAXB(1, "Pack 1", 1, 3, pack, "AXB", 3, 1);
	}

	@Test
	public void crearPromocionTest() {
		assertNotNull(promo);
	}

	@Test
	public void precioDePromocionTest() {
		int precioObtenido = promo.getCostoDeVisita();
		int precioEsperado = 50;

		assertEquals(precioEsperado, precioObtenido);
	}

	@Test
	public void cupoDePromocionTest() {
		// El cupo de la promocion es el menor cupo de la atraccion que lo compone
		int cupoObtenido = promo.getCupo();
		int cupoEsperado = 6;

		assertEquals(cupoEsperado, cupoObtenido);
	}

	@Test
	public void restarCupoTest() {
		// El cupo de la promocion es el menor cupo de la atraccion que lo compone
		promo.restarCupo();

		int cupoObtenido = promo.getCupo();
		int cupoEsperado = 5;

		assertEquals(cupoEsperado, cupoObtenido);
	}

	@Test
	public void duracionDePromocionTest() {
		double duracionObtenida = promo.getTiempoDeVisita();
		double duracionEsperada = 6;

		assertEquals(duracionEsperada, duracionObtenida, 0);
	}

	@Test
	public void noEstaIncluidaTest() {
		Producto atraccionIncluida = new Atraccion(1, "Rivendel", 10, 2, 6, 1, 1);
		Producto atraccionNoIncluida = new Atraccion(2, "Isengard", 10, 2.5, 23, 1, 1);

		assertTrue(promo.esOContiene(atraccionIncluida));
		assertFalse(promo.esOContiene(atraccionNoIncluida));
	}

	@Test
	public void hayCupoTest() {
		promo.restarCupo();
		promo.restarCupo();
		promo.restarCupo();
		promo.restarCupo();
		promo.restarCupo();
		assertTrue(promo.hayCupo());
	}

	@Test
	public void noHayCupoTest() {
		promo.restarCupo();
		promo.restarCupo();
		promo.restarCupo();
		promo.restarCupo();
		promo.restarCupo();
		promo.restarCupo();
		assertFalse(promo.hayCupo());
	}

	@Test
	public void esPromocionTest() {
		assertTrue(promo.esPromocion());
	}

}
