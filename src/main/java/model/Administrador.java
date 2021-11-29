package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.AtraccionDAO;
import dao.DAOFactory;
import dao.ItinerarioDAO;
import dao.PromocionDAO;
import dao.UsuarioDAO;

public class Administrador {
	private static Administrador admin;
	private HashMap<Integer, Usuario> usuarios = new HashMap<Integer, Usuario>();
	private HashMap<Integer, Atraccion> atracciones = new HashMap<Integer, Atraccion>();
	private HashMap<Integer, Promocion> promociones = new HashMap<Integer, Promocion>();
	private ArrayList<Producto> productos = new ArrayList<Producto>();

	public synchronized static Administrador getInstance() {
		if (admin == null) {
			admin = new Administrador();
			admin.leerAtracciones();
			admin.leerPromociones();
			admin.crearProductos();
			admin.leerUsuarios();
		}
		return admin;
	}

	private void leerUsuarios() {
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
		usuarios = usuarioDAO.findAll();
	}

	private void leerAtracciones() {
		AtraccionDAO atraccionDAO = DAOFactory.getAtraccionDAO();
		atracciones = atraccionDAO.findAll();
	}

	private void leerPromociones() {
		if (!atracciones.isEmpty()) {
			PromocionDAO promocionDAO = DAOFactory.getPromocionDAO();
			promociones = promocionDAO.findAll(this.atracciones);
		}
	}

	private void crearProductos() {
		if (!atracciones.isEmpty()) {
			productos.addAll(this.promociones.values());
			productos.addAll(this.atracciones.values());
		}
	}

	public HashMap<Integer, Usuario> getUsuarios() {
		return usuarios;
	}

	public HashMap<Integer, Atraccion> getAtracciones() {
		return atracciones;
	}

	public HashMap<Integer, Promocion> getPromociones() {
		return promociones;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public static void guardar(Producto producto, Usuario usuario) {
		AtraccionDAO atraccioDAO = DAOFactory.getAtraccionDAO();
		ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();
		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
		usuarioDAO.update(usuario);
		itinerarioDAO.insert(usuario.getItinerario());
		// el update de atraccion se hace de forma individual
		// por eso no se usa una lista sino que se recorre la lista antes y se envia la
		// atraccion a updatear
		if (producto.esPromocion()) {
			List<Atraccion> atracciones = new ArrayList<Atraccion>();
			Promocion miPromo = (Promocion) producto;
			atracciones.addAll(miPromo.getAtracciones());
			for (Atraccion atraccion : atracciones) {
				atraccioDAO.update(atraccion);
			}
		} else {
			atraccioDAO.update((Atraccion) producto);
		}
	}
}
