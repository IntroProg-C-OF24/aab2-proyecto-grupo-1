import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Proyecto_5 {

    static LocalDate fecha = LocalDate.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static String fechaFormat = fecha.format(formatter);
    static String archivoInventario = "Inventario.csv";
    static String archivoEstadistica = "Estadisticas.csv";
    static List<Factura> carrito;
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
    static double alimentacionD;
    static double educacionD;
    static double hogarD;
    static double vestimentaD;
    static double saludD;
    static double deducibleTotal;

    public static void main(String[] args) throws Exception {
        int limFactura = 5;
        Scanner sc = new Scanner(System.in);
        List<Producto> inventario = leerInventario(archivoInventario);
        System.out.println(fecha);

        for (int i = 0; i < limFactura; i++) {
            int limProductos = ran.nextInt(10) + 1;
            generarFactura(inventario, limProductos);
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println(
                    "Cod \t\t\t" + "Nombre \t\t\t\t" + " Cant \t" + " Descuento \t" +
                            " PrecioU \t" + "  Total");
            for (Factura prodFact : carrito) {
                System.out.printf("%-5s %-51s %-9s %-13s %-15.2f %.2f%n",
                        prodFact.getCodigo(), prodFact.getNombre(),
                        prodFact.getCantidad(), prodFact.getDescuento(), prodFact.getPrecio(),
                        prodFact.getTotal());
            }
            System.out.println();
            System.out.println(
                    "-----------------------------------------------------------------------------------------------------------");
            System.out.printf("SUBTOTAL \t\t %.2f \n", subTotal);
            System.out.printf("DESCUENTO \t\t %.2f\n", sumaDescuentos, "$");
            System.out.printf("AHORRO POR AFILIACION \t %.2f\n", subDescuentoAfiliado);
            System.out.printf("IVA \t\t\t %.2f \n", iva, "%");
            System.out.printf("SUBIVA \t\t\t %.2f\n", subIva);
            System.out.printf("TOTAL \t\t\t %.2f\n", total);
            System.out.println("---------------------------------");
            System.out.println("Forma de Pago: \t\t" + formaPago());
            System.out.println("---------------------------------");
            System.out.println("======== DEDUCIBLES ========");
            System.out.printf("Deducible Vivienda \t %.2f\n", hogarD);
            System.out.printf("Deducible Educacion \t %.2f\n", educacionD);
            System.out.printf("Deducible Alimentacion \t %.2f\n", alimentacionD);
            System.out.printf("Deducible Vestimenta \t %.2f\n", vestimentaD);
            System.out.printf("Deducible Salud \t %.2f\n", saludD);
            System.out.printf("Total Deducible \t %.2f\n", deducibleTotal);
            System.out.println("---------------------------------");
            System.out.println();
        }

        // PresentarInventario(inventario);

        sc.close();
    }

    public static List<Producto> leerInventario(String archivo) {
        List<Producto> inventario = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(archivo))) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(";");

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
        ReinicioVariables();
        double precioT;
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
                precioT = preciounitario * cantidad;
                sumaDescuentos += preciounitario;
                subTotal += preciounitario * cantidad;
                Factura factura = new Factura(codigo, producto.getNombre(), cantidad, descuento, producto.getPrecio(),
                        precioT);
                carrito.add(factura);

            } else {
                subTotal += preciounitario * cantidad;
                precioT = preciounitario * cantidad;
                Factura factura = new Factura(codigo, producto.getNombre(), cantidad, descuento, producto.getPrecio(),
                        precioT);
                carrito.add(factura);
            }
            
            DeduciblesImpuestos(producto.getCategoria(), precioT);

            EscribirEstadistica(codigo, producto.getNombre(), producto.getCategoria(), cantidad);

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

    public static String formaPago() {
        String forma;
        int n = ran.nextInt(3) + 1;
        if (n == 1) {
            forma = "Efectivo";
        } else if (n == 2) {
            forma = "tarjeta de credito";
        } else {
            forma = "tarjeta de debito";
        }
        return forma;
    }

    public static void PresentarInventario(List<Producto> inventario) {
        for (Producto producto : inventario) {
            System.out.println(producto.getCodigo() + ". " + producto.getNombre() + " - "
                    + producto.getPrecio() + " - " + producto.getCantidad() + " - " +
                    producto.getCategoria() + " - " + producto.getCaducidad());
        }
    }

    public static void EscribirEstadistica(int codigo, String nombre, String categoria, int nroVentas) {

        try {
            FileWriter fw = new FileWriter(archivoEstadistica, true);
            if (ArchivoVacio(archivoEstadistica)) {
                fw.write(fechaFormat + ";" + codigo + ";" + nombre + ";" + categoria + ";" + nroVentas + "\r\n");
            } else {
                fw.write("Fecha;Codigo;Nombre;Categoria;Ventas \r\n");
                fw.write(fechaFormat + ";" + codigo + ";" + nombre + ";" + categoria + ";" + nroVentas + "\r\n");
            }

            fw.close();
        } catch (IOException e) {
            System.out.println("Error al encontrar archivo " + e);
        }

    }

    public static boolean ArchivoVacio(String archivo) {
        File file = new File(archivo);
        if (!file.exists() || file.isDirectory()) {
            return false;
        }
        return file.length() > 0;
    }

    public static void ReinicioVariables() {
        subTotal = 0;
        subIva = 0;
        total = 0;
        subDescuentoAfiliado = 0;
        sumaDescuentos = 0;
        carrito = new ArrayList<>();
        descuento = 0;
        deducibleTotal = 0;
        alimentacionD = 0;
        educacionD = 0;
        hogarD = 0;
        vestimentaD = 0;
        saludD = 0;
    }

    public static void DeduciblesImpuestos(String categoriaD, double precioT) {
        if ("Alimentacion".equals(categoriaD)) {
            alimentacionD += precioT;
        } else if ("Educacion".equals(categoriaD)) {
            educacionD += precioT;
        } else if ("Hogar".equals(categoriaD)) {
            hogarD += precioT;
        } else if ("Vestimenta".equals(categoriaD)) {
            vestimentaD += precioT;
        } else if ("Salud".equals(categoriaD)) {
            saludD += precioT;
        }
        deducibleTotal += precioT;
    }

}