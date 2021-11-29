package model;

import java.util.List;

public abstract class Promocion extends Producto {
	private String nombre;
	private int cantAtracciones;
	private int id;
	private List<Atraccion> atracciones;
	private String tipoDePromocion;
	private double tiempoDeVisita;

	public Promocion(int id, String nombre, int cantAtracciones, List<Atraccion> atracciones, String tipoPromocion) {
		this.nombre = nombre;
		this.id = id;
		this.tipoDePromocion = tipoPromocion;
		this.cantAtracciones = cantAtracciones;
		this.atracciones = atracciones;
	}

	public List<Atraccion> getAtracciones() {
		return atracciones;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getCupo() {
		int cupo = this.atracciones.get(0).getCupo();
		for (Atraccion cadaAtraccion : this.atracciones) {
			if (cadaAtraccion.getCupo() < cupo) {
				cupo = cadaAtraccion.getCupo();
			}
		}
		return cupo;
	}

	@Override
	public boolean esOContiene(Producto otro) {
		for (Atraccion a : this.atracciones) {
			if (otro.esOContiene(a))
				return true;
		}
		return false;
	}

	@Override
	public void restarCupo() {
		for (Atraccion cadaAtraccion : atracciones) {
			cadaAtraccion.restarCupo();
		}
	}

	@Override
	public boolean hayCupo() {
		boolean cupo = true;
		for (Atraccion cadaAtraccion : atracciones) {
			if (!cadaAtraccion.hayCupo()) {
				cupo = false;
				break;
			}
		}
		return cupo;
	}

	@Override
	public String toString() {
		return this.nombre + " con  " + this.cantAtracciones + " atracciones " + this.atracciones;
	}

	@Override
	public double getTiempoDeVisita() {
		tiempoDeVisita = 0;
		for (Atraccion cadaAtraccion : atracciones) {
			tiempoDeVisita += cadaAtraccion.getTiempoDeVisita();
		}
		return tiempoDeVisita;
	}

	@Override
	public String getTipoAtracciones() {
		return this.atracciones.get(0).getTipoAtracciones();
	}

	@Override
	public boolean esPromocion() {
		return true;
	}

	@Override
	public String ofertas() {
		String ofertaAtracciones = "";
		for (Atraccion cadaAtraccion : this.atracciones) {
			ofertaAtracciones = ofertaAtracciones + " " + cadaAtraccion.getNombre() + ". ";
		}
		return "Promoción disponible para adquirir: " + this.nombre + "\nAtracciones que contiene:" + ofertaAtracciones
				+ "\n" + "Costo total: " + this.getCostoDeVisita() + " monedas" + "\n" + "Tiempo total: "
				+ this.getTiempoDeVisita() + " horas";

	}

	public String getTipoDePromocion() {
		return tipoDePromocion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atracciones == null) ? 0 : atracciones.hashCode());
		result = prime * result + cantAtracciones;
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		long temp;
		temp = Double.doubleToLongBits(tiempoDeVisita);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((tipoDePromocion == null) ? 0 : tipoDePromocion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Promocion other = (Promocion) obj;
		if (atracciones == null) {
			if (other.atracciones != null)
				return false;
		} else if (!atracciones.equals(other.atracciones))
			return false;
		if (cantAtracciones != other.cantAtracciones)
			return false;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (Double.doubleToLongBits(tiempoDeVisita) != Double.doubleToLongBits(other.tiempoDeVisita))
			return false;
		if (tipoDePromocion == null) {
			if (other.tipoDePromocion != null)
				return false;
		} else if (!tipoDePromocion.equals(other.tipoDePromocion))
			return false;
		return true;
	}
	
}