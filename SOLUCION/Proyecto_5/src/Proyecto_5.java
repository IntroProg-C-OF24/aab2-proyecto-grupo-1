import java.util.*;
import java.io.*;
public class Proyecto_5 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String nomArchivo = "Inventario.txt";
        List<Producto> inventario = leerInventario(nomArchivo);
        for (Producto producto : inventario) {
            System.out.println(producto.getCodigo() + ". "+ producto.getNombre() + " - " + producto.getPrecio() + " - " + producto.getCantidad() + " - " + producto.getCategoria() + " - " + producto.getCaducidad());
        }
        sc.close();
    }

    public static List<Producto> leerInventario(String archivo) {
        List<Producto> inventario = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(archivo))) {
            
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",");

                int codigo = Integer.parseInt(partes[0].trim());
                String nombre = partes[1].trim();
                double precio = Double.parseDouble(partes[2].trim());
                int cantidad = Integer.parseInt(partes[3].trim());
                String categoria = partes[4].trim();
                String caducidad = partes[5].trim();

                Producto producto = new Producto(codigo,nombre, precio, cantidad,categoria,caducidad);
                inventario.add(producto);
            }
        }
         catch (FileNotFoundException e) {
            System.out.println("Error al encontrar archivo "+e);
        }
        return inventario;
    }

}