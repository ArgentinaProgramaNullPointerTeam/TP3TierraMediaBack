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

public class PromocionDAOImpl implements PromocionDAO {

	public HashMap<Integer, Promocion> findAll(HashMap<Integer, Atraccion> atracciones) {
		try {
			String sql = "SELECT p.id_promocion, p.nombre, p.tipo_promocion, p.descuento_AXB, p.descuento_porcentual, p.descuento_absoluta, tipo.nombre AS 'tipo_atraccion', p.atraccion1, p.atraccion2, p.atraccion3, p.status "
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

	public int update(Promocion promocion) {
		int rows = 0;

		return rows;
	}

	public int delete(Promocion promocion) {
		int rows = 0;
		try {
			String sql = "UPDATE promocion SET status = '0' WHERE id_promocion = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, promocion.getId());
			rows = statement.executeUpdate();
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return rows;
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
				throw new Error("La atracci�n no est� en el listado de atracciones");
			}

			model.Promocion promocion = null;
			if (resultados.getString(3).equals("Porcentual")) {
				promocion = new model.PromocionPorcentual(resultados.getInt(1), resultados.getString(2),
						cantAtracciones, atraccionesEnPromo, resultados.getString(3), resultados.getDouble(5), resultados.getInt(11));
			} else if (resultados.getString(3).equals("Absoluta")) {
				promocion = new model.PromocionAbsoluta(resultados.getInt(1), resultados.getString(2), cantAtracciones,
						atraccionesEnPromo, resultados.getString(3), resultados.getInt(6), resultados.getInt(11));
			} else if (resultados.getString(3).equals("AXB")) {
				promocion = new model.PromocionAXB(resultados.getInt(1), resultados.getString(2), cantAtracciones,
						atraccionesEnPromo, resultados.getString(3), resultados.getInt(4), resultados.getInt(11));
			}
			return promocion;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

}
