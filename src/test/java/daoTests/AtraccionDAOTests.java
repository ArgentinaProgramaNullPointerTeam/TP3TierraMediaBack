package daoTests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.AtraccionDAO;
import dao.DAOFactory;
import jdbc.ConnectionProvider;
import model.Atraccion;

public class AtraccionDAOTests {
	Connection conexion;

	@Before
	public void setUp() throws Exception {
		conexion = ConnectionProvider.getConnection("src/test/resources/tierra_media_test.db");
		conexion.setAutoCommit(false);
	}

	@After
	public void tearDown() throws Exception {
		conexion.rollback();
		conexion.setAutoCommit(true);
	}

	@Test
	public void cargarAtraccionesTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, 1, 1);
		Atraccion atraccion2 = new Atraccion(2, "Minas Tirith", 2, 2.5, 25, 1, 1);
		Atraccion atraccion3 = new Atraccion(3, "La Comarca", 3, 1, 150, 1, 1);

		HashMap<Integer, Atraccion> atraccionesEsperadas = new HashMap<Integer, Atraccion>();
		atraccionesEsperadas.put(atraccion1.getId(), atraccion1);
		atraccionesEsperadas.put(atraccion2.getId(), atraccion2);
		atraccionesEsperadas.put(atraccion3.getId(), atraccion3);
		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.findAll();

		assertEquals(atraccionesEsperadas, atraccionesObtenidas);
	}

	@Test
	public void actualizarCupoTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		Atraccion atraccion = new Atraccion(1, "Moria", 1, 1, 6, 1, 1);
		Atraccion atraccionObtenida = atraccionDAO.findAll().get(1);
		atraccion.restarCupo();
		int cupoInicial = atraccion.getCupo();
		atraccionDAO.changeFields(atraccion);
		int cupoModificado = atraccionObtenida.getCupo();

		assertNotEquals(cupoInicial, cupoModificado);
	}
	
	@Test
	public void insertTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, 1, 1);
		Atraccion atraccion2 = new Atraccion(2, "Minas Tirith", 2, 2.5, 25, 1, 1);
		Atraccion atraccion3 = new Atraccion(3, "La Comarca", 3, 1, 150, 1, 1);
		Atraccion atraccion4 = new Atraccion(4, "Mordor", 3, 1, 150, 1, 1);
		
		HashMap<Integer, Atraccion> atraccionesEsperadas = new HashMap<Integer, Atraccion>();
		atraccionesEsperadas.put(atraccion1.getId(), atraccion1);
		atraccionesEsperadas.put(atraccion2.getId(), atraccion2);
		atraccionesEsperadas.put(atraccion3.getId(), atraccion3);
		atraccionesEsperadas.put(atraccion4.getId(), atraccion4);
		
		atraccionDAO.insert(atraccion4);
		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.findAll();

		assertEquals(atraccionesEsperadas, atraccionesObtenidas);
	}
	
	@Test
	public void updateTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 2, 6, 1, 1);
		Atraccion atraccion2 = new Atraccion(2, "Minas Tirith", 2, 2.5, 25, 1, 1);
		Atraccion atraccion3 = new Atraccion(3, "La Comarca", 3, 1, 150, 1, 1);
		
		HashMap<Integer, Atraccion> atraccionesEsperadas = new HashMap<Integer, Atraccion>();
		atraccionesEsperadas.put(atraccion1.getId(), atraccion1);
		atraccionesEsperadas.put(atraccion2.getId(), atraccion2);
		atraccionesEsperadas.put(atraccion3.getId(), atraccion3);
		
		
		atraccionDAO.update(atraccion1);
		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.findAll();

		assertEquals(atraccionesEsperadas, atraccionesObtenidas);
	}
	
	@Test
	public void deleteTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, 1, 1);
		Atraccion atraccion2 = new Atraccion(2, "Minas Tirith", 2, 2.5, 25, 1, 1);
		Atraccion atraccion3 = new Atraccion(3, "La Comarca", 3, 1, 150, 1, 1);
		
		HashMap<Integer, Atraccion> atraccionesEsperadas = new HashMap<Integer, Atraccion>();
		atraccionesEsperadas.put(atraccion1.getId(), atraccion1);
		atraccionesEsperadas.put(atraccion2.getId(), atraccion2);		
		atraccionDAO.delete(atraccion3);
		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.findAll();

		assertEquals(atraccionesEsperadas, atraccionesObtenidas);
	}
	
	@Test
	public void findTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		Atraccion atraccionEsperada = new Atraccion(1, "Moria", 1, 1, 6, 1, 1);
		Atraccion atraccionObtenida = atraccionDAO.find(1);

		assertEquals(atraccionEsperada, atraccionObtenida);
	}
}
