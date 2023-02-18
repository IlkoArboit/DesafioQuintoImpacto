package Servicios;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Entidades.Administrador;
import Repositorios.AdministradorRepository;
import Repositorios.AlumnoRepository;
import Repositorios.CursoRepository;
import Repositorios.ProfesorRepository;

@Service
public class AdministradorService {
    
    @Autowired
    AdministradorRepository AdministradorRepository;

    @Autowired
    ProfesorRepository ProfesorService;

    @Autowired
    AlumnoRepository AlumnoService;

    @Autowired
    CursoRepository CursoService;

    @Transactional
    public Administrador guardar(String nombre, String apellido){
        
        Administrador newAdmin = new Administrador();

        newAdmin.setNombre(nombre);
        newAdmin.setApellido(apellido);

        return AdministradorRepository.save(newAdmin);
    }    

}
