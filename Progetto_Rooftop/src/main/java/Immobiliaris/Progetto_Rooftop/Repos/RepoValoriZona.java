package Immobiliaris.Progetto_Rooftop.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Immobiliaris.Progetto_Rooftop.Model.ValoriZona;
import Immobiliaris.Progetto_Rooftop.Model.ZonaProvinciaTorino;

public interface RepoValoriZona extends JpaRepository<ValoriZona, Long> {
    Optional<ValoriZona> findByZona(ZonaProvinciaTorino zona);
    Optional<ValoriZona> findByProvinciaAndZona(String provincia, ZonaProvinciaTorino zona);
    boolean existsByProvinciaAndZona(String provincia, ZonaProvinciaTorino zona);
    java.util.List<ValoriZona> findAllByProvinciaOrderByZonaAsc(String provincia);
}
