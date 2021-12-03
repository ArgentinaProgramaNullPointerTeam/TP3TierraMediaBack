package dao;

import java.util.HashMap;

import model.Atraccion;

public interface AtraccionDAO extends GenericDAO<Atraccion> {
	public abstract HashMap<Integer, Atraccion> findAll();
	public abstract int changeFields(Atraccion atraccion);
	public abstract Atraccion find(Integer id);
}
