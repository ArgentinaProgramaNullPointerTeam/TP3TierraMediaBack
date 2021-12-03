package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map.Entry;

import jdbc.ConnectionProvider;
import model.Administrador;
import model.Itinerario;
import model.Usuario;
import nullObject.NullUser;

public class UsuarioDAOImpl implements UsuarioDAO {

	public HashMap<Integer, Usuario> findAll() {
		try {
			String sql = "SELECT u.id_usuario, u.nombre, u.password, u.dinero_disponible, u.tiempo_disponible, tipo.id_tipo_atraccion, tipo.nombre AS 'tipo_atraccion', u.is_admin, u.status "
					+ "FROM 'usuario' AS u INNER JOIN 'tipo_atraccion' AS tipo "
					+ "ON u.id_tipo_atraccion = tipo.id_tipo_atraccion WHERE u.status = '1' AND tipo.status = '1'";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			Administrador admin = Administrador.getInstance();
			HashMap<Integer, Usuario> usuarios = new HashMap<Integer, Usuario>();
			while (resultados.next()) {
				usuarios.put(resultados.getInt("id_usuario"), toUsuario(resultados));
			}

			for (Entry<Integer, Usuario> cadaUsuario : usuarios.entrySet()) {
				Usuario usuario = usuarios.get(cadaUsuario.getKey());
				ItinerarioDAO itinerarioDAO = DAOFactory.getItinerarioDAO();
				HashMap<Integer, Itinerario> itinerarios = itinerarioDAO.findById(cadaUsuario.getKey(),
						admin.getAtracciones(), admin.getPromociones());
				for (Entry<Integer, Itinerario> cadaItinerario : itinerarios.entrySet()) {
					usuario.setItinerario(cadaItinerario.getValue());
				}
			}
			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public int changeFields(Usuario usuario) {
		try {
			String sql = "UPDATE usuario SET dinero_disponible = ?, tiempo_disponible = ? WHERE id_usuario = ? AND status = '1'";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, usuario.getId());
			statement.setDouble(2, usuario.getTiempoDisponible());
			statement.setInt(3, usuario.getId());

			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public int update(Usuario usuario) {
		try {
			String sql = "UPDATE usuario SET nombre = ?, password = ?, dinero_disponible = ?, tiempo_disponible = ?, id_tipo_atraccion = ?, is_admin = ? WHERE id_usuario = ? AND status = 1";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getPassword());
			statement.setInt(3, usuario.getDineroDisponible());
			statement.setDouble(4, usuario.getTiempoDisponible());
			statement.setInt(5, usuario.getAtraccionPreferida());
			statement.setInt(6, toInt(usuario.isAdmin()));
			statement.setInt(7, usuario.getId());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public int insert(Usuario usuario) {
		try {
			String sql = "INSERT INTO usuario (nombre, password, dinero_disponible, tiempo_disponible, id_tipo_atraccion, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, usuario.getNombre());
			statement.setString(2, usuario.getPassword());
			statement.setInt(3, usuario.getDineroDisponible());
			statement.setDouble(4, usuario.getTiempoDisponible());
			statement.setInt(5, usuario.getAtraccionPreferida());
			statement.setInt(6, toInt(usuario.isAdmin()));

			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
		
	}
	public int delete(Usuario usuario) {
		try {
			String sql = "UPDATE usuario SET status = '0' WHERE id_usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setInt(1, usuario.getId());
			int rows = statement.executeUpdate();
			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Usuario findByUsername(String nombre) {
		try {
			String sql = "SELECT u.id_usuario, u.nombre, u.password, u.dinero_disponible, u.tiempo_disponible, tipo.id_tipo_atraccion, tipo.nombre AS 'tipo_atraccion', u.is_admin, u.status"
					+ "FROM 'usuario' AS u INNER JOIN 'tipo_atraccion' AS tipo"
					+ "ON u.id_tipo_atraccion = tipo.id_tipo_atraccion WHERE u.status = '1' AND tipo.status = '1' AND u.nombre = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, nombre);
			ResultSet resultados = statement.executeQuery();

			Usuario user = NullUser.build();

			if (resultados.next()) {
				user = toUsuario(resultados);
			}

			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	public Usuario find(Integer id) {
		try {
			String sql = "SELECT u.id_usuario, u.nombre, u.password, u.dinero_disponible, u.tiempo_disponible, tipo.id_tipo_atraccion, tipo.nombre AS 'tipo_atraccion', u.is_admin, u.status "
					+ "FROM 'usuario' AS u INNER JOIN 'tipo_atraccion' AS tipo "
					+ "ON u.id_tipo_atraccion = tipo.id_tipo_atraccion WHERE u.status = '1' AND tipo.status = '1' AND u.id_usuario = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			
			ResultSet resultados = statement.executeQuery();

			Usuario usuario = NullUser.build();
			if (resultados.next()) {
				usuario = toUsuario(resultados);
			}

			return usuario;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}
	
	private Usuario toUsuario(ResultSet resultados) {
		try {
			return new Usuario(resultados.getInt(1), resultados.getString(2), resultados.getString(3), resultados.getInt(6),
					resultados.getInt(4), resultados.getDouble(5), resultados.getInt(8), resultados.getInt(9));
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private int toInt(Boolean noInt) {
		int entero = 0;
		if (noInt) entero = 1;
		return entero;
	}
}
