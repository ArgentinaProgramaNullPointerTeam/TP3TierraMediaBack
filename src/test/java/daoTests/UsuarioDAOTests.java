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

		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, 1, 1);
		ArrayList<Producto> compra = new ArrayList<Producto>();
		compra.add(atraccion1);

		Usuario usuarioEsperado = new Usuario(1, "Eowyn", "pass", 1, 10, 8, 0, 1);
		Itinerario itinerarioEsperado = new Itinerario(1, usuarioEsperado.getId(), compra);
		usuarioEsperado.setItinerario(itinerarioEsperado);
		HashMap<Integer, Usuario> usuariosEsperados = new HashMap<Integer, Usuario>();
		usuariosEsperados.put(usuarioEsperado.getId(), usuarioEsperado);
		
		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.findAll();
		
		assertEquals(usuariosEsperados, usuariosObtenidos);
	}

	@Test
	public void actualizarCostoDuracionUsuarioTest() {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
		
		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, 1, 1);
		Atraccion atraccion2 = new Atraccion(2, "Minas Tirith", 2, 2.5, 25, 1, 1);
		ArrayList<Producto> compras = new ArrayList<Producto>();
		compras.add(atraccion1);
		compras.add(atraccion2);

		Usuario usuarioEsperado = new Usuario(1, "Eowyn", "pass", 1, 8, 5.5, 0, 1);

		Itinerario itinerario = new Itinerario(1, usuarioEsperado.getId(), compras);
		usuarioEsperado.setItinerario(itinerario);
		itinerario.setCostoItinerario(2);
		itinerario.setDuracionItinerario(2.5);

		HashMap<Integer, Usuario> usuariosEsperados = new HashMap<Integer, Usuario>();
		usuariosEsperados.put(usuarioEsperado.getId(), usuarioEsperado);

		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.findAll();
		Usuario usuarioObtenido = usuariosObtenidos.get(1);

		usuarioObtenido.comprar(atraccion2);
		usuarioDAO.changeFields(usuarioObtenido);
		
		assertEquals(usuariosEsperados, usuariosObtenidos);
	}
	
	@Test
	public void insertarUsuarioTest() {
		
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, 1, 1);
		ArrayList<Producto> compra = new ArrayList<Producto>();
		compra.add(atraccion1);

		Usuario usuarioEsperado1 = new Usuario(1, "Eowyn", "pass", 1, 10, 8, 0, 1);
		Usuario usuarioEsperado2 = new Usuario(2, "Gandalf", "pass", 1, 8, 5.5, 0, 1);
		Itinerario itinerarioEsperado = new Itinerario(1, usuarioEsperado1.getId(), compra);
		usuarioEsperado1.setItinerario(itinerarioEsperado);
		
		HashMap<Integer, Usuario> usuariosEsperados = new HashMap<Integer, Usuario>();
		usuariosEsperados.put(usuarioEsperado1.getId(), usuarioEsperado1);
		usuariosEsperados.put(usuarioEsperado2.getId(), usuarioEsperado2);
		
		usuarioDAO.insert(usuarioEsperado2);
		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.findAll();
		assertEquals(usuariosEsperados, usuariosObtenidos);
	}
	
	@Test
	public void updateUsuarioTest() {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

		Atraccion atraccion1 = new Atraccion(1, "Moria", 1, 1, 6, 1, 1);
		ArrayList<Producto> compra = new ArrayList<Producto>();
		compra.add(atraccion1);

		Usuario usuarioEsperado1 = new Usuario(1, "Eowyn", "pass", 1, 8, 8, 0, 1);
		
		Itinerario itinerarioEsperado = new Itinerario(1, usuarioEsperado1.getId(), compra);
		usuarioEsperado1.setItinerario(itinerarioEsperado);
		
		HashMap<Integer, Usuario> usuariosEsperados = new HashMap<Integer, Usuario>();
		usuariosEsperados.put(usuarioEsperado1.getId(), usuarioEsperado1);
		
		usuarioDAO.update(usuarioEsperado1);
		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.findAll();
		assertEquals(usuariosEsperados, usuariosObtenidos);
	}
	
	@Test
	public void deleteUsuarioTest() {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();

		Usuario usuario = new Usuario(1, "Eowyn", "pass", 1, 10, 8, 0, 1);
		
		HashMap<Integer, Usuario> usuariosEsperados = new HashMap<Integer, Usuario>();
		
		usuarioDAO.delete(usuario);
		HashMap<Integer, Usuario> usuariosObtenidos = usuarioDAO.findAll();
		assertEquals(usuariosEsperados, usuariosObtenidos);
	}
	
	@Test
	public void findUsuarioTest() {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
		Usuario usuarioEsperado = new Usuario(1, "Eowyn", "pass", 1, 10, 8, 0, 1);
		Usuario usuarioObtenido = usuarioDAO.find(1);
		assertEquals(usuarioEsperado, usuarioObtenido);
	}
}
