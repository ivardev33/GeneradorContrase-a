package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    private static final String DIRECCION_SERVIDOR = "localhost";
    private static final int PUERTO = 4321;
    private Socket socket;

    public void conectar() {
        try {
            socket = new Socket(DIRECCION_SERVIDOR, PUERTO);
            System.out.println("Conectado al servidor en " + DIRECCION_SERVIDOR + ":" + PUERTO);

            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salidaCliente = new PrintWriter(socket.getOutputStream(), true);

            // Escuchar mensajes del servidor y responder
            String mensajeServidor;
            BufferedReader entradaUsuario = new BufferedReader(new InputStreamReader(System.in));

            while ((mensajeServidor = entradaServidor.readLine()) != null) {
                System.out.println("Mensaje del servidor: " + mensajeServidor);

                // Si el servidor pregunta algo, el cliente responde
                if (mensajeServidor.endsWith(":")) {
                    String respuestaUsuario = entradaUsuario.readLine();
                    salidaCliente.println(respuestaUsuario);
                }
            }

            // Cerrar conexión con el servidor
            socket.close();
        } catch (IOException e) {
            System.err.println("Error al conectar al servidor: " + e.getMessage());
        }
    }
}
