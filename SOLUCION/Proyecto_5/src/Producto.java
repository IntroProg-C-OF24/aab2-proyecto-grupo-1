public class Producto {
    // ATRIBUTOS
    private int codigo;
    private String nombre;
    private double precio;
    private int cantidad;
    private String categoria;
    private String caducidad;

    // CONSTRUCTOR
    public Producto(int codigo, String nombre, Double precio, int cantidad, String categoria, String caducidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.caducidad = caducidad;
    }

    // SETTERS
    public void setCodigo(int codigoINT) {
        this.codigo = codigoINT;
    }

    public void setNombre(String nombreString) {
        this.nombre = nombreString;
    }

    public void setPrecio(Double prDouble) {
        this.precio = prDouble;
    }

    public void setCantidad(int cantidadINT) {
        this.cantidad = cantidadINT;
    }

    public void setCategoria(String categoriaString) {
        this.categoria = categoriaString;
    }

    public void setCaducidad(String caducidadString) {
        this.caducidad = caducidadString;
    }

    // GETTERS
    public int getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public double getPrecio() {
        return this.precio;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public String getCaducidad() {
        return this.caducidad;
    }

    // RESTAR CANTIDAD
    public void restarCantidad(int ran_Cant) {
        if (this.cantidad > 0) {
            this.cantidad -= ran_Cant;
        }else{
            System.out.println("NO HAY SUFICIENTES EN STOCk");
        }
    }
}