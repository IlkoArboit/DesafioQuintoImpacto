package Servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Entidades.Administrador;
import Entidades.Alumno;
import Entidades.Profesor;
import Repositorios.AdministradorRepository;
import Repositorios.AlumnoRepository;
import Repositorios.CursoRepository;
import Repositorios.ProfesorRepository;

@Service
public class AdministradorService {
    
    @Autowired
    AdministradorRepository AdministradorRepository;

    @Autowired
    ProfesorService ProfesorService;

    @Autowired
    AlumnoService AlumnoService;

    @Autowired
    CursoService CursoService;

    @Autowired
    Utilidades util;

    @Transactional
    public Administrador guardar(String nombre, String apellido){
        
        Administrador newAdmin = new Administrador();

        newAdmin.setNombre(nombre);
        newAdmin.setApellido(apellido);
        newAdmin.setEmail(Utilidades.generarEmail(nombre, apellido, "administradores"));
        newAdmin.setContrasenia(Utilidades.generarContrasena(12));

        return AdministradorRepository.save(newAdmin);
    }

    @Transactional
    public Administrador modificar(String id, String nombre, String apellido) throws Exception{

        Administrador modAdmin = AdministradorRepository.findById(id).get();

        if(modAdmin != null){
            modAdmin.setNombre(nombre);
            modAdmin.setApellido(apellido);
            modAdmin.setEmail(Utilidades.generarEmail(nombre, apellido, "administradores"));
            return AdministradorRepository.save(modAdmin);
        } else {
            throw new Exception("El Administrador no se encuentra en la base de datos");
        }
    }

    @Transactional
    public void eliminar(String id) throws Exception{
        
        Administrador delAdmin = AdministradorRepository.findById(id).get();

        if(delAdmin != null){
            AdministradorRepository.delete(delAdmin);
        } else {
            throw new Exception("El Administrador no se encuentra en la base de datos");
        }
    }

    @Transactional
    public Profesor guardarProfesor(String nombre, String apellido){
        return ProfesorService.guardar(nombre, apellido);

    }
    @Transactional
    public Alumno guardarAlumno(String nombre, String apellido){
        return AlumnoService.guardar(nombre, apellido);

    }
    @Transactional
    public Profesor guardarCurso(String nombre, String apellido){
        return CursoService.guardar(nombre, apellido);

    }
}
