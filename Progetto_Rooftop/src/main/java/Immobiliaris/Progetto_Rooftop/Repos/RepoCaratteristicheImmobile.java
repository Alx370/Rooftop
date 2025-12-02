package Immobiliaris.Progetto_Rooftop.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Immobiliaris.Progetto_Rooftop.Model.CaratteristicheImmobile;
import java.util.Optional;

@Repository
public interface RepoCaratteristicheImmobile extends JpaRepository<CaratteristicheImmobile, Integer>{
    @Query("SELECT c FROM CaratteristicheImmobile c WHERE c.immobile.id_immobile = :idImmobile")
    Optional<CaratteristicheImmobile> findByImmobileId(@Param("idImmobile") Integer idImmobile);
}
