package Immobiliaris.Progetto_Rooftop.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Immobiliaris.Progetto_Rooftop.Model.ValutazioneZona;
import Immobiliaris.Progetto_Rooftop.Model.ZonaProvinciaTorino;

public interface RepoValutazioneZona extends JpaRepository<ValutazioneZona, Long> {
    Optional<ValutazioneZona> findByZona(ZonaProvinciaTorino zona);
    Optional<ValutazioneZona> findByProvinciaAndZona(String provincia, ZonaProvinciaTorino zona);
    boolean existsByProvinciaAndZona(String provincia, ZonaProvinciaTorino zona);
    java.util.List<ValutazioneZona> findAllByProvinciaOrderByZonaAsc(String provincia);
}
