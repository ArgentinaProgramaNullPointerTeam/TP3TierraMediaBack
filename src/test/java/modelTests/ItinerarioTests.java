package modelTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Atraccion;
import model.Itinerario;
import model.Producto;
import model.Usuario;

public class ItinerarioTests {
	Itinerario itinerario;
	Usuario usuario;
	Producto sugerencia1, sugerencia2, sugerencia3;
	ArrayList<Producto> compras;

	@Before
	public void setup() {
		usuario = new Usuario(1, "Sam", "Aventura", 50, 3);
		sugerencia1 = new Atraccion(1, "Edoras", 5, 0.5, 2, "Aventura");
		sugerencia2 = new Atraccion(1, "Isengard", 5, 1, 2, "Aventura");
		sugerencia3 = new Atraccion(3, "Rivendel", 10, 1, 2, "Aventura");
		compras = new ArrayList<Producto>();
	}

	@After
	public void tearDown() {
		usuario = null;
		sugerencia1 = null;
		sugerencia2 = null;
		sugerencia3 = null;
		compras = null;
	}

	@Test
	public void tiempoItinerario() {
		// Verifica el gasto correcto de tiempo
		compras.add(sugerencia1);
		compras.add(sugerencia2);
		compras.add(sugerencia3);
		itinerario = new Itinerario(1, 1, compras);
		assertEquals(2.5, itinerario.getDuracionItinerario(), 0);
	}

	@Test
	public void monedasItinerario() {
		// Verifica el gasto correcto de dinero.
		compras.add(sugerencia1);
		compras.add(sugerencia2);
		compras.add(sugerencia3);
		itinerario = new Itinerario(1, 1, compras);
		assertEquals(20, itinerario.getCostoItinerario());
	}

}
