/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package señordelatienda;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;


import javax.swing.JOptionPane;
import claseUsuario.usuario;
/**
 *
 * @author USER
 */
public class Seguridad {
    Login L = new Login();
    String mensa;

    
    public void ValidarUsuario(String usuarios[], String user, String pwd, int intentos){
      boolean encontrado = false;
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i].equalsIgnoreCase(user)&& usuarios[i+1].equals(pwd)) {
                mensa = "Bienbenido " + user;
                Menu m = new Menu();
                m.setVisible(true);
                encontrado = true;
                JOptionPane.showMessageDialog(null, mensa, "Inicio de Sección",JOptionPane.INFORMATION_MESSAGE);
                intentos = 0;
                L.setIntentos(intentos);
                break;
            } 
        } if (encontrado == false) {
            mensa = "Clave y/o usuarios erroneos van " + intentos + " intentos fallidos";
            JOptionPane.showMessageDialog(null, mensa, "Inicio de Sección",JOptionPane.ERROR_MESSAGE);
        }
        if(intentos > 2){
               JOptionPane.showMessageDialog(null, "Tres intentos fallidos, esto se cerrara", "Inicio de Sección",JOptionPane.ERROR_MESSAGE);
               System.exit(0);
        }
        
        
    }
    
    public void Registrar(String usuario, String pwd){
        try{
            FileWriter fw = new FileWriter("Datos.txt");
            BufferedWriter bfw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bfw);
            
            pw.println(usuario);
            pw.println(pwd);
            pw.close();
            
        }catch(Exception e){
            JOptionPane.showInputDialog(null,e);
        }
    }
}
