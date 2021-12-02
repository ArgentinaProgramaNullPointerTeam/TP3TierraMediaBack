package modelTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.ComparadorDeProductos;
import model.Promocion;
import model.PromocionAXB;
import model.PromocionAbsoluta;
import model.PromocionPorcentual;
import model.Usuario;

public class ComparadorDeProductosTest {
	Usuario u1;
	Atraccion a1;
	Atraccion a2;
	Atraccion a3;
	Atraccion a4;
	Promocion p1;
	Promocion p2;
	Promocion p3;

	@Before
	public void setup() {
		u1 = new Usuario(1, "pepito", "pass", 1, 100, 100, 0, 1);
		a1 = new Atraccion(1, "a1", 10, 3, 20, 1, 1);
		a2 = new Atraccion(2, "a2", 4, 2.5, 20, 1, 1);
		a3 = new Atraccion(3, "a3", 10, 5, 20, 1, 1);
		a4 = new Atraccion(4, "a4", 8, 4, 20, 1, 1);

		List<Atraccion> packUno = new ArrayList<Atraccion>();
		packUno.add(a1);
		packUno.add(a2);
		p1 = new PromocionAbsoluta(1, "Pack uno", 1, 2, packUno, "Absoluta", 10, 1);

		List<Atraccion> packDos = new ArrayList<Atraccion>();
		packDos.add(a1);
		packDos.add(a3);
		p2 = new PromocionPorcentual(2, "Pack dos", 1, 2, packDos, "Porcentual", 0.3, 1);

		List<Atraccion> packTres = new ArrayList<Atraccion>();
		packTres.add(a1);
		packTres.add(a3);
		packTres.add(a2);
		p3 = new PromocionAXB(3, "Pack tres", 1, 3, packTres, "AXB", 3, 1);

	}

	@Test
	public void testCriterio1() {
		ComparadorDeProductos compDeProd = new ComparadorDeProductos(u1.getAtraccionPreferida());
		int result = compDeProd.compare(a1, a4);
		assertTrue("a1 coincide con atraccion preferida antes que a4 que no coincide", result <= -1);
	}

	@Test
	public void testCriterio2() {
		ComparadorDeProductos compDeProd2 = new ComparadorDeProductos(u1.getAtraccionPreferida());
		int result2 = compDeProd2.compare(a1, p1);
		assertTrue("packUno que es promocion antes que a1 que es atraccion", result2 >= 1);
	}

	@Test
	public void testCriterio3() {
		ComparadorDeProductos compDeProd3 = new ComparadorDeProductos(u1.getAtraccionPreferida());
		int result3 = compDeProd3.compare(a2, a1);
		assertTrue("a1 mas caro que a2", result3 >= 1);

	}

	@Test
	public void testCriterio4() {
		ComparadorDeProductos compDeProd4 = new ComparadorDeProductos(u1.getAtraccionPreferida());
		int result4 = compDeProd4.compare(a1, a3);
		assertTrue("a3 mas tiempo que a1", result4 >= 1);

	}

}
