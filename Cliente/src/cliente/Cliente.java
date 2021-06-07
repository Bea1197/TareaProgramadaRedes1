/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 *
 * @author Beatriz Leiva
 */
public class Cliente extends Thread{

   String ruta; 
//
//    public Cliente(String rutaArchivo) {
//        ruta = rutaArchivo;
//         
//    }
   
     @Override
    public void run() {
         // Crear el file chooser para abrir cuadro de dialogo y elegir un archivo
        JFileChooser jFileChooser = new JFileChooser();
        // titulo de dialogo
        jFileChooser.setDialogTitle("Escoger archivo enviado.");
        jFileChooser.showOpenDialog(jFileChooser);
         ruta = jFileChooser.getSelectedFile().getAbsolutePath();
         System.out.println("cliente"+ruta);
        //puerto del servidor
        final int PUERTO_SERVIDOR = 5000;
        //buffer donde se almacenara los mensajes
        byte[] buffer = new byte[100000];

        try {
            //Obtengo la localizacion de localhost
            InetAddress direccionServidor = InetAddress.getByName("localhost");

            //Creo el socket de UDP
            DatagramSocket socketUDP = new DatagramSocket();

            String mensaje = "¡Hola mundo desde el cliente!";

          

            //Convierto el mensaje a bytes
            
            buffer = mensaje.getBytes();
            RandomAccessFile f = new RandomAccessFile(ruta, "r");
            buffer = new byte[(int) f.length()];
            f.read(buffer);
            f.close();

            //Creo un datagrama
            DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);

            //Lo envio con send
            System.out.println("Envio el datagrama");
            socketUDP.send(pregunta);

            //Preparo la respuesta
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

            //Recibo la respuesta
            socketUDP.receive(peticion);
            System.out.println("Recibo la peticion");

            //Cojo los datos y lo muestro
            mensaje = new String(peticion.getData());

            //cierro el socket
            socketUDP.close();

        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
