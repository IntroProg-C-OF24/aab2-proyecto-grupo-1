import java.util.*;
import java.io.*;

public class Proyecto_5 {
    static List<String> carrito;
    static int stock = 100;
    static Random ran = new Random();
    static double subTotal;
    static double subIva;
    static double descuento;
    static double sumaDescuentos;
    static double subDescuentoAfiliado;
    static double iva = 0.12;
    static double total;
    static double descuentoAfiliado = 0.05;
    static boolean afiliado = false;

    public static void main(String[] args) throws Exception {
        int limFactura = 5;
        Scanner sc = new Scanner(System.in);
        String nomArchivo = "C:\\LENGUAJES\\REPOSITORIO\\SEGUNDO BIMESTRE\\PROYECTO\\aab2-proyecto-grupo-1\\SOLUCION\\Proyecto_5\\Inventario.txt";
        List<Producto> inventario = leerInventario(nomArchivo);
        /*
         * for (Producto producto : inventario) {
         * System.out.println(producto.getCodigo() + ". "+ producto.getNombre() + " - "
         * + producto.getPrecio() + " - " + producto.getCantidad() + " - " +
         * producto.getCategoria() + " - " + producto.getCaducidad());
         * }
         */
        for (int i = 0; i < limFactura; i++) {
            System.out.println((i + 1) + ". FACTURA");
            int limProductos = ran.nextInt(10) + 1;
            generarFactura(inventario, limProductos);
            for (String lista : carrito) {
                System.out.println(lista);
            }
            System.out.println("------------------------------");
            System.out.println("SUBTOTAL " + subTotal);
            System.out.println("DESCUENTO " + sumaDescuentos + "$");
            System.out.println("AHORRO POR AFILIACION " + subDescuentoAfiliado);
            System.out.println("IVA " + iva + "%");
            System.out.println("SUBIVA " + subIva);
            System.out.println("TOTAL " + total);
            System.out.println("------------------------------");
        }
        sc.close();
    }

    public static List<Producto> leerInventario(String archivo) {
        List<Producto> inventario = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(archivo))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",");

                int codigo = Integer.parseInt(partes[0].trim());
                String nombre = partes[1].trim();
                double precio = Double.parseDouble(partes[2].trim());
                int cantidad = Integer.parseInt(partes[3].trim());
                String categoria = partes[4].trim();
                String caducidad = partes[5].trim();

                Producto producto = new Producto(codigo, nombre, precio, cantidad, categoria, caducidad);
                inventario.add(producto);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error al encontrar archivo " + e);
        }
        return inventario;
    }

    public static Producto buscaProducto(List<Producto> inventario, int ran_Cod) {
        for (Producto producto : inventario) {
            if (producto.getCodigo() == ran_Cod) {
                return producto;
            }
        }
        return null;
    }

    public static void aplicarDescuentoProducto(Producto producto) {
        descuento = 0;
        if (producto.getCantidad() > stock && producto.getCaducidad().contains("2024")) {
            descuento = 0.30;
        } else if (producto.getCantidad() > stock) {
            descuento = 0.10;
        } else if (producto.getCaducidad().contains("2024")) {
            descuento = 0.20;
        }
    }

    public static void generarFactura(List<Producto> inventario, int limProductos) {
        subTotal = 0;
        subIva = 0;
        total = 0;
        subDescuentoAfiliado = 0;
        sumaDescuentos = 0;
        carrito = new ArrayList<>();
        descuento = 0;
        double preciounitario;
        for (int i = 0; i < limProductos; i++) {
            int codigo = ran.nextInt(100) + 1;
            Producto producto = buscaProducto(inventario, codigo);
            int cantidad = ran.nextInt(10) + 1;
            producto.restarCantidad(cantidad);
            preciounitario = producto.getPrecio();
            aplicarDescuentoProducto(producto);
            if (descuento > 0) {
                preciounitario -= producto.getPrecio() * descuento;
                sumaDescuentos += preciounitario;
                subTotal += preciounitario * cantidad;
                carrito.add(producto.getCodigo() + ". " + producto.getNombre() + " " + cantidad + " " + descuento
                        + " " + producto.getPrecio());

            } else {
                subTotal += preciounitario * cantidad;
                carrito.add(producto.getCodigo() + ". " + producto.getNombre() + " " + cantidad + " " + descuento
                        + " " + producto.getPrecio());
            }

        }
        verificarAfiliado();
        if (afiliado) {
            total = subTotal - sumaDescuentos;
            subDescuentoAfiliado = total * descuentoAfiliado;
            total -= subDescuentoAfiliado;
            subIva = total * iva;
            total += subIva;

        } else {
            total = subTotal - sumaDescuentos;
            subIva = total * iva;
            total += subIva;
        }
    }

    public static void verificarAfiliado() {
        int aux = ran.nextInt(2) + 1;
        if (aux == 1) {
            afiliado = true;
        } else {
            afiliado = false;
        }
    }
}