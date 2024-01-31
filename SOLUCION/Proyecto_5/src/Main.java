import java.util.*;
import java.io.*;

public class Main {
    static String archivoInventario = "Inventario\\Inventario.csv";

    public static void main(String[] args) throws Exception {
        String cliente = "Juan Manuel Gomez Lopez";
        int cedula = 1106951784;
        String direccion = "La Argelia";
        int limFactura = 1;
        int limProductos = 1;
        Scanner sc = new Scanner(System.in);
        List<Producto> inventario = leerInventario(archivoInventario);
        int op;
        do {
            System.out.println(
                    "Seleccione la opcion |      1.GENERAR FACTURAS      |       2.OBSERVAR INVENTARIO       |       3.SALIR");
            op = sc.nextInt();
            switch (op) {
                case 1:
                    System.out.println("==================== GENERADOR DE FACTURAS ====================");
                    System.out.println("INGRESE NUMERO DE FACTURAS A GENERAR");
                    limFactura = sc.nextInt();
                    System.out.println("INGRESE NUMERO DE PRODUCTOS POR FACTURA");
                    limProductos = sc.nextInt();
                    System.out.println("DESEAINGRESAR ALGUNA DIRECCION?     1. SI   |   2.NO");
                    if (sc.nextInt() == 1) {
                        System.out.println("INGRESE LA DIRECCION");
                        direccion = sc.nextLine().trim();

                    }else{
                        System.out.println("SE ASIGNARA UNA DIRECCION ALEATORIA ");
                    }
                    System.out.println("DESEA INGRESAR ALGUN NOMBRE?    1. SI  |   2. NO");
                    if (sc.nextInt() == 1) {
                        System.out.println("INGRESE EL NOMBRE");
                        cliente = sc.nextLine();
                        System.out.println("CEDULA DEL CLIENTE");
                        cedula = sc.nextInt();
                    } else {
                        System.out.println("SE ASIGANARA UN NOMBRE Y CEDULA ALEATORIA");
                    }
                    //AnimacionCargando("Generando");
                    for (int i = 0; i < limFactura; i++) {
                        Factura.generarFactura(inventario, limProductos, cliente, cedula, direccion);
                    }
                    break;
                case 2:
                    System.out.println("==================== INVENTARIO SUPERMAXI ====================");
                    PresentarInventario(inventario);
                    break;
                default:
                    break;
            }
        } while (op != 3);

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

    public static void PresentarInventario(List<Producto> inventario) {
        for (Producto producto : inventario) {
            System.out.println(producto.getCodigo() + ". " + producto.getNombre() + " - "
                    + producto.getPrecio() + " - " + producto.getCantidad() + " - " +
                    producto.getCategoria() + " - " + producto.getCaducidad());
        }
    }

    public static void AnimacionCargando(String texto) {
        int tiempo = 200;
        int repeticiones = 30;
        String[] animacion = { "|", "/", "-", "\\" };
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
        System.out.println("COMPLETADO!");
    }

}