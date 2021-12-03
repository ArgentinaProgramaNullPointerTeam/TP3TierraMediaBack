package dao;

import java.util.HashMap;

import model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario> {
	public abstract HashMap<Integer, Usuario> findAll();
	public abstract int changeFields(Usuario usuario);
	public abstract Usuario findByUsername(String username);
	public abstract Usuario find(Integer id);
}
