package model;

import java.util.ArrayList;
import java.util.List;

/*
 * Tiene un metodo llamado
 * yaCompro que retorna un booleano y verifica que el producto comprado no haya
 * sido comprado anteriormente.
 * Tiene dos metodos que retorna las monedas gastadas y el tiempo gastado en las
 * compras que realizo el usuario respectivamente. 
 */
public class Itinerario {
	private List<Producto> listaCompra = new ArrayList<Producto>();
	private int costoItinerario = 0;
	private double duracionItinerario = 0;
	private int idUsuario;
	private int idItinerario;

	public Itinerario(int idItinerario, int idUsuario, List<Producto> listaCompra) {
		this.idItinerario = idItinerario;
		this.idUsuario = idUsuario;
		this.listaCompra = listaCompra;
	}

	public Itinerario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		String listaArchivo = "";
		for (Producto producto : listaCompra) {
			listaArchivo = listaArchivo + "\n" + producto.ofertas() + " \n";
		}
		return listaArchivo;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public int getIdItinerario() {
		return this.idItinerario;
	}

	public int getCostoItinerario() {
		costoItinerario = 0;
		for (Producto cadaProducto : this.listaCompra) {
			costoItinerario += cadaProducto.getCostoDeVisita();
		}
		return costoItinerario;
	}

	public double getDuracionItinerario() {
		duracionItinerario = 0;
		for (Producto cadaProducto : this.listaCompra) {
			duracionItinerario += cadaProducto.getTiempoDeVisita();
		}
		return duracionItinerario;
	}

	public void setCostoItinerario(int costoItinerario) {
		this.costoItinerario = costoItinerario;
	}

	public void setDuracionItinerario(double duracionItinerario) {
		this.duracionItinerario = duracionItinerario;
	}

	public void agregarAItinerario(Producto producto) {
		this.listaCompra.add(producto);
		this.costoItinerario += producto.getCostoDeVisita();
		this.duracionItinerario += producto.getTiempoDeVisita();
	}

	public boolean yaCompro(Producto otro) {
		// Verifica que el producto no haya sido comprado anteriormente
		for (Producto cadaProductoItinerario : listaCompra) {
			if (otro.esOContiene(cadaProductoItinerario) || cadaProductoItinerario.esOContiene(otro))
				return true;
		}
		return false;
	}

	public List<Producto> getListaCompra() {
		return listaCompra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + costoItinerario;
		long temp;
		temp = Double.doubleToLongBits(duracionItinerario);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + idItinerario;
		result = prime * result + idUsuario;
		result = prime * result + ((listaCompra == null) ? 0 : listaCompra.hashCode());
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
		Itinerario other = (Itinerario) obj;
		if (costoItinerario != other.costoItinerario)
			return false;
		if (Double.doubleToLongBits(duracionItinerario) != Double.doubleToLongBits(other.duracionItinerario))
			return false;
		if (idItinerario != other.idItinerario)
			return false;
		if (idUsuario != other.idUsuario)
			return false;
		if (listaCompra == null) {
			if (other.listaCompra != null)
				return false;
		} else if (!listaCompra.equals(other.listaCompra))
			return false;
		return true;
	}

}
