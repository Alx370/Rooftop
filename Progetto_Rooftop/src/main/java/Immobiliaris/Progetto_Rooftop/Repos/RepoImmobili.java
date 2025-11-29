package Immobiliaris.Progetto_Rooftop.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Immobiliaris.Progetto_Rooftop.Model.Immobile;

@Repository
public interface RepoImmobili extends JpaRepository<Immobile, Integer>{

}