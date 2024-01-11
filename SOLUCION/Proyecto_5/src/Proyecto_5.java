import java.util.*;
public class Proyecto_5 {
    public static void main(String[] args) throws Exception {
        String nomCatCadProd[][] = new String[20][7];
        double preCanProd[][] = new double[20][2];
        Gen_Precio_Cantidad_Producto(preCanProd);
    }
    public static void Gen_Nombre_Categoria_Producto(String matriz[][]){
        
    }
    public static void Gen_Precio_Cantidad_Producto(double matriz[][]){
        Random ran = new Random();
        for(int i=0 ; i<matriz.length ; i++){
            matriz[i][0] = ran.nextInt(100)+1;
            matriz[i][1] = ran.nextDouble()*19;
        }
    }
    public static void Gen_Facturar(){
        //generar una factura
    }
    
}
