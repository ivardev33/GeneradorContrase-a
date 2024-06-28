package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    private static final int PUERTO = 4321;
    private ServerSocket serverSocket;

    public void iniciar() {
        try {
            serverSocket = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado en el puerto " + PUERTO);

            while (true) {
                System.out.println("Esperando clientes...");
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clienteSocket.getInetAddress());

                // Aquí se manejarán las interacciones con el cliente
                manejarInteracciones(clienteSocket);

                // No cerramos el clienteSocket aquí para permitir múltiples interacciones
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private void manejarInteracciones(Socket clienteSocket) {
        try {
        	
            BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            PrintWriter salidaServidor = new PrintWriter(clienteSocket.getOutputStream(), true);

            // 1. Servidor pregunta el nombre del cliente
            salidaServidor.println("Ingrese su nombre:");
            String nombreCliente = entradaCliente.readLine();
            System.out.println("Nombre del cliente: " + nombreCliente);

            // 2. Servidor da la bienvenida al cliente
            salidaServidor.println("¡Bienvenido, " + nombreCliente + "!");

            // 3. Servidor introduce al cliente a la funcionalidad
            salidaServidor.println("Por favor ingrese el número de mayúsculas, minúsculas, dígitos y caracteres especiales de la contraseña que desea generar.");

            // 4. Servidor pregunta los requisitos de la contraseña
            salidaServidor.println("Número de mayúsculas:");
            int numMayusculas = Integer.parseInt(entradaCliente.readLine());

            salidaServidor.println("Número de minúsculas:");
            int numMinusculas = Integer.parseInt(entradaCliente.readLine());

            salidaServidor.println("Número de dígitos:");
            int numDigitos = Integer.parseInt(entradaCliente.readLine());

            salidaServidor.println("Número de caracteres especiales:");
            int numCaractEspeciales = Integer.parseInt(entradaCliente.readLine());

            
         // 5. Calcular la longitud de la contraseña
            RequisitosPass requisitos = new RequisitosPass(numMayusculas, numMinusculas, numDigitos, numCaractEspeciales);
            int longitudContraseña = ServicioPass.longitudContraseña(requisitos);
            salidaServidor.println("La longitud de la contraseña que se va a generar es: " + longitudContraseña);
            // 6. Servidor pregunta si se quiere generar una contraseña ahora
            salidaServidor.println("¿Desea generar una contraseña ahora? (Si/No):");
            String respuesta = entradaCliente.readLine();

            if (respuesta.equalsIgnoreCase("si")) {
                // Opción 1: Generar la contraseña y enviarla
            	String contraseñaGenerada = ServicioPass.generarContraseña(requisitos);
                salidaServidor.println("La contraseña generada es: " + contraseñaGenerada);
            } else {
                // Opción 2: Indicar que no se generará contraseña y despedirse
                salidaServidor.println("No se generará una contraseña. Adiós.");
            }

            // Cerrar la conexión con el cliente
            clienteSocket.close();
        } catch (IOException e) {
            System.err.println("Error al manejar las interacciones con el cliente: " + e.getMessage());
        }
    }
}
