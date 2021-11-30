package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import jdbc.ConnectionProvider;
import model.Atraccion;

public class AtraccionDAOImpl implements AtraccionDAO {

	public HashMap<Integer, Atraccion> findAll() {
		try {
			String sql = "SELECT a.id_atraccion, a.nombre, a.costo_visita, a.cupo, a.tiempo_visita, tipo.nombre as 'tipo_atraccion' , a.status "
					+ "FROM atraccion a INNER JOIN tipo_atraccion tipo "
					+ "ON a.id_tipo_atraccion = tipo.id_tipo_atraccion "
					+ "WHERE a.status = '1' AND tipo.status = '1'";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			HashMap<Integer, Atraccion> atracciones = new HashMap<Integer, Atraccion>();
			while (resultados.next()) {
				atracciones.put(resultados.getInt("id_atraccion"), toAtraccion(resultados));
			}

			return atracciones;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(Atraccion atraccion) {
		int rows = 0;
		try {
			String sql = "UPDATE atraccion SET cupo = cupo -1 WHERE id_atraccion = ? AND status = '1'";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, atraccion.getId());
			rows = statement.executeUpdate();

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		return rows;
	}

	private Atraccion toAtraccion(ResultSet resultados) {
		try {
			return new Atraccion(resultados.getInt(1), resultados.getString(2), resultados.getInt(3),
					resultados.getDouble(5), resultados.getInt(4), resultados.getString(6), resultados.getInt(7));
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}
