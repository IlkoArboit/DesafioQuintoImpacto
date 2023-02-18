package Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Entidades.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, String> {

    @Query("SELECT a FROM Administrador a WHERE a.id = :id")
    Optional<Administrador> findById(@Param("id") String id);
}
