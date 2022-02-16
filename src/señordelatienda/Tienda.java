
package señordelatienda;
import java.io.Serializable;

public class Tienda implements Serializable{
    private int codigo;
    private String nombre;
    private double precio;
    private Object descripcion;
    private byte[] fotto;

    public Tienda(){
        
    }
    public Tienda(int codigo, String nombre, double precio, Object descripcion, byte[] fotto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.fotto = fotto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Object getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Object descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the fotto
     */
    public byte[] getFotto() {
        return fotto;
    }

    /**
     * @param fotto the fotto to set
     */
    public void setFotto(byte[] fotto) {
        this.fotto = fotto;
    }
    
}
