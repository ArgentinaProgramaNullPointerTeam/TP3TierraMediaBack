package model;

/**
 * Clase que modela al usuario. Tiene un constructor con los parametros nombre,
 * atraccionPreferida, dineroDisponible, tiempoDisponible. Tiene los getter para
 * atraccionPreferida. Tiene un metodo comprar que recibe un producto y lo
 * guarda en el itinerario. Tiene un metodo restarTiempo y restarDinero que
 * resta el tiempo y el dinero respectivamente. Tiene un metodo puedeComprar que
 * retorna un boolean si tiene tiempo y dinero disponible.
 */
public class Usuario {
	private int id;
	private String nombre;
	private String password;
	private int atraccionPreferida;
	private int dineroDisponible;
	private double tiempoDisponible;
	private Itinerario itinerario;
	private boolean isAdmin;
	private boolean status = true;

	public Usuario(int id, String nombre, String password, int atraccionPreferida, int dineroDisponible, double tiempoDisponible, int isAdmin, int status) {
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.atraccionPreferida = atraccionPreferida;
		this.dineroDisponible = dineroDisponible;
		this.tiempoDisponible = tiempoDisponible;
		this.itinerario = new Itinerario(id);
		this.isAdmin = this.toBoolean(isAdmin);
		this.status = this.toBoolean(status);
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public int getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getAtraccionPreferida() {
		return atraccionPreferida;
	}

	public int getDineroDisponible() {
		return dineroDisponible;
	}

	public double getTiempoDisponible() {
		return tiempoDisponible;
	}

	public Itinerario getItinerario() {
		return this.itinerario;
	}
	

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void comprar(Producto producto) {
		this.restarDinero(producto.getCostoDeVisita());
		this.restarTiempo(producto.getTiempoDeVisita());
		this.itinerario.agregarAItinerario(producto);
	}

	public void restarDinero(int dinero) {
		this.dineroDisponible -= dinero;
	}

	public void restarTiempo(double tiempo) {
		this.tiempoDisponible -= tiempo;
	}

	public boolean puedeComprar(Producto producto) {
		// Verifica que el usuario tenga tiempo, dinero y no haya comprado el mismo
		// producto.
		return (this.dineroDisponible >= producto.getCostoDeVisita())
				&& (this.tiempoDisponible >= producto.getTiempoDeVisita()) && !itinerario.yaCompro(producto);
	}

	@Override
	public String toString() {
		return "Nombre de usuario= " + nombre + ", Atraccion preferida= " + atraccionPreferida + ", Dinero disponible= "
				+ dineroDisponible + ", Tiempo disponible= " + tiempoDisponible;
	}

	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + atraccionPreferida;
		result = prime * result + dineroDisponible;
		result = prime * result + id;
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + ((itinerario == null) ? 0 : itinerario.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(tiempoDisponible);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Usuario other = (Usuario) obj;
		if (atraccionPreferida != other.atraccionPreferida)
			return false;
		if (dineroDisponible != other.dineroDisponible)
			return false;
		if (id != other.id)
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (itinerario == null) {
			if (other.itinerario != null)
				return false;
		} else if (!itinerario.equals(other.itinerario))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (status != other.status)
			return false;
		if (Double.doubleToLongBits(tiempoDisponible) != Double.doubleToLongBits(other.tiempoDisponible))
			return false;
		return true;
	}

	private boolean toBoolean(int noBoolean) {
		return noBoolean == 1;
	}
}
