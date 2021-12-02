package model;

import java.util.List;

public class PromocionAbsoluta extends Promocion {
	private int descuento;

	public PromocionAbsoluta(int id, String nombre, int tipoAtraccion, int cantAtracciones, List<Atraccion> atracciones,
			String tipoPromocion, int descuento, int status) {
		super(id, nombre, tipoAtraccion, cantAtracciones, atracciones, tipoPromocion, status);
		this.descuento = descuento;
	}

	@Override
	public int getCostoDeVisita() {
		return this.descuento;
	}

	public int getDescuento() {
		return descuento;
	}
	
}