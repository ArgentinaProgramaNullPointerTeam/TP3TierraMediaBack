package model;

import java.util.Comparator;

public class ComparadorDeProductos implements Comparator<Producto> {
	// Atributo para guardar la atraccion preferida del usuario
	private int atraccionPreferida;

	public ComparadorDeProductos(int atraccionPreferida) {
		// Constructor que recibe por parametro la atracción preferida del usuario
		this.atraccionPreferida = atraccionPreferida;
	}

	public int compare(Producto p1, Producto p2) {
		/*
		 * Metodo que ordena segun prioridades: 1 Coincida con la atraccion preferida
		 * del usuario 2 Promocion por sobre atraccion 3 Mayor costo 4 Mayor tiempo
		 */
		if (this.atraccionPreferida == p1.getTipoAtracciones() && this.atraccionPreferida != p2.getTipoAtracciones())
			return -1;
		else if (this.atraccionPreferida != p1.getTipoAtracciones()
				&& this.atraccionPreferida == p2.getTipoAtracciones())
			return 1;
		else {
			if (p1.esPromocion() == true && p2.esPromocion() == false)
				return -1;
			else if (p1.esPromocion() == false && p2.esPromocion() == true)
				return 1;
			else {
				if (p1.getCostoDeVisita() > p2.getCostoDeVisita())
					return -1;
				else if (p1.getCostoDeVisita() < p2.getCostoDeVisita())
					return 1;
				else {
					return -1 * Double.compare(p1.getTiempoDeVisita(), p2.getTiempoDeVisita());
				}
			}

		}
	}

}