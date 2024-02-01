
import java.awt.Desktop;
import java.util.*;
import java.io.*;

public class Main {

    static String archivoInventario = "Inventario\\Inventario.csv";
    public static String archivoFactura = "Facturas\\Factura.csv";
    public static String archivoEstadistica = "Estadistica\\Estadisticas.csv";

    public static void main(String[] args) throws Exception {
        String cliente = "Juan Manuel Gomez Lopez";
        int cedula = 1106951784;
        String direccion = "La Argelia";
        int limFactura = 1;
        int limProductos = 1;
        Scanner teclado = new Scanner(System.in);
        List<Producto> inventario = leerInventario(archivoInventario);
        int op;
        do {
            System.out.println(
                    "Seleccione la opcion |      1.GENERAR FACTURAS      |       2.OBSERVAR INVENTARIO       |       3.ESTADISTICAS     |       4.SALIR");
            op = teclado.nextInt();
            switch (op) {
                case 1:
                    int aux;
                    System.out.println("==================== GENERADOR DE FACTURAS ====================");
                    System.out.println("INGRESE NUMERO DE FACTURAS A GENERAR");
                    limFactura = teclado.nextInt();
                    System.out.println("INGRESE NUMERO DE PRODUCTOS POR FACTURA");
                    limProductos = teclado.nextInt();
                    System.out.println("DESEA INGRESAR ALGUNA DIRECCION?     1. SI   |   2.NO");
                    if (teclado.nextInt() == 1) {
                        System.out.println("INGRESE LA DIRECCION");
                        direccion = teclado.next().trim();

                    } else {
                        System.out.println("SE ASIGNARA UNA DIRECCION ALEATORIA ");
                    }
                    System.out.println("DESEA INGRESAR ALGUN NOMBRE?    1. SI  |   2. NO");
                    if (teclado.nextInt() == 1) {
                        System.out.println("INGRESE EL NOMBRE");
                        cliente = teclado.next();
                        System.out.println("CEDULA DEL CLIENTE");
                        cedula = teclado.nextInt();
                    } else {
                        System.out.println("SE ASIGANARA UN NOMBRE Y CEDULA ALEATORIA");
                    }
                    //AnimacionCargando("Generando");
                    for (int i = 0; i < limFactura; i++) {
                        Factura.generarFactura(inventario, limProductos, cliente, cedula, direccion, i+1);
                    }
                    ModificarInventario(inventario);
                    System.out.println("DESEA ABIR EL ARCHIVO?  1. SI   |   2.NO");
                    aux = teclado.nextInt();
                    if (aux == 1) {

                        //AnimacionCargando("Abriendo");
                        AbrirArchivo(archivoFactura);
                    }
                    break;
                case 2:
                    //AnimacionCargando("Abriendo");
                    AbrirArchivo(archivoInventario);
                    break;
                case 3:
                    if (Factura.ArchivoVacio(archivoEstadistica)) {
                        //AnimacionCargando("Abriendo");
                        AbrirArchivo(archivoEstadistica);
                    } else {
                        System.out.println("NO HAY FACTURAS PARA GENERAR ESTADISTICA");
                    }
                    break;
                default:
                    break;
            }
        } while (op != 4);
    }

    public static List<Producto> leerInventario(String archivo) {
        List<Producto> inventario = new ArrayList<>();

        try (Scanner tecladoanner = new Scanner(new File(archivo))) {
            while (tecladoanner.hasNextLine()) {
                String linea = tecladoanner.nextLine();
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

    public static void PresentarInventario(List<Producto> inventario) {
        for (Producto producto : inventario) {
            System.out.println(producto.getCodigo() + ". " + producto.getNombre() + " - "
                    + producto.getPrecio() + " - " + producto.getCantidad() + " - "
                    + producto.getCategoria() + " - " + producto.getCaducidad());
        }
    }

    public static void AnimacionCargando(String texto) {
        int tiempo = 200;
        int repeticiones = 30;
        String[] animacion = {"|", "/", "-", "\\"};
        System.out.println("=== " + (texto + "...") + " ===");
        for (int i = 0; i < repeticiones; i++) {
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.print("\r" + animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
            System.out.print(animacion[i % animacion.length]);
        }
        System.out.println();
        System.out.println("!COMPLETADO!");
    }

    public static void AbrirArchivo(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        try {
            Desktop.getDesktop().open(archivo);
        } catch (IOException e) {
            System.out.println("NO SE PUDO ABRIR EL ARCHIVO " + e);
        }
    }
    public static void ModificarInventario(List<Producto> inventario){
        try(FileWriter fw = new FileWriter(archivoInventario)){
            for (Producto producto : inventario) {
                fw.write(producto.getCodigo()+";"+producto.getNombre()+";"+producto.getPrecio()+";"+producto.getCantidad()+";"+producto.getCategoria()+";"+producto.getCaducidad()+"\r\n");
            }
        } catch (Exception e) {

        }
    }
}
