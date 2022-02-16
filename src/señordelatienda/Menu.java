/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package señordelatienda;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
//
import señordelatienda.Limpiar;
import señordelatienda.TablaImagen;
//
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author USER
 */
public class Menu extends javax.swing.JFrame {

        Limpiar lt = new Limpiar();
    
    private String ruta_txt = "Tienda.txt"; 
    
    Tienda p;
    procesos rp;
    
    int clic_tabla;
    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        this.setLocationRelativeTo(null);
                rp = new procesos();
        
        try{
            cargar_txt();
            listarRegistro();
        }catch(Exception ex){
            mensaje("No existe el archivo txt");
        }
    }
     public void cargar_txt(){
        File ruta = new File(ruta_txt);
        try{
            FileInputStream fis = new FileInputStream(ruta_txt);
            ObjectInputStream in = new ObjectInputStream(fis);
            if (in != null) {
                rp = (procesos)in.readObject();
                in.close();
            }
            
//            FileReader fi = new FileReader(ruta);
//            BufferedReader bu = new BufferedReader(fi);
//            
//            
//            String linea = null;
//            while((linea = bu.readLine())!=null){
//                StringTokenizer st = new StringTokenizer(linea, ",");
//                p = new Tienda();
//                p.setCodigo(Integer.parseInt(st.nextToken()));
//                p.setNombre(st.nextToken());
//                p.setPrecio(Double.parseDouble(st.nextToken()));
//                p.setDescripcion(st.nextToken());
//                rp.agregarRegistro(p);
//            }
//            bu.close();
        }catch(Exception ex){
            mensaje("Error al cargar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
     
         public void grabar_txt(){
//        FileWriter fw;
//        PrintWriter pw;
             FileOutputStream fos;
             ObjectOutputStream out;
             
        try{
//            fw = new FileWriter(ruta_txt);
//            pw = new PrintWriter(fw);
            fos = new FileOutputStream(ruta_txt);
            out = new ObjectOutputStream(fos);
            if (out != null) {
                out.writeObject(rp);
                out.close();
            }
            
//            for(int i = 0; i < rp.cantidRegistro(); i++){
//                p = rp.obtenerRegistro(i);
//                pw.println(String.valueOf(p.getCodigo()+", "+p.getNombre()+", "+p.getPrecio()+", "+p.getDescripcion()));
//            }
//             pw.close();
            
        }catch(Exception ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
             public void ingresarRegistro(File ruta){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre() == null)mensaje("Ingresar Nombre");
            else if(leerPrecio() == -666) mensaje("Ingresar Precio");
            else if(leerDescripcion() == null)mensaje("Ingresar Descripcion");
            else{
                p = new Tienda(leerCodigo(), leerNombre(), leerPrecio(), leerDescripcion(),leerFoto(ruta));
                if(rp.buscarCodigo(p.getCodigo())!= -1)mensaje("Este codigo ya existe");
                else rp.agregarRegistro(p);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(jPanel1); 
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
        public void modificarRegistro(File ruta){
        try{
            if(leerCodigo() == -666)mensaje("Ingresar codigo entero");
            else if(leerNombre() == null)mensaje("Ingresar Nombre");
            else if(leerPrecio() == -666) mensaje("Ingresar Precio");
            else if(leerDescripcion() == null)mensaje("Ingresar Descripcion");
            else{
                int codigo = rp.buscarCodigo(leerCodigo());
                if (txt_foto.getText().equalsIgnoreCase("")) 
                      p = new Tienda(leerCodigo(), leerNombre(), leerPrecio(), leerDescripcion(),leerFoto2(codigo));
                 else
                p = new Tienda(leerCodigo(), leerNombre(), leerPrecio(), leerDescripcion(),leerFoto(ruta));
                
                if(codigo == -1)rp.agregarRegistro(p);
                else rp.modificarRegistro(codigo, p);
                
                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(jPanel1);
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
    
             
    public void eliminarRegistro(){
        try{
            if(leerCodigo() == -666) mensaje("Ingrese codigo entero");
            
            else{
                int codigo = rp.buscarCodigo(leerCodigo());
                if(codigo == -1) mensaje("codigo no existe");
                
                else{
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto","Si/No",0);
                    if(s == 0){
                        rp.eliminarRegistro(codigo);
                        
                        grabar_txt();
                        listarRegistro();
                        lt.limpiar_texto(jPanel1);
                    }
                }
                
                
            }
        }catch(Exception ex){
            mensaje(ex.getMessage());
        }
    }
             
         public void listarRegistro(){
        DefaultTableModel dt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        dt.addColumn("Codigo");
        dt.addColumn("Nombre");
        dt.addColumn("Precio");
        dt.addColumn("Descripcion");
        dt.addColumn("Foto");
        
        jTable1.setDefaultRenderer(Object.class, new TablaImagen());
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < rp.cantidRegistro(); i++){
            p = rp.obtenerRegistro(i);
            fila[0] = p.getCodigo();
            fila[1] = p.getNombre();
            fila[2] = p.getPrecio();
            fila[3] = p.getDescripcion();
            try{
                byte[] bi = p.getFotto();
                BufferedImage image = null;
                InputStream in = new ByteArrayInputStream(bi);
                image = ImageIO.read(in);
                ImageIcon img = new ImageIcon(image.getScaledInstance(60, 80, 0));
                fila[4] = new JLabel(img);
                lbl_imagen.setIcon(img);
            }catch (Exception ex){
                fila[4] = "No hay imagen";
            }
            
            dt.addRow(fila);
        }
        jTable1.setModel(dt);
        jTable1.setRowHeight(60);
    }        
    public int leerCodigo(){
        try{
            int codigo = Integer.parseInt(txt_Codigo.getText().trim());
            return codigo;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public String leerNombre(){
        try{
            String nombre = txt_Nombre.getText().trim().replace(" ", "_");
            return nombre;
        }catch(Exception ex){
            return null;
        }
    }
    
    public double leerPrecio(){
        try{
            double precio = Double.parseDouble(txt_precio.getText().trim());
            return precio;
        }catch(Exception ex){
            return -666;
        }
    }
    
    public Object leerDescripcion(){
        try{
            Object descripcion = txt_descrip.getText().trim();
            return descripcion;
        }catch(Exception ex){
            return null;
        }
    }
    
    public byte[] leerFoto(File ruta){
        try{
            byte[] icono = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(icono);
            return icono;
        }catch(Exception ex){
            return null;
        }
    }
        public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
        
            public byte[] leerFoto2(int codigo){
            p = rp.obtenerRegistro(codigo);
            try{
               return p.getFotto();
            }catch(Exception ex){
               return null;
            }
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_Modificar = new javax.swing.JButton();
        btn_Agregar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_imagen = new javax.swing.JLabel();
        txt_foto = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txt_descrip = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_precio = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_Codigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_Nombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_Google = new javax.swing.JButton();
        btn_tomarFoto = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(900, 600));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 377, 860, 210));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/escoba.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 290, -1, -1));

        btn_eliminar.setBackground(new java.awt.Color(0, 0, 102));
        btn_eliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_eliminar.setForeground(new java.awt.Color(255, 255, 255));
        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 310, -1, -1));

        btn_Modificar.setBackground(new java.awt.Color(0, 0, 102));
        btn_Modificar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Modificar.setForeground(new java.awt.Color(255, 255, 255));
        btn_Modificar.setText("Modificar");
        btn_Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Modificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 310, -1, -1));

        btn_Agregar.setBackground(new java.awt.Color(0, 0, 102));
        btn_Agregar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_Agregar.setText("Agregar");
        btn_Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar (2).png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 10, 40, -1));
        jPanel1.add(lbl_imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 40, 120, 110));
        jPanel1.add(txt_foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, 190, -1));

        btn_buscar.setBackground(new java.awt.Color(0, 0, 102));
        btn_buscar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_buscar.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar.setText("Buscar foto...");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 150, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Foto:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, -1, -1));

        txt_descrip.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel1.add(txt_descrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 320, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Descripcion:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, -1, -1));

        txt_precio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel1.add(txt_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 110, -1));

        jLabel1.setText("INVENTARIO");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Codigo: ");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, -1, -1));

        txt_Codigo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel1.add(txt_Codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 110, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nombre:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, -1, -1));

        txt_Nombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel1.add(txt_Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 110, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Precio:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, -1, -1));

        btn_Google.setBackground(new java.awt.Color(0, 0, 102));
        btn_Google.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Google.setForeground(new java.awt.Color(255, 255, 255));
        btn_Google.setText("Descargar imagen...");
        btn_Google.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GoogleActionPerformed(evt);
            }
        });
        jPanel1.add(btn_Google, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, -1, -1));

        btn_tomarFoto.setBackground(new java.awt.Color(0, 0, 102));
        btn_tomarFoto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_tomarFoto.setForeground(new java.awt.Color(255, 255, 255));
        btn_tomarFoto.setText("Tomar una foto...");
        btn_tomarFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tomarFotoActionPerformed(evt);
            }
        });
        jPanel1.add(btn_tomarFoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 230, -1, -1));

        fondo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        jPanel1.add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 600));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    Login l = new Login();
    l.setVisible(true);
    Menu m = new Menu();
    System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
                JFileChooser jf = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
        jf.setFileFilter(fil);
        jf.setCurrentDirectory(new File("Fotos"));
        int el = jf.showOpenDialog(this);
        if(el == JFileChooser.APPROVE_OPTION){
            txt_foto.setText(jf.getSelectedFile().getAbsolutePath());
            lbl_imagen.setIcon(new ImageIcon(txt_foto.getText()));
        }
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarActionPerformed
         File ruta = new File(txt_foto.getText());
        this.ingresarRegistro(ruta);
                txt_Codigo.setText("");
        txt_Nombre.setText("");
        txt_descrip.setText("");
        txt_foto.setText("");
        txt_precio.setText("");
        lbl_imagen.setText(null);
        
    }//GEN-LAST:event_btn_AgregarActionPerformed

    private void btn_ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ModificarActionPerformed
              File ruta = new File(txt_foto.getText());
        this.modificarRegistro(ruta);
    }//GEN-LAST:event_btn_ModificarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
              this.eliminarRegistro();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
              Limpiar lp = new Limpiar();
        lp.limpiar_texto(jPanel1);
        txt_Codigo.setText("");
        txt_Nombre.setText("");
        txt_descrip.setText("");
        txt_foto.setText("");
        txt_precio.setText("");
        lbl_imagen.setText(null);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
             clic_tabla = jTable1.rowAtPoint(evt.getPoint());
        
        int codigo = (int)jTable1.getValueAt(clic_tabla, 0);
        String nombre = ""+jTable1.getValueAt(clic_tabla, 1);
        double precio = (double)jTable1.getValueAt(clic_tabla, 2);
        Object descripcion = ""+jTable1.getValueAt(clic_tabla, 3);

        txt_Codigo.setText(String.valueOf(codigo));
        txt_Nombre.setText(nombre);
        txt_precio.setText(String.valueOf(precio));
        txt_descrip.setText(String.valueOf(descripcion));
        
        try{
            JLabel lbl = (JLabel)jTable1.getValueAt(clic_tabla, 4);
            lbl_imagen.setIcon(lbl.getIcon());
        }catch(Exception ex){
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btn_GoogleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GoogleActionPerformed
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop dk = java.awt.Desktop.getDesktop();
            if (dk.isSupported(java.awt.Desktop.Action.BROWSE)) {
                try{
                    java.net.URI uri = new java.net.URI("https://www.google.com.mx/imghp?hl=es-419&tab=ri&ogbl");
                    dk.browse(uri);
                }catch(URISyntaxException | IOException ex){
                    
                }
            }
        }
    }//GEN-LAST:event_btn_GoogleActionPerformed

    private void btn_tomarFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tomarFotoActionPerformed
       WebFoto w = new WebFoto();
       w.setVisible(true);
    }//GEN-LAST:event_btn_tomarFotoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Agregar;
    private javax.swing.JButton btn_Google;
    private javax.swing.JButton btn_Modificar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_tomarFoto;
    private javax.swing.JLabel fondo;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_imagen;
    private javax.swing.JTextField txt_Codigo;
    private javax.swing.JTextField txt_Nombre;
    private javax.swing.JTextField txt_descrip;
    private javax.swing.JTextField txt_foto;
    private javax.swing.JTextField txt_precio;
    // End of variables declaration//GEN-END:variables
}
