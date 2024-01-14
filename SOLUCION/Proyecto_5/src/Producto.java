public class Producto {
    //ATRIBUTOS
    public int codigo;
    public String nombre;
    public double precio;
    public int cantidad;
    public String categoria;
    public String caducidad;

    //CONSTRUCTOR
    public Producto(int codigo, String nombre, Double precio, int cantidad, String categoria, String caducidad){
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.caducidad = caducidad;
    }

    //SETTERS
    public void setCodigo(int codigoINT){
        this.codigo = codigoINT;
    }
    public void setNombre(String nombreString){
        this.nombre = nombreString;
    }
    public void setPrecio(Double prDouble){
        this.precio = prDouble;
    }
    public void setCantidad(int cantidadINT){
        this.cantidad = cantidadINT;
    }
    public void setCategoria(String categoriaString){
        this.categoria = categoriaString;
    }
    public void setCaducidad(String caducidadString){
        this.caducidad = caducidadString;
    }

    //GETTERS
    public int getCodigo(){
        return this.codigo;
    }
    public String getNombre(){
        return this.nombre;
    }
    public double getPrecio(){
        return this.precio;
    }
    public int getCantidad(){
        return this.cantidad;
    }
    public String getCategoria(){
        return this.categoria;
    }
    public String getCaducidad(){
        return this.caducidad;
    }
}