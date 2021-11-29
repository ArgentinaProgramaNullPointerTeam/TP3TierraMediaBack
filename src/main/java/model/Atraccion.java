package model;

/**
 * Clase que modela la atraccion. Tiene un constructor con los @param nombre,
 * costoDeVisita, tiempoDeVisita, cupo y tipoAtraccion Tiene los getter para
 * nombre, costoDeVisita, tiempoDeVisita y tipoAtraccion. Tiene un metodo
 * hayCupo que indica si la Atraccion tiene cupo o no y un metodo restarCupo que
 * resta 1 cupo cuando se acepta una compra y hay cupo .
 */

public class Atraccion extends Producto {
	private int id;
	private String nombre;
	private int costoDeVisita;
	private double tiempoDeVisita;
	private int cupo;
	private String tipoAtraccion;

	public Atraccion(int id, String nombre, int costoDeVisita, double tiempoDeVisita, int cupo, String tipoAtraccion) {
		this.id = id;
		this.nombre = nombre;
		this.costoDeVisita = costoDeVisita;
		this.tiempoDeVisita = tiempoDeVisita;
		this.cupo = cupo;
		this.tipoAtraccion = tipoAtraccion;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public int getCostoDeVisita() {
		return costoDeVisita;
	}

	@Override
	public double getTiempoDeVisita() {
		return tiempoDeVisita;
	}

	@Override
	public String getTipoAtracciones() {
		return tipoAtraccion;
	}

	@Override
	public boolean hayCupo() {
		return this.cupo > 0;
	}

	@Override
	public void restarCupo() {
		if (this.cupo > 0) {
			this.cupo -= 1;
		}

	}

	@Override
	public boolean esPromocion() {
		return false;
	}

	public int getCupo() {
		return this.cupo;
	}

	@Override
	public String toString() {
		return "Atraccion [nombre=" + nombre + ", costoDeVisita=" + costoDeVisita + ", tiempoDeVisita=" + tiempoDeVisita
				+ ", cupo=" + cupo + ", tipoAtraccion=" + tipoAtraccion + "]";
	}

	@Override
	public String ofertas() {
		return "Atracción disponible para adquirir: " + this.nombre + "\n" + "Costo total: " + this.getCostoDeVisita()
				+ " monedas." + "\n" + "Tiempo total: " + this.getTiempoDeVisita() + " horas";
	}

	@Override
	public boolean esOContiene(Producto otro) {
		return this.equals(otro);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + costoDeVisita;
		result = prime * result + cupo;
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		long temp;
		temp = Double.doubleToLongBits(tiempoDeVisita);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((tipoAtraccion == null) ? 0 : tipoAtraccion.hashCode());
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
		Atraccion other = (Atraccion) obj;
		if (costoDeVisita != other.costoDeVisita)
			return false;
		if (cupo != other.cupo)
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
		if (tipoAtraccion == null) {
			if (other.tipoAtraccion != null)
				return false;
		} else if (!tipoAtraccion.equals(other.tipoAtraccion))
			return false;
		return true;
	}

}
