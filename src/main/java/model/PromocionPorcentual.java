package model;

import java.util.List;

public class PromocionPorcentual extends Promocion {

	private List<Atraccion> ListaAtracciones;
	private double descuento;
	private int costoDeVisita;

	public PromocionPorcentual(int id, String nombre, int cantAtracciones, List<Atraccion> atracciones,
			String tipoPromocion, Double descuento) {
		super(id, nombre, cantAtracciones, atracciones, tipoPromocion);
		this.ListaAtracciones = atracciones;
		this.descuento = descuento;
	}

	@Override
	public int getCostoDeVisita() {
		costoDeVisita = 0;
		for (Atraccion atraccion : this.ListaAtracciones) {
			costoDeVisita = costoDeVisita + atraccion.getCostoDeVisita();
		}
		costoDeVisita = (int) (costoDeVisita - (costoDeVisita * descuento));
		return costoDeVisita;
	}

}