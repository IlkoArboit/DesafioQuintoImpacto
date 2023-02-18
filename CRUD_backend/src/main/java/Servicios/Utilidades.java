package Servicios;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Entidades.Administrador;
import Entidades.Alumno;
import Entidades.Profesor;
import Repositorios.AdministradorRepository;
import Repositorios.AlumnoRepository;
import Repositorios.ProfesorRepository;

@Service
public class Utilidades {

    @Autowired
    private static AlumnoRepository alumnoRepository;
    
    @Autowired
    private static ProfesorRepository profesorRepository;
    
    @Autowired
    private static AdministradorRepository administradorRepository;

    private static final String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Random RANDOM = new SecureRandom();

    public static String generarEmail(String nombre, String apellido, String tipoUsuario) {
        String email = (nombre.charAt(0) + apellido).toLowerCase() + "@" + tipoUsuario.toLowerCase() + ".universidad.com";
        String letraNombre = "";
        int contador = 1;

        while (emailRepetido(email)) {
            if (nombre.length() > contador) {
                letraNombre = Character.toString(nombre.charAt(contador));
                email = (nombre.charAt(0) + letraNombre + apellido).toLowerCase() + "@" + tipoUsuario.toLowerCase() + ".universidad.com";
                contador++;
            } else {
                email = (nombre + contador + apellido).toLowerCase() + "@" + tipoUsuario.toLowerCase() + ".universidad.com";
                contador++;
            }
        }
        return email;
    }

    public static boolean emailRepetido(String email) {
        Alumno alumno = alumnoRepository.findByEmail(email);
        Profesor profesor = profesorRepository.findByEmail(email);
        Administrador administrador = administradorRepository.findByEmail(email);

        if (alumno != null || profesor != null || administrador != null) {
            return true;
        } else {
            return false;
        }
    }

    public static String generarContrasena(int longitud) {
        StringBuilder sb = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            sb.append(CARACTERES.charAt(RANDOM.nextInt(CARACTERES.length())));
        }
        return sb.toString();
    }
}

