import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Factura {
    // ATRIBUTOS
    private int codigo;
    private String nombre;
    private int cantidad;
    private double descuentoFac;
    private double precio;
    private double totalFac;
    //
    //
    static Random ran = new Random();
    static LocalDate fecha = LocalDate.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static String fechaFormat = fecha.format(formatter);
    static double subTotal;
    static double subIva;
    static double descuento;
    static double sumaDescuentos;
    static double subDescuentoAfiliado;
    static double iva = 0.12;
    static double total;
    static double descuentoAfiliado = 0.05;
    static double alimentacionD;
    static double educacionD;
    static double hogarD;
    static double vestimentaD;
    static double saludD;
    static boolean afiliado = false;
    static List<Factura> carrito;
    static int stock = 100;
    static String archivoEstadistica = "Estadisticas.csv";
    static int nroFactura = ran.nextInt(1000) + 1;
    // CADENA VARIABLES
    static String cadena_subTotal;
    static String cadena_subIva;
    static String cadena_descuento;
    static String cadena_sumaDescuentos;
    static String cadena_subDescuentoAfiliado;
    static String cadena_iva = "0.12";
    static String cadena_total;
    static String cadena_alimentacionD;
    static String cadena_educacionD;
    static String cadena_hogarD;
    static String cadena_vestimentaD;
    static String cadena_saludD;
    //
    // RUTA ARCHIVO
    static String rutaArchivo = "Facturas\\Factura.txt";
    static File facturas = new File(rutaArchivo);

    //
    public static void main(String[] args) {
        // GenerarArchivoFactura();
    }

    public Factura(int codigo, String nombre, int cantidad, double descuento, double precio, double total) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.descuentoFac = descuento;
        this.precio = precio;
        this.totalFac = total;
    }

    // GETERS
    public int getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public double getDescuento() {
        return this.descuentoFac;
    }

    public double getPrecio() {
        return this.precio;
    }

    public double getTotal() {
        return this.totalFac;
    }

    public static void generarFactura(List<Producto> inventario, int limProductos, String cliente, int cedula, String direccion) {
        ReinicioVariables();
        double precioT;
        double preciounitario;

        for (int i = 0; i < limProductos; i++) {
            int codigo = ran.nextInt(100) + 1;
            nroFactura = ran.nextInt(1000) + 1;
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

            DeduciblesImpuestos(producto.getCategoria());
            EscribirEstadistica(codigo, producto.getNombre(), producto.getCategoria(), cantidad);
            TransformarDosDecimals();
        }
        GenerarArchivoFactura(carrito, cadena_subTotal, cadena_sumaDescuentos, cadena_subDescuentoAfiliado, iva,
                cadena_subIva, cadena_total, cliente, cedula,direccion, nroFactura, cadena_hogarD, cadena_educacionD,
                cadena_alimentacionD, cadena_vestimentaD, cadena_saludD);
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

    public static void ReinicioVariables() {
        subTotal = 0;
        subIva = 0;
        total = 0;
        subDescuentoAfiliado = 0;
        sumaDescuentos = 0;
        carrito = new ArrayList<>();
        descuento = 0;
        alimentacionD = 0;
        educacionD = 0;
        hogarD = 0;
        vestimentaD = 0;
        saludD = 0;
    }

    public static void TransformarDosDecimals() {
        DecimalFormat formato = new DecimalFormat("#.##");
        cadena_subTotal = formato.format(subTotal);
        cadena_subIva = formato.format(subIva);
        cadena_descuento = formato.format(descuento);
        cadena_sumaDescuentos = formato.format(sumaDescuentos);
        cadena_subDescuentoAfiliado = formato.format(subDescuentoAfiliado);
        cadena_total = formato.format(total);
        cadena_alimentacionD = formato.format(alimentacionD);
        cadena_educacionD = formato.format(educacionD);
        cadena_hogarD = formato.format(hogarD);
        cadena_vestimentaD = formato.format(vestimentaD);
        cadena_saludD = formato.format(saludD);
    }

    public static void DeduciblesImpuestos(String categoriaD) {
        if ("Alimentacion".equals(categoriaD)) {
            alimentacionD = ran.nextInt(10)+1;
        } else if ("Educacion".equals(categoriaD)) {
            educacionD = ran.nextInt(10)+1;
        } else if ("Hogar".equals(categoriaD)) {
            hogarD = ran.nextInt(10)+1;
        } else if ("Vestimenta".equals(categoriaD)) {
            vestimentaD = ran.nextInt(10)+1;
        } else if ("Salud".equals(categoriaD)) {
            saludD = ran.nextInt(10)+1;
        }
    }

    public static boolean ArchivoVacio(String archivo) {
        File file = new File(archivo);
        if (!file.exists()) {
            return false;
        }
        return file.length() > 0;
    }

    public static Producto buscaProducto(List<Producto> inventario, int ran_Cod) {
        for (Producto producto : inventario) {
            if (producto.getCodigo() == ran_Cod) {
                return producto;
            }
        }
        return null;
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

    public static void GenerarArchivoFactura(List<Factura> carrito, String subTotal, String sumaDescuentos,
            String subDescuentoAfiliado, double iva, String subIva, String total, String cliente, int cedula, String direccion,
            int nroFactura, String hogarD, String educacionD, String alimentacionD, String vestimentaD, String saludD) {
        try {
            if (!facturas.exists()) {
                facturas.createNewFile();
            }
            FileWriter fw = new FileWriter(facturas, true);
            fw.write(
                    "----------------------------------------------- SUPERMAXI -----------------------------------------------\r\n");
            fw.write(
                    "                                           DIRECCION SUCURSAL:                                           \r\n");
            fw.write(
                    "                                           AV. 18 DE NOVIEMBRE                                           \r\n");
            fw.write(
                    "                                             LOJA - ECUADOR                                              \r\n");
            fw.write(
                    "                                            RUC:1790016919001                                            \r\n");
            fw.write(
                    "---------------------------------------------------------------------------------------------------------\r\n");
            fw.write("COD       Descipcion                                   Cant  Descuento PrecioU Total\r\n");
            for (Factura prodFact : carrito) {
                String formatoTotal = String.format("%.2f", prodFact.getTotal());
                fw.write(String.format("%-5s %-50s %-6s %-8s %-6s %-8s%n",
                        prodFact.getCodigo(), prodFact.getNombre(), prodFact.getCantidad(),
                        prodFact.getDescuento(), prodFact.getPrecio(), formatoTotal));
            }
            fw.write(
                    "---------------------------------------------------------------------------------------------------------\r\n");
            fw.write("SUBTOTAL                  " + cadena_subTotal + "\r\n");
            fw.write("DESCUENTO                 " + cadena_sumaDescuentos + "%" +"\r\n");
            fw.write("AHORRO POR AFILIACION     " + cadena_subDescuentoAfiliado + "\r\n");
            fw.write("IVA                       " + iva + "%" + "\r\n");
            fw.write("SUBIVA                    " + cadena_subIva + "\r\n");
            fw.write("TOTAL                     " + cadena_total + "\r\n");
            fw.write("--------------------------------------------------------\r\n");
            fw.write("CLIENTE                      " + cliente + "\r\n");
            fw.write("CEDULA                       " + cedula + "\r\n");
            fw.write("DIRECCION                    " + direccion + "\r\n");
            fw.write("FACTURA Nro                  " + nroFactura + "\r\n");
            fw.write("FEHCA EMISION (dd/mm/aaaa)   " + fechaFormat + "\r\n");
            fw.write("AUTORIZACION SRI             " + nroFactura + "\r\n");
            fw.write("Forma de Pago                " + formaPago() + "\r\n");
            fw.write("========== DEDUCIBLES ==========" + "\r\n");
            fw.write("Vivienda          " + cadena_hogarD + "% "+"\r\n");
            fw.write("Educacion         " + cadena_educacionD + "% "+"\r\n");
            fw.write("Alimentacion      " + cadena_alimentacionD + "% "+"\r\n");
            fw.write("Vestimenta        " + cadena_vestimentaD + "% "+"\r\n");
            fw.write("Salud             " + cadena_saludD + "% "+"\r\n");
            fw.write("---------------------------------" + "\r\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("EROR AL CREAR ARCHIVO " + e);
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

}
