package app;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;
import model.Administrador;
import model.ComparadorDeProductos;
import model.Itinerario;
import model.Producto;
import model.Usuario;

public class App {
	private static HashMap<Integer, Usuario> usuarios = new HashMap<Integer, Usuario>();
	private static List<Producto> productos = new ArrayList<Producto>();
	private static String entradaUsuario = "";

	private static String saludoBienvenida(String nombreUsuario) {
		return "Hola " + nombreUsuario + "\n";
	}

	private static String saludoDespedida(String nombreUsuario) {
		return "Que disfrute de su compra " + nombreUsuario + "\n";
	}

	public static void main(String[] args) {
		Administrador admin = Administrador.getInstance();
		usuarios = admin.getUsuarios();
		productos = admin.getProductos();

		Scanner entradaDeUsuario = new Scanner(System.in);

		for (Entry<Integer, Usuario> ListaUsuario : usuarios.entrySet()) {
			Usuario cadaUsuario = usuarios.get(ListaUsuario.getKey());
			Collections.sort(productos, new ComparadorDeProductos(cadaUsuario.getAtraccionPreferida()));

			List<Producto> listaProductosComprados = cadaUsuario.getItinerario().getListaCompra();
			if (listaProductosComprados.isEmpty())
				System.out.println(saludoBienvenida(cadaUsuario.getNombre()));
			else {
				System.out.println("Hola nuevamente " + cadaUsuario.getNombre().toUpperCase());
				System.out.println("\nUsted ya ha comprado anteriormente:\n");
				for (Producto producto : listaProductosComprados) {
					System.out.println(producto.getNombre().indent(5));
				}
				System.out.println("Su saldo actual es de " + cadaUsuario.getDineroDisponible()
						+ " monedas y su tiempo disponible " + cadaUsuario.getTiempoDisponible() + " Hs.\n");
			}

			for (Producto cadaProducto : productos) {
				if (cadaUsuario.puedeComprar(cadaProducto) && cadaProducto.hayCupo()) {
					System.out.println(cadaProducto.ofertas());
					System.out.println("Si desea adquirir este producto ingrese SI," + " de lo contrario ingrese NO");
					entradaUsuario = entradaDeUsuario.nextLine();
					if ((!entradaUsuario.contains("NO")) && (!entradaUsuario.contains("SI"))) {
						System.out
								.println("Si desea adquirir este producto ingrese SI," + " de lo contrario ingrese NO");
						entradaUsuario = entradaDeUsuario.nextLine();
					}
					if (entradaUsuario.contains("SI")) {
						cadaUsuario.comprar(cadaProducto);
						System.out.println(cadaProducto.getNombre() + " fue agregado a su itinerario.\n");
						model.Administrador.guardar(cadaProducto, cadaUsuario);
					} else if (entradaUsuario.contains("NO")) {
						System.out.println(cadaProducto.getNombre() + " No fue agregado a su itinerario.\n");
					}

					entradaUsuario = "";

				}
			}

			Itinerario itinerario = cadaUsuario.getItinerario();
			String listaCompra = "Su itinerario es: \n" + itinerario.toString() + "\n Dinero total invertido: "
					+ itinerario.getCostoItinerario() + " monedas.\n " + "\n Tiempo total necesario: "
					+ itinerario.getDuracionItinerario() + " horas.\n " + "\n Saldo de tiempo: "
					+ cadaUsuario.getTiempoDisponible() + " horas.\n " + "\n Saldo de dinero: "
					+ cadaUsuario.getDineroDisponible() + " monedas.\n ";
			System.out.println(listaCompra);
			System.out.println(
					saludoDespedida(cadaUsuario.getNombre()) + "\n Su archivo fue generado.------------------\n");
		}
		entradaDeUsuario.close();
	}
}