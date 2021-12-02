package dao;

import java.util.HashMap;
import model.Atraccion;
import model.Itinerario;
import model.Promocion;

public interface ItinerarioDAO extends GenericDAO<Itinerario> {

	public abstract HashMap<Integer, Itinerario> findById(int id, HashMap<Integer, Atraccion> atracciones,
			HashMap<Integer, Promocion> promociones);
}
