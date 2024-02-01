
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
    static int alimentacionD;
    static int educacionD;
    static int hogarD;
    static int vestimentaD;
    static int saludD;
    static List<Factura> carrito;
    static int stock = 100;
    public static String archivoEstadistica = "Estadistica\\Estadisticas.csv";
    // CADENA VARIABLES
    static String cadena_subTotal;
    static String cadena_subIva;
    static String cadena_sumaDescuentos;
    static String cadena_subDescuentoAfiliado;
    static String cadena_total;
    //
    // RUTA ARCHIVO
    public static String archivoFactura = "Facturas\\Factura.csv";
    static String archivoInventario = "Inventario\\Inventario.csv";
    static File facturas = new File(archivoFactura);

    //
    public static void main(String[] args) {
        // GenerarArchivoFactura();
    }

    // CONSTRUCTOR
    public Factura(int codigo, String nombre, int cantidad, double descuento, double precio, double total) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.descuentoFac = descuento;
        this.precio = precio;
        this.totalFac = total;
    }

    //
    // GETERS //////////////////
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
    ///////////////////////////

    public static void generarFactura(List<Producto> inventario, int limProductos, String cliente, int cedula,
            String direccion, int numFactura) {
        ReinicioVariables();
        double precioT;
        double preciounitario;
        int autorizacionSRI = ran.nextInt(1000) + 1;
        for (int i = 0; i < limProductos; i++) {
            int codigo = ran.nextInt(100) + 1;
            Producto producto = buscaProducto(inventario, codigo);
            int cantidad = ran.nextInt(5) + 1;
            if (producto.getCantidad() > cantidad) {
                producto.restarCantidad(cantidad);
                preciounitario = producto.getPrecio();
                aplicarDescuentoProducto(producto);
                if (descuento > 0) {
                    preciounitario -= producto.getPrecio() * descuento;
                    precioT = preciounitario * cantidad;
                    sumaDescuentos += preciounitario;
                    subTotal += preciounitario * cantidad;
                    Factura factura = new Factura(codigo, producto.getNombre(), cantidad, descuento,
                            producto.getPrecio(),
                            precioT);
                    carrito.add(factura);

                } else {
                    subTotal += preciounitario * cantidad;
                    precioT = preciounitario * cantidad;
                    Factura factura = new Factura(codigo, producto.getNombre(), cantidad, descuento,
                            producto.getPrecio(),
                            precioT);
                    carrito.add(factura);
                }

                DeduciblesImpuestos(producto.getCategoria());
                EscribirEstadistica(codigo, producto.getNombre(), producto.getCategoria(), cantidad);
            }else if (producto.getCantidad() > 0) {
                preciounitario = producto.getPrecio();
                aplicarDescuentoProducto(producto);
                if (descuento > 0) {
                    preciounitario -= producto.getPrecio() * descuento;
                    precioT = preciounitario * cantidad;
                    sumaDescuentos += preciounitario;
                    subTotal += preciounitario * cantidad;
                    Factura factura = new Factura(codigo, producto.getNombre(), cantidad, descuento,
                            producto.getPrecio(),
                            precioT);
                    carrito.add(factura);

                } else {
                    subTotal += preciounitario * cantidad;
                    precioT = preciounitario * cantidad;
                    Factura factura = new Factura(codigo, producto.getNombre(), producto.getCantidad(), descuento,
                            producto.getPrecio(),
                            precioT);
                    carrito.add(factura);
                }

                DeduciblesImpuestos(producto.getCategoria());
                EscribirEstadistica(codigo, producto.getNombre(), producto.getCategoria(), cantidad);
                producto.setCantidad(0);
            }
        }
        if (verificarAfiliado()) {
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
        if (!carrito.isEmpty()) {
            GenerarArchivoFactura(carrito, cliente, cedula, direccion, autorizacionSRI,
            numFactura, hogarD, educacionD,
            alimentacionD, vestimentaD, saludD);
        }
    }

    public static boolean verificarAfiliado() {
        int aux = ran.nextInt(2) + 1;
        return aux == 1;
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
        cadena_sumaDescuentos = formato.format(sumaDescuentos);
        cadena_subDescuentoAfiliado = formato.format(subDescuentoAfiliado);
        cadena_total = formato.format(total);
    }

    public static void DeduciblesImpuestos(String categoriaD) {
        switch (categoriaD) {
            case "Alimentacion":
                alimentacionD = ran.nextInt(10) + 1;
                break;
            case "Educacion":
                educacionD = ran.nextInt(10) + 1;
                break;
            case "Hogar":
                hogarD = ran.nextInt(10) + 1;
                break;
            case "Vestimenta":
                vestimentaD = ran.nextInt(10) + 1;
                break;
            case "Salud":
                saludD = ran.nextInt(10) + 1;
                break;
            default:
                break;
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

        try (FileWriter fw = new FileWriter(archivoEstadistica, true)) {
            File archivo = new File("Estadistica\\Estadisticas.csv");
            if (ArchivoVacio(archivoEstadistica)) {
                fw.write(fechaFormat + ";" + codigo + ";" + nombre + ";" + categoria + ";" + nroVentas + "\r\n");
            } else {
                archivo.createNewFile();
                fw.write("Fecha;Codigo;Nombre;Categoria;Ventas \r\n");
                fw.write(fechaFormat + ";" + codigo + ";" + nombre + ";" + categoria + ";" + nroVentas + "\r\n");
            }
        } catch (IOException e) {
            System.out.println("Error al encontrar archivo " + e);
        }

    }

    public static void GenerarArchivoFactura(List<Factura> carrito, String cliente, int cedula, String direccion,
            int autorizacionSRI,int numFactura, int hogarD, int educacionD, int alimentacionD, int vestimentaD, int saludD) {
        try (FileWriter fw = new FileWriter(facturas, true)) {
            if (!facturas.exists()) {
                facturas.createNewFile();
            }
            TransformarDosDecimals();
            fw.write(
                    "----------------------------------------------- SUPERMAXI -----------------------------------------------;\r\n");
            fw.write(
                    "                                           DIRECCION SUCURSAL:                                           ;\r\n");
            fw.write(
                    "                                           AV. 18 DE NOVIEMBRE                                           ;\r\n");
            fw.write(
                    "                                             LOJA - ECUADOR                                              ;\r\n");
            fw.write(
                    "                                            RUC:1790016919001                                            ;\r\n");
            fw.write(
                    "---------------------------------------------------------------------------------------------------------;\r\n");
            fw.write("COD;Descipcion;Cant;Descuento;PrecioU;Total\r\n");
            for (Factura prodFact : carrito) {
                String formatoTotal = String.format("%.2f", prodFact.getTotal());

                fw.write(prodFact.getCodigo() + ";" + prodFact.getNombre() + ";" + prodFact.getCantidad() + ";"
                        + prodFact.getDescuento() + ";" + prodFact.getPrecio() + ";"
                        + formatoTotal + "\r\n");
            }
            fw.write(
                    "---------------------------------------------------------------------------------------------------------;\r\n");
            fw.write("SUBTOTAL;" + cadena_subTotal + ";\r\n");
            fw.write("DESCUENTO;" + cadena_sumaDescuentos + ";\r\n");
            fw.write("AHORRO POR AFILIACION;" + cadena_subDescuentoAfiliado + ";\r\n");
            fw.write("IVA;" + iva + "%" + ";\r\n");
            fw.write("SUBIVA;" + cadena_subIva + ";\r\n");
            fw.write("TOTAL;" + cadena_total + ";\r\n");
            fw.write("--------------------------------------------------------\r\n");
            fw.write("CLIENTE;" + cliente + ";\r\n");
            fw.write("CEDULA;" + cedula + ";\r\n");
            fw.write("DIRECCION;" + direccion + ";\r\n");
            fw.write("FACTURA Nro;" + numFactura + ";\r\n");
            fw.write("FEHCA EMISION (dd/mm/aaaa);" + fechaFormat + ";\r\n");
            fw.write("AUTORIZACION SRI;" + autorizacionSRI + ";\r\n");
            fw.write("Forma de Pago;" + formaPago() + ";\r\n");
            fw.write("========== DEDUCIBLES ==========" + ";\r\n");
            fw.write("Vivienda;" + hogarD + "% " + ";\r\n");
            fw.write("Educacion;" + educacionD + "% " + ";\r\n");
            fw.write("Alimentacion;" + alimentacionD + "% " + ";\r\n");
            fw.write("Vestimenta;" + vestimentaD + "% " + ";\r\n");
            fw.write("Salud;" + saludD + "% " + ";\r\n");
            fw.write("---------------------------------" + ";\r\n");
        } catch (IOException e) {
            System.out.println("EROR AL CREAR ARCHIVO " + e);
        }

    }

    public static String formaPago() {
        int n = ran.nextInt(3) + 1;
        switch (n) {
            case 1:
                return "Efectivo";
            case 2:
                return "tarjeta de credito";
            default:
                return "tarjeta de debito";
        }
    }

}
