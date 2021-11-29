package dao;

import java.util.HashMap;

import model.Usuario;

public interface UsuarioDAO extends GenericDAO<Usuario> {
	public HashMap<Integer, Usuario> findAll();
}
