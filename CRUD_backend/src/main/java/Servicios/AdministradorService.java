package Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Entidades.Administrador;
import Entidades.Alumno;
import Entidades.Profesor;
import Repositorios.AdministradorRepository;

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
    Repositorios.CursoRepository CursoRepository;

    @Autowired
    Utilidades util;

    @Transactional
    public Administrador crear(String nombre, String apellido){
        
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
    public Profesor guardarProfesor(String nombre, String apellido, int dni) throws Exception{
        return ProfesorService.crear(nombre, apellido, dni);
    }

    @Transactional
    public Alumno guardarAlumno(String nombre, String apellido, int dni) throws Exception{
        return AlumnoService.crear(nombre, apellido, dni);
    }

}
