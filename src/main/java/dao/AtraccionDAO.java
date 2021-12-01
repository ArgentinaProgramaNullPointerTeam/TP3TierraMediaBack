package dao;

import java.util.HashMap;

import model.Atraccion;

public interface AtraccionDAO extends GenericDAO<Atraccion> {
	public HashMap<Integer, Atraccion> findAll();
	public int changeFields(Atraccion atraccion);
}
