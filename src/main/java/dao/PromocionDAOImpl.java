package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import jdbc.ConnectionProvider;
import model.Atraccion;
import model.Promocion;
import model.PromocionAXB;
import model.PromocionAbsoluta;
import model.PromocionPorcentual;

public class PromocionDAOImpl implements PromocionDAO {

	public HashMap<Integer, Promocion> findAll(HashMap<Integer, Atraccion> atracciones) {
		try {
			String sql = "SELECT p.id_promocion, p.nombre, p.tipo_promocion, p.descuento_AXB, p.descuento_porcentual, p.descuento_absoluta, tipo.id_tipo_atraccion, tipo.nombre AS 'tipo_atraccion', p.atraccion1, p.atraccion2, p.atraccion3, p.status "
					+ "FROM promocion p INNER JOIN tipo_atraccion tipo "
					+ "ON p.id_tipo_atraccion = tipo.id_tipo_atraccion "
					+ "WHERE p.status = '1' AND tipo.status = '1'";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			HashMap<Integer, Promocion> promociones = new HashMap<Integer, Promocion>();
			while (resultados.next()) {
				promociones.put(resultados.getInt("id_promocion"), toPromocion(resultados, atracciones));
			}
			return promociones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public Promocion find(Integer id, HashMap<Integer, Atraccion> atracciones) {
		try {
			String sql = "SELECT p.id_promocion, p.nombre, p.tipo_promocion, p.descuento_AXB, p.descuento_porcentual, p.descuento_absoluta, tipo.id_tipo_atraccion, tipo.nombre AS 'tipo_atraccion', p.atraccion1, p.atraccion2, p.atraccion3, p.status "
					+ "FROM promocion p INNER JOIN tipo_atraccion tipo "
					+ "ON p.id_tipo_atraccion = tipo.id_tipo_atraccion "
					+ "WHERE p.status = '1' AND tipo.status = '1' AND p.id_promocion = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			
			ResultSet resultados = statement.executeQuery();
			Promocion promocion = null;
			while (resultados.next()) {
				promocion = toPromocion(resultados, atracciones);
			}
			return promocion;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public int insert(Promocion promocion) {
		try {
			String sql = "";
			if (promocion.getTipoDePromocion().equals("AXB")) {
				sql = "INSERT INTO promocion (nombre, tipo_promocion, descuento_AXB, id_tipo_atraccion, atraccion1, atraccion2, atraccion3) VALUES (?, ?, ?, ?, ?, ?, ?)";
			} else if (promocion.getTipoDePromocion().equals("Absoluta")) {
				sql = "INSERT INTO promocion (nombre, tipo_promocion, descuento_absoluta, id_tipo_atraccion, atraccion1, atraccion2) VALUES (?, ?, ?, ?, ?, ?)";
			} else if (promocion.getTipoDePromocion().equals("Porcentual")) {
				sql = "INSERT INTO promocion (nombre, tipo_promocion, descuento_porcentual, id_tipo_atraccion, atraccion1, atraccion2) VALUES (?, ?, ?, ?, ?, ?)";
			}
			
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, promocion.getNombre());
			statement.setString(2, promocion.getTipoDePromocion());
			if (promocion.getTipoDePromocion().equals("AXB")) {
				PromocionAXB nuevaPromo = (PromocionAXB) promocion;
				statement.setInt(3, nuevaPromo.getidAtraccionGratuita());
				statement.setInt(7, nuevaPromo.getidAtraccionGratuita());
			} else if (promocion.getTipoDePromocion().equals("Absoluta")) {
				PromocionAbsoluta nuevaPromo = (PromocionAbsoluta) promocion;
				statement.setInt(3, nuevaPromo.getDescuento());
			} else if (promocion.getTipoDePromocion().equals("Porcentual")) {
				PromocionPorcentual nuevaPromo = (PromocionPorcentual) promocion;
				statement.setDouble(3, nuevaPromo.getDescuento());
			}
			statement.setInt(4, promocion.getTipoAtracciones());
			statement.setInt(5, promocion.getAtracciones().get(0).getId());
			statement.setInt(6, promocion.getAtracciones().get(1).getId());
			
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}		
	}

	public int update(Promocion promocion) {
		try {
			String sql = "";
			if (promocion.getTipoDePromocion().equals("AXB")) {
				sql = "UPDATE promocion SET nombre = ?, tipo_promocion = ?, descuento_AXB = ?, id_tipo_atraccion = ?, atraccion1 = ?, atraccion2 = ?, atraccion3 = ? WHERE id_promocion = ? AND status = 1";
			} else if (promocion.getTipoDePromocion().equals("Absoluta")) {
				sql = "UPDATE promocion SET nombre = ?, tipo_promocion = ?, descuento_absoluta = ?, id_tipo_atraccion = ?, atraccion1 = ?, atraccion2 = ? WHERE id_promocion = ? AND status = 1";
			} else if (promocion.getTipoDePromocion().equals("Porcentual")) {
				sql = "UPDATE promocion SET nombre = ?, tipo_promocion = ?, descuento_porcentual = ?, id_tipo_atraccion = ?, atraccion1 = ?, atraccion2 = ? WHERE id_promocion = ? AND status = 1";
			}
			
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, promocion.getNombre());
			statement.setString(2, promocion.getTipoDePromocion());
			if (promocion.getTipoDePromocion().equals("AXB")) {
				PromocionAXB nuevaPromo = (PromocionAXB) promocion;
				statement.setInt(3, nuevaPromo.getidAtraccionGratuita());
				statement.setInt(7, nuevaPromo.getidAtraccionGratuita());
				statement.setInt(8, nuevaPromo.getId());
			} else if (promocion.getTipoDePromocion().equals("Absoluta")) {
				PromocionAbsoluta nuevaPromo = (PromocionAbsoluta) promocion;
				statement.setInt(3, nuevaPromo.getDescuento());
				statement.setInt(7, nuevaPromo.getId());
			} else if (promocion.getTipoDePromocion().equals("Porcentual")) {
				PromocionPorcentual nuevaPromo = (PromocionPorcentual) promocion;
				statement.setDouble(3, nuevaPromo.getDescuento());
				statement.setInt(7, nuevaPromo.getId());
			}
			statement.setInt(4, promocion.getTipoAtracciones());
			statement.setInt(5, promocion.getAtracciones().get(0).getId());
			statement.setInt(6, promocion.getAtracciones().get(1).getId());
			
			
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}		
	}

	public int delete(Promocion promocion) {
		try {
			String sql = "UPDATE promocion SET status = '0' WHERE id_promocion = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, promocion.getId());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	private Promocion toPromocion(ResultSet resultados, HashMap<Integer, Atraccion> atracciones) {
		try {
			List<Atraccion> atraccionesEnPromo = new LinkedList<Atraccion>();
			int[] idAtracciones = new int[] { resultados.getInt(8), resultados.getInt(9), resultados.getInt(10) };
			int cantAtracciones = 0;
			for (int i = 0; i < idAtracciones.length; i++)
				for (Entry<Integer, Atraccion> atraccion : atracciones.entrySet()) {
					if (atraccion.getValue().getId() == (idAtracciones[i])) {
						atraccionesEnPromo.add(atraccion.getValue());
						cantAtracciones++;
					}
				}
			if (atraccionesEnPromo.isEmpty()) {
				throw new Error("La atracción no está en el listado de atracciones");
			}

			model.Promocion promocion = null;
			if (resultados.getString(3).equals("Porcentual")) {
				promocion = new model.PromocionPorcentual(resultados.getInt(1), resultados.getString(2),
						resultados.getInt(7) ,cantAtracciones, atraccionesEnPromo, resultados.getString(3), resultados.getDouble(5), resultados.getInt(12));
			} else if (resultados.getString(3).equals("Absoluta")) {
				promocion = new model.PromocionAbsoluta(resultados.getInt(1), resultados.getString(2), resultados.getInt(7), cantAtracciones,
						atraccionesEnPromo, resultados.getString(3), resultados.getInt(6), resultados.getInt(12));
			} else if (resultados.getString(3).equals("AXB")) {
				promocion = new model.PromocionAXB(resultados.getInt(1), resultados.getString(2), resultados.getInt(7), cantAtracciones,
						atraccionesEnPromo, resultados.getString(3), resultados.getInt(4), resultados.getInt(12));
			}
			return promocion;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}
