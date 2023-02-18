package Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Entidades.Curso;
import Entidades.Profesor;

@Repository
public interface ProfesorService extends JpaRepository<Profesor, String> {

    @Query("SELECT a FROM profesor a WHERE a.id = :id")
    Optional<Profesor> findById(@Param("id") String id);

    @Query("SELECT p FROM Profesor p WHERE p.email = :email")
    Profesor findByEmail(@Param("email") String email);
    
    @Query("SELECT p FROM Profesor p WHERE p.apellido = :apellido AND p.nombre = :nombre")
    List<Profesor> findByApellidoAndNombre(@Param("apellido") String apellido, @Param("nombre") String nombre);
    
    @Query("SELECT p FROM Profesor p JOIN p.cursos c WHERE c = :curso AND p.turno = :turno")
    List<Profesor> findByCursoAndTurno(@Param("curso") Curso curso, @Param("turno") String turno);
}
