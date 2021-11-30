package daoTests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.AtraccionDAO;
import dao.DAOFactory;
import dao.ItinerarioDAO;
import dao.PromocionDAO;
import dao.UsuarioDAO;
import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Itinerario;
import model.Producto;
import model.Promocion;
import model.Usuario;

public class ItinerarioDAOTests {

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
	public void cargarItinerariosTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
		ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();

		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, "Aventura", 1);
		ArrayList<Producto> sugerenciasEsperadas = new ArrayList<Producto>();
		sugerenciasEsperadas.add(atraccion1);
		Itinerario itinerario = new Itinerario(1, 1, sugerenciasEsperadas);

		HashMap<Integer, Itinerario> itinerariosEsperados = new HashMap<Integer, Itinerario>();
		itinerariosEsperados.put(itinerario.getIdUsuario(), itinerario);

		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.findAll();
		HashMap<Integer, Promocion> promocionesObtenidas = promocionDAO.findAll(atraccionesObtenidas);
		HashMap<Integer, Itinerario> itinerariosObtenidos = itinerarioDAO.findById(1, atraccionesObtenidas,
				promocionesObtenidas);

		assertEquals(itinerariosEsperados, itinerariosObtenidos);
	}

	@Test
	public void insertarItinerarioTest() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
		ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, "Aventura", 1);

		ArrayList<Producto> sugerenciasEsperadas = new ArrayList<Producto>();
		sugerenciasEsperadas.add(atraccion1);

		Itinerario itinerarioEsperado = new Itinerario(1, 1, sugerenciasEsperadas);

		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.findAll();
		HashMap<Integer, Atraccion> atraccionesObtenidas = atraccionDAO.findAll();
		HashMap<Integer, Promocion> promocionesObtenidas = promocionDAO.findAll(atraccionesObtenidas);
		HashMap<Integer, Itinerario> itinerariosObtenidos = itinerarioDAO.findById(1, atraccionesObtenidas,
				promocionesObtenidas);
		Itinerario itinerarioObtenido = itinerariosObtenidos.get(1);

		Usuario usuarioObtenido = usuariosObtenidos.get(1);
		usuarioObtenido.setItinerario(itinerarioObtenido);
		usuarioObtenido.comprar(atraccion1);

		usuarioDAO.update(usuarioObtenido);
		itinerarioDAO.update(itinerarioObtenido);
		itinerariosObtenidos = itinerarioDAO.findById(1, atraccionesObtenidas, promocionesObtenidas);
		itinerarioObtenido = itinerariosObtenidos.get(1);

		assertEquals(itinerarioEsperado, itinerarioObtenido);
	}

}
