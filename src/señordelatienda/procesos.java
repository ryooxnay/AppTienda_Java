
package señordelatienda;
import java.util.ArrayList;
import java.io.Serializable;

public class procesos implements Serializable{
    
   private ArrayList<Object> a = new ArrayList<Object>();
   
   public procesos(){
       
   }
       public procesos(ArrayList<Object> a){
           this.a = a;
       }
       public void agregarRegistro(Tienda t){
           this.a.add(t);
       }
       public void modificarRegistro(int i, Tienda t){
           this.a.set(i, t);
       }
       public void eliminarRegistro(int i){
           this.a.remove(i);
       }
       public Tienda obtenerRegistro(int i){
           return (Tienda)a.get(i);
       }
       public int cantidRegistro(){
           return this.a.size();
       }
       public int buscarCodigo(int codigo){
           for (int i = 1; i < cantidRegistro(); i++) {
               if(codigo == obtenerRegistro(i).getCodigo())return 1;
           }
           return -1;
       }
}
