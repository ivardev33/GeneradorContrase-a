

package servidor;

import java.util.Random;

public class ServicioPass {
    private static final String CARACTERES_ESPECIALES = "!@#$%^&*()_-+=.:?";
    
    // Método para generar la contraseña
    public static String generarContraseña(RequisitosPass requisitos) {
        StringBuilder contraseña = new StringBuilder();
        
        // Generar mayúsculas
        for (int i = 0; i < requisitos.getNumMayusculas(); i++) {
            contraseña.append(generarCaracterMayuscula());
        }
        
        // Generar minúsculas
        for (int i = 0; i < requisitos.getNumMinusculas(); i++) {
            contraseña.append(generarCaracterMinuscula());
        }
        
        // Generar dígitos
        for (int i = 0; i < requisitos.getNumDigitos(); i++) {
            contraseña.append(generarDigito());
        }
        
        // Generar caracteres especiales
        for (int i = 0; i < requisitos.getNumCaractEspeciales(); i++) {
            contraseña.append(generarCaracterEspecial());
        }
        
        // Barajar la contraseña para mezclar los caracteres
        return mezclarContraseña(contraseña.toString());
    }
    
    // Método para obtener la longitud de la contraseña
    public static int longitudContraseña(RequisitosPass requisitos) {
        return requisitos.getNumMayusculas() + requisitos.getNumMinusculas() +
                requisitos.getNumDigitos() + requisitos.getNumCaractEspeciales();
    }
    
    // Métodos privados para generar caracteres aleatorios
    private static char generarCaracterMayuscula() {
        Random rand = new Random();
        return (char) (rand.nextInt(26) + 'A');
    }
    
    private static char generarCaracterMinuscula() {
        Random rand = new Random();
        return (char) (rand.nextInt(26) + 'a');
    }
    
    private static char generarDigito() {
        Random rand = new Random();
        return (char) (rand.nextInt(10) + '0');
    }
    
    private static char generarCaracterEspecial() {
        Random rand = new Random();
        return CARACTERES_ESPECIALES.charAt(rand.nextInt(CARACTERES_ESPECIALES.length()));
    }
    
    private static String mezclarContraseña(String contraseña) {
        char[] contraseñaArray = contraseña.toCharArray();
        Random rand = new Random();
        
        for (int i = contraseñaArray.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            char temp = contraseñaArray[i];
            contraseñaArray[i] = contraseñaArray[j];
            contraseñaArray[j] = temp;
        }
        
        return new String(contraseñaArray);
    }
}
