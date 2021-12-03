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
			String sql = "SELECT a.id_atraccion, a.nombre, a.costo_visita, a.cupo, a.tiempo_visita, tipo.id_tipo_atraccion, tipo.nombre as 'tipo_atraccion' , a.status "
					+ "FROM atraccion a INNER JOIN tipo_atraccion tipo "
					+ "ON a.id_tipo_atraccion = tipo.id_tipo_atraccion " + "WHERE a.status = '1' AND tipo.status = '1'";
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

	public int changeFields(Atraccion atraccion) {
		try {
			String sql = "UPDATE atraccion SET cupo = cupo -1 WHERE id_atraccion = ? AND status = '1'";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, atraccion.getId());
			int rows = statement.executeUpdate();
			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public int delete(Atraccion atraccion) {
		try {
			String sql = "UPDATE atraccion SET status = '0' WHERE id_atraccion = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, atraccion.getId());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(Atraccion atraccion) {
		try {
			String sql = "UPDATE atraccion SET nombre = ?, costo_visita = ?, cupo = ?, tiempo_visita = ?, id_tipo_atraccion = ? WHERE id_atraccion = ? AND status = '1'";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, atraccion.getNombre());
			statement.setInt(2, atraccion.getCostoDeVisita());
			statement.setInt(3, atraccion.getCupo());
			statement.setDouble(4, atraccion.getTiempoDeVisita());
			statement.setInt(5, atraccion.getTipoAtracciones());
			statement.setInt(6, atraccion.getId());
			
			int rows = statement.executeUpdate();
			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int insert(Atraccion atraccion) {
		try {
			String sql = "INSERT INTO atraccion(nombre, costo_visita, cupo, tiempo_visita, id_tipo_atraccion) VALUES (?, ?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, atraccion.getNombre());
			statement.setInt(2, atraccion.getCostoDeVisita());
			statement.setInt(3, atraccion.getCupo());
			statement.setDouble(4, atraccion.getTiempoDeVisita());
			statement.setInt(5, atraccion.getTipoAtracciones());
			int rows = statement.executeUpdate();
			return rows;

		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Atraccion find(Integer id) {
		try {
			String sql = "SELECT a.id_atraccion, a.nombre, a.costo_visita, a.cupo, a.tiempo_visita, tipo.id_tipo_atraccion, tipo.nombre as 'tipo_atraccion' , a.status "
					+ "FROM atraccion a INNER JOIN tipo_atraccion tipo "
					+ "ON a.id_tipo_atraccion = tipo.id_tipo_atraccion "
					+ "WHERE a.status = '1' AND tipo.status = '1' AND a.id_atraccion = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			
			ResultSet resultados = statement.executeQuery();
			Atraccion atraccion = null;
			if (resultados.next()) {
				atraccion = toAtraccion(resultados);
			}

			return atraccion;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	private Atraccion toAtraccion(ResultSet resultados) {
		try {
			return new Atraccion(resultados.getInt(1), resultados.getString(2), resultados.getInt(3),
					resultados.getDouble(5), resultados.getInt(4), resultados.getInt(6), resultados.getInt(8));
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
}
