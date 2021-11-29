package model;

import java.util.List;

public class PromocionAbsoluta extends Promocion {
	private int descuento;

	public PromocionAbsoluta(int id, String nombre, int cantAtracciones, List<Atraccion> atracciones,
			String tipoPromocion, int descuento) {
		super(id, nombre, cantAtracciones, atracciones, tipoPromocion);
		this.descuento = descuento;
	}

	@Override
	public int getCostoDeVisita() {
		return this.descuento;
	}
}