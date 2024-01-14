import java.text.DecimalFormat;
import java.util.Random;

public class xd {
    public static void main(String[] args) {
        String categoria = "Salud";
        String[] nombre= { "Pinzas dentales",
        "Bisturí",
        "Escalpel",
        "Fórceps",
        "Tijeras quirúrgicas",
        "Tenacillas de pellizcar",
        "Aguja de hilo dental",
        "Espátula",
        "Vaso de Kidd",
        "Sifón de borbotones",
        "Bolsa de esponja",
        "Tapabocas quirúrgico",
        "Guantes estériles",
        "Bota de piel de cordero",
        "Gasa",
        "Algodón de lienzo",
        "Colirios",
        "Agujero de alfiler",
        "Eyedropper",
        "Compresa"},  
                caducidad= {"20/06/2027",
            "20/06/2027",
            "20/06/2025",
            "20/06/2025",
            "20/06/2024",
            "20/06/2024",
            "03/10/2024",
            "03/10/2026",
            "03/10/2026",
            "03/10/2026",
            "03/10/2026",
            "03/10/2026",
            "17/03/2026",
            "17/03/2027",
            "17/03/2027",
            "17/03/2027",
            "17/03/2027",
            "13/05/2024",
            "13/05/2024",
            "13/05/2024"};
        double[] precio= { 5.0,
 6.0,
 7.0,
 8.0,
 9.0,
10.0,
11.0,
12.0,
13.0,
14.0,
15.0,
16.0,
17.0,
18.0,
19.0,
20.0,
21.0,
22.0,
23.0,
24.0
};
        for(int i = 0 ; i<nombre.length ; i++){
            Random ran = new Random();
            int n = ran.nextInt(100)+1;
            double p = ran.nextDouble()*50;
            DecimalFormat df = new DecimalFormat("#.##");
            System.out.println((i+81) + " " +nombre[i]+ " " + df.format(p)+ " " + n + " " + categoria + " " + caducidad[i] );
        }
    }
    /* 
"Pinzas dentales",
"Bisturí",
"Escalpel",
"Fórceps",
"Tijeras quirúrgicas",
"Tenacillas de pellizcar",
"Aguja de hilo dental",
"Espátula",
"Vaso de Kidd",
"Sifón de borbotones",
"Bolsa de esponja",
"Tapabocas quirúrgico",
"Guantes estériles",
"Bota de piel de cordero",
"Gasa",
"Algodón de lienzo",
"Colirios",
"Agujero de alfiler",
"Eyedropper",
"Compresa",
     */

}
