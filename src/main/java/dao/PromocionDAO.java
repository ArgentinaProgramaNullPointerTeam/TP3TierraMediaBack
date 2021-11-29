package dao;

import java.util.HashMap;
import model.Atraccion;
import model.Promocion;

public interface PromocionDAO extends GenericDAO<Promocion> {

	public HashMap<Integer, Promocion> findAll(HashMap<Integer, Atraccion> atracciones);

}
