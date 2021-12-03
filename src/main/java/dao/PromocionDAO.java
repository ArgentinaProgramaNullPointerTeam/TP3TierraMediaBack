package dao;

import java.util.HashMap;
import model.Atraccion;
import model.Promocion;

public interface PromocionDAO extends GenericDAO<Promocion> {

	public abstract HashMap<Integer, Promocion> findAll(HashMap<Integer, Atraccion> atracciones);
	public abstract Promocion find(Integer id, HashMap<Integer, Atraccion> atracciones);

}
