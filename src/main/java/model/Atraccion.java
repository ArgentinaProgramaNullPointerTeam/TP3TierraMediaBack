package model;

import java.util.HashMap;
import java.util.Map;

public class Atraccion extends Producto {
	private int id;
	private String nombre;
	private int costoDeVisita;
	private double tiempoDeVisita;
	private int cupo;
	private int tipoAtraccion;
	private boolean status;
	private Map<String, String> errores;

	public Atraccion(int id, String nombre, int costoDeVisita, double tiempoDeVisita, int cupo, int tipoAtraccion, int status) {
		this.id = id;
		this.nombre = nombre;
		this.costoDeVisita = costoDeVisita;
		this.tiempoDeVisita = tiempoDeVisita;
		this.cupo = cupo;
		this.tipoAtraccion = tipoAtraccion;
		this.status = this.toBoolean(status);
	}
	
	public Atraccion(String nombre, int costoDeVisita, double tiempoDeVisita, int cupo, int tipoAtraccion) {
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
	public int getTipoAtracciones() {
		return tipoAtraccion;
	}
	
	@Override
	public boolean isStatus() {
		return status;
	}
	
	@Override
	public void setStatus(boolean status) {
		this.status = status;
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

	public boolean isValid() {
		validate();
		return errores.isEmpty();
	}
	
	public void validate() {
		errores = new HashMap<String, String>();
		boolean isNumeric =  this.nombre.matches("[+-]?\\d*(\\.\\d+)?");
		if (isNumeric) {
			errores.put("nombre", "No debe contener numeros");
		}
		if (this.nombre.contains(" ")) {
			errores.put("nombre", "No debe ingresar espacios");
		}
		if (this.nombre.equals("")) {
			errores.put("nombre", "Debe ingresar un nombre");
		}
		if (this.costoDeVisita <= 0) {
			errores.put("costoDeVisita", "El costo debe ser positivo");
		}
		if (this.tiempoDeVisita <= 0) {
			errores.put("tiempoDeVisita", "Debe ser positivo");
		}
		if (this.cupo <= 0) {
			errores.put("cupo", "Debe ser positivo");
		}
	}
	
	public Map<String, String> getErrors() {
		return errores;
	}
	
	private boolean toBoolean(int noBoolean) {
		return noBoolean == 1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + costoDeVisita;
		result = prime * result + cupo;
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(tiempoDeVisita);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + tipoAtraccion;
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
		if (status != other.status)
			return false;
		if (Double.doubleToLongBits(tiempoDeVisita) != Double.doubleToLongBits(other.tiempoDeVisita))
			return false;
		if (tipoAtraccion != other.tipoAtraccion)
			return false;
		return true;
	}
}
