package modelTests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Atraccion;
import model.Itinerario;
import model.Producto;
import model.Promocion;
import model.PromocionAXB;
import model.PromocionAbsoluta;
import model.PromocionPorcentual;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

public class UsuarioTests {

	Usuario usuario, u1;
	Atraccion a1, a2, a3, a4;
	Promocion p1, p2, p3, p4;

	@Before
	public void setup() {
		usuario = new Usuario(1, "Sam", "Aventura", 50, 3);
		u1 = new Usuario(2, "pepito", "Aventura", 100, 100);

		a1 = new Atraccion(1, "a1", 10, 3, 20, "Aventura");
		a2 = new Atraccion(2, "a2", 4, 2.5, 20, "Aventura");
		a3 = new Atraccion(3, "a3", 2, 1, 20, "Aventura");
		a4 = new Atraccion(4, "a4", 8, 4, 20, "Aventura");

		List<Atraccion> packUno = new ArrayList<Atraccion>();
		packUno.add(a1);
		packUno.add(a2);
		p1 = new PromocionAbsoluta(1, "Pack uno", 2, packUno, "Absoluta", 10);

		List<Atraccion> packDos = new ArrayList<Atraccion>();
		packDos.add(a1);
		packDos.add(a3);
		p2 = new PromocionPorcentual(2, "Pack dos", 2, packDos, "Porcentual", 0.3);

		List<Atraccion> packTres = new ArrayList<Atraccion>();
		packTres.add(a1);
		packTres.add(a3);
		packTres.add(a2);
		p3 = new PromocionAXB(3, "Pack tres", 3, packTres, "AXB", 3);

		List<Atraccion> packCuatro = new ArrayList<Atraccion>();
		packCuatro.add(a2);
		packCuatro.add(a4);
		p4 = new PromocionAbsoluta(4, "Pack Cuatro", 2, packCuatro, "Absoluta", 5);
	}

	@After
	public void tearDown() {
		usuario = null;
		u1 = null;
		a1 = null;
		a2 = null;
		a3 = null;
		a4 = null;
		p1 = null;
		p2 = null;
		p3 = null;
		p4 = null;
	}

	@Test
	public void crearUsuarioTest() {
		assertNotNull(usuario);
	}

	@Test
	public void comprarTestDinero() {
		// Verifica que al comprar, descuente bien el dinero y el tiempo.
		Producto sugerenciaAceptada = new Atraccion(5, "Rivendel", 20, 1.5, 2, "Aventura");
		usuario.comprar(sugerenciaAceptada);
		int dineroDispObtenido = usuario.getDineroDisponible();
		int dineroDispEsperado = 30;

		assertEquals(dineroDispEsperado, dineroDispObtenido);
	}

	@Test
	public void comprarTestTiempo() {
		// Verifica que al comprar, descuente bien el dinero y el tiempo.
		Producto sugerenciaAceptada = new Atraccion(5, "Rivendel", 20, 1.5, 2, "Aventura");
		usuario.comprar(sugerenciaAceptada);
		double tiempoDispObtenido = usuario.getTiempoDisponible();
		double tiempoDispEsperado = 1.5;

		assertEquals(tiempoDispEsperado, tiempoDispObtenido, 0);
	}

	@Test
	public void puedeComprarTest() {
		// Verifica que el usuario pueda comprar una sugerencia.
		Producto sugerencia = new Atraccion(6, "Hobbiton", 10, 0.5, 2, "Aventura");
		assertTrue(usuario.puedeComprar(sugerencia));
	}

	@Test
	public void noPuedeComprarTest() { // El usuario no tiene suficiente dinero
		Producto sugerencia = new Atraccion(7, "Edoras", 60, 0.5, 2, "Aventura");
		assertFalse(usuario.puedeComprar(sugerencia));
	}

	@Test
	public void noPuedeComprarTest2() {// El usuario no tiene suficiente tiempo
		Producto sugerencia = new Atraccion(8, "Isengard", 5, 4, 2, "Aventura");
		assertFalse(usuario.puedeComprar(sugerencia));
	}

	@Test
	public void atraccionEnPromocion() {
		// Verifica que no pueda comprar atracciones o promociones repetidas.
		// Test nro1 - No puede comprar una atraccion que ya este en una promocion
		u1.comprar(p1);
		assertFalse(u1.puedeComprar(a2));
	}

	@Test
	public void atraccionesDelMismoTipo() {
		// Verifica que no pueda comprar atracciones o promociones repetidas.
		// Test nro1.5 (opcional si una promocion solo tiene atracciones del mismo tipo)
		u1.comprar(a1);
		assertFalse(u1.puedeComprar(p2));
	}

	@Test
	public void atraccionYaComprada() {
		// Verifica que no pueda comprar atracciones o promociones repetidas.
		// Test nro2 - No puede comprar una promocion que tenga una atraccion que ya
		// haya comprado
		u1.comprar(p1);
		assertFalse(u1.puedeComprar(p2));
	}

	@Test
	public void atraccionYaComprada2() {
		// Verifica que no pueda comprar atracciones o promociones repetidas.
		// Test nro3 - No puede comprar una promocion que tenga una atraccion que ya
		// haya comprado
		u1.comprar(p3);
		assertFalse(u1.puedeComprar(p4));
	}

	@Test
	public void setItinerarioTest() {
		ArrayList<Producto> listaCompras = new ArrayList<Producto>();
		listaCompras.add(a1);
		listaCompras.add(a2);
		Itinerario miItinerario = new Itinerario(1, 2, listaCompras);
		usuario.setItinerario(miItinerario);
		assertFalse(usuario.getItinerario().getListaCompra().isEmpty());
	}
}
