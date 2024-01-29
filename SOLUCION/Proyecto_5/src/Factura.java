public class Factura {
    private int codigo;
    private String nombre;
    private int cantidad;
    private double descuento;
    private double precio;
    private double total;

    public Factura(int codigo, String nombre, int cantidad, double descuento, double precio, double total) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.precio = precio;
        this.total = total;
    }

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
        return this.descuento;
    }

    public double getPrecio() {
        return this.precio;
    }

    public double getTotal() {
        return this.total;
    }
}
