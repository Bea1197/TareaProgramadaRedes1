/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Beatriz Leiva
 */
public class Servidor extends Thread{

  @Override
    public void run() {

        final int PUERTO = 5000;
        byte[] buffer = new byte[100000];

        try {
            System.out.println("Iniciado el servidor UDP");
            //Creacion del socket
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);
               
            //Siempre atendera peticiones
            while (true) {

                //Preparo la respuesta
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                //Recibo el datagrama
                socketUDP.receive(peticion);
                System.out.println("Recibo la informacion del cliente");

                //Convierto lo recibido y mostrar el mensaje
                ByteArrayInputStream bis = new ByteArrayInputStream(peticion.getData());
                BufferedImage bImage2 = ImageIO.read(bis);
                ImageIO.write(bImage2, "jpg", new File("recibida.jpg"));

                String mensaje = "Recibí la imagen";
                System.out.println(mensaje);

                //Obtengo el puerto y la direccion de origen
                //Sino se quiere responder, no es necesario
                int puertoCliente = peticion.getPort();
                InetAddress direccion = peticion.getAddress();

                mensaje = "¡Hola mundo desde el servidor!";
                buffer = mensaje.getBytes();

                //creo el datagrama
                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);

                //Envio la información
                System.out.println("Envio la informacion del cliente");
                socketUDP.send(respuesta);

            }

        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
