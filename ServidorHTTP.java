//package PaquetePrincipal;


import java.io.BufferedReader;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;


class ServidorHTTP extends Thread{

    Socket Cliente;
    InputStreamReader inSR;
    PrintWriter printWriter;
    
  private  String getDateValue(){
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z",
    Locale.ENGLISH);
    df.setTimeZone(TimeZone.getTimeZone("GMT"));
    return df.format(new Date());
  }

  
  public ServidorHTTP(Socket cliente){
      this.Cliente= cliente;
  }
  
    @Override
  public void run()  {
        try {
            //variables locales
            String peticion;
            String html;
            //Flujo de entrada
            inSR = new InputStreamReader(Cliente.getInputStream());
            //espacio en memoria para la entrada de peticiones
            BufferedReader bufLeer = new BufferedReader(inSR);      
            //objeto de java.io que entre otras características, permite escribir
            //'línea a línea' en un flujo de salida
            printWriter = new PrintWriter(Cliente.getOutputStream(), true);
            
            //mensaje petición cliente
            peticion = bufLeer.readLine();
            
            //para compactar la petición y facilitar así su análisis, suprimimos todos
            //los espacios en blanco que contenga
            peticion = peticion.replaceAll(" ", "");
            
            //si realmente se trata de una petición 'GET' (que es la única que vamos a
            //implementar en nuestro Servidor)
            if (peticion.startsWith("GET")) {
                //extrae la subcadena entre 'GET' y 'HTTP/1.1'
                peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));
                
                //si corresponde a la página de inicio
                if (peticion.length() == 0 || peticion.equals("/")) {
                    //sirve la página
                    html = Paginas.html_index;
                    printWriter.println(Mensajes.lineaInicial_OK);
                    printWriter.println(Paginas.primeraCabecera);
                    printWriter.println("Content-Length: " + html.length());
                    printWriter.println("Date: " + getDateValue());
                    printWriter.println("\n");
                    printWriter.println(html);
                } //si corresponde a la página del Quijote
                else if (peticion.equals("/quijote")) {
                    //sirve la página
                    html = Paginas.html_quijote;
                    printWriter.println(Mensajes.lineaInicial_OK);
                    printWriter.println("Content-Type:text/html;charset=UTF-8;");
                    printWriter.println("Content-Length: " + html.length());
                    printWriter.println("Date: "+ getDateValue());
                    printWriter.println("Expires: " + "Tue, 12 Oct 2005 06:22:41 GMT");
                    printWriter.println("\n");
                    printWriter.println(html);
                } //en cualquier otro caso
                else {
                    //sirve la página
                    html = Paginas.html_noEncontrado;
                    printWriter.println(Mensajes.lineaInicial_NotFound);
                    printWriter.println(Paginas.primeraCabecera);
                    printWriter.println("Content-Length: " + html.length() + 1);
                    printWriter.println("Date: " + getDateValue());
                    printWriter.println("\n");
                    printWriter.println(html);
                }
                
            }   } catch (IOException ex) {
            Logger.getLogger(ServidorHTTP.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

  /**
   * **************************************************************************
   * muestra un mensaje en la Salida que confirma el arranque, y da algunas
   * indicaciones posteriores
   */
  public static void imprimeDisponible() {
    Date date = new Date();
    DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
      
    System.out.println("El Servidor WEB se está ejecutando y permanece a la "
            + "escucha por el puerto 8066.\nEscribe en la barra de direcciones "
            + "de tu explorador preferido:\n\nhttp://localhost:8066\npara "
            + "solicitar la página de bienvenida\n\nhttp://localhost:8066/"
            + "quijote\n para solicitar una página del Quijote,\n\nhttp://"
            + "localhost:8066/q\n para simular un error");
    System.out.println("Hora y fecha: "+hourdateFormat.format(date));
      //System.out.println("cabecera "+getDateValue());

  }

/**
   * **************************************************************************
   * procedimiento principal que asigna a cada petición entrante un socket 
   * cliente, por donde se enviará la respuesta una vez procesada 
   *
   * @param args the command line arguments
   */
//  public static void main(String[] args) throws IOException, Exception {
//
//    //Asociamos al servidor el puerto 8066
//    ServerSocket socServidor = new ServerSocket(8066);
//    imprimeDisponible();
//    Socket socCliente;
//
//    //ante una petición entrante, procesa la petición por el socket cliente
//    //por donde la recibe
//    while (true) {
//      //a la espera de peticiones
//      socCliente = socServidor.accept();
//      //atiendo un cliente
//      System.out.println("Atendiendo al cliente ");
//      procesaPeticion(socCliente);
//      //cierra la conexión entrante
//      socCliente.close();
//      System.out.println("cliente atendido");
//    }
//  }

}

