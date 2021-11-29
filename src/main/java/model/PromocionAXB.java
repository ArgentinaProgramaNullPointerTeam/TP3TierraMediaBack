package model;

import java.util.List;

public class PromocionAXB extends Promocion {
	private List<Atraccion> ListaAtracciones;
	private int idAtraccionGratuita;
	private int costoDeVisita;

	public PromocionAXB(int id, String nombre, int cantAtracciones, List<Atraccion> atracciones, String tipoPromocion,
			int descuento) {
		super(id, nombre, cantAtracciones, atracciones, tipoPromocion);
		this.idAtraccionGratuita = descuento;
		this.ListaAtracciones = atracciones;
	}

	@Override
	public int getCostoDeVisita() {
		costoDeVisita = 0;
		Atraccion atraccionGratuita = this.ListaAtracciones.get(ListaAtracciones.size() - 1);
		for (Atraccion cadaAtraccion : this.ListaAtracciones) {
			costoDeVisita = costoDeVisita + cadaAtraccion.getCostoDeVisita();
		}
		costoDeVisita = costoDeVisita - atraccionGratuita.getCostoDeVisita();
		return costoDeVisita;
	}

	public int getidAtraccionGratuita() {
		return this.idAtraccionGratuita;
	}

}
