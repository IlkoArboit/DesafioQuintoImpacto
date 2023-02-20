package Servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import Entidades.Curso;
import Repositorios.CursoRepository;

public class CursoService {

    @Autowired
    private CursoRepository CursoRepository;

    public Curso findById(String id){

        return CursoRepository.findById(id).get();

    }
    
}
