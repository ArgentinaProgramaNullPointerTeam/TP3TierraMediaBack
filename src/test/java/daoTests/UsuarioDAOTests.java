package daoTests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.DAOFactory;
import dao.UsuarioDAO;
import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Itinerario;
import model.Producto;
import model.Usuario;

public class UsuarioDAOTests {

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
	public void cargarUsuariosTest() {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, "Aventura");
		ArrayList<Producto> compra = new ArrayList<Producto>();
		compra.add(atraccion1);

		Usuario usuarioEsperado = new Usuario(1, "Eowyn", "Aventura", 10, 8);
		Itinerario itinerarioEsperado = new Itinerario(1, usuarioEsperado.getId(), compra);
		usuarioEsperado.setItinerario(itinerarioEsperado);
		HashMap<Integer, Usuario> usuariosEsperados = new HashMap<Integer, Usuario>();

		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.findAll();
		usuariosEsperados.put(usuarioEsperado.getId(), usuarioEsperado);

		assertEquals(usuariosEsperados, usuariosObtenidos);
	}

	@Test
	public void actualizarUsuarioTest() {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, "Aventura");
		ArrayList<Producto> compras = new ArrayList<Producto>();
		compras.add(atraccion1);

		Usuario usuarioEsperado = new Usuario(1, "Eowyn", "Aventura", 9, 7);

		Itinerario itinerario = new Itinerario(1, usuarioEsperado.getId(), compras);
		usuarioEsperado.setItinerario(itinerario);

		HashMap<Integer, Usuario> usuariosEsperados = new HashMap<Integer, Usuario>();
		usuariosEsperados.put(usuarioEsperado.getId(), usuarioEsperado);

		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.findAll();
		Usuario usuarioObtenido = usuariosObtenidos.get(1);
		usuarioObtenido.comprar(atraccion1);
		usuarioDAO.update(usuarioObtenido);
		usuariosObtenidos = usuarioDAO.findAll();

		assertEquals(usuariosEsperados, usuariosObtenidos);

	}

}
