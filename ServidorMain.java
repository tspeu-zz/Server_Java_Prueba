
//package PaquetePrincipal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JM_B
 */
public class ServidorMain {
    

  public static void main(String[] args) {
            System.out.println("Servidor iniciado ");
      try {
          //Asociamos al servidor el puerto 8066
          ServerSocket socServidor = new ServerSocket(8066);
          //imprimeDisponible();
          Socket socCliente;
          
          //ante una petición entrante, procesa la petición por el socket cliente
          //por donde la recibe
          while (true) {
              //a la espera de peticiones
              socCliente = socServidor.accept();
              //atiendo un cliente
              System.out.println("Atendiendo al cliente ");
              //procesaPeticion(socCliente);
              //cierra la conexión entrante
              ServidorHTTP cliente = new ServidorHTTP(socCliente);
              cliente.start();
              cliente.imprimeDisponible();
              //socCliente.close();
              System.out.println("cliente atendido");
          } 
      } catch (IOException ex) {
            Logger.getLogger(ServidorMain.class.getName()).log(Level.SEVERE, null, ex);
      }
  }

    /*try {
  socServidor = new ServerSocket(puerto);
    while (true) {
      //acepta una petici�n, y le asigna un socket cliente para la respuesta
      socketCliente = socServidor.accept();
      //crea un nuevo hilo para despacharla por el socketCliente que le asign�
      hilo = new HiloDespachador(socketCliente);
      hilo.start();
    }
  } catch (IOException ex) {
}
*/
}