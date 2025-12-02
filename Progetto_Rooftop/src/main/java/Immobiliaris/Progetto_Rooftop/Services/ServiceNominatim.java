package Immobiliaris.Progetto_Rooftop.Services;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
/**
 * Integrazione con Nominatim (OpenStreetMap) per risolvere quartieri/suburb e città.
 * Utilizza una semplice chiamata HTTP con `User-Agent` dedicato.
 */
public class ServiceNominatim {

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Classe che contiene i risultati della risoluzione Nominatim.
     */
    public static class RispostaNominatim {
        public final String quartiere;
        public final String citta;
        public final Double latitudine;
        public final Double longitudine;
        
        public RispostaNominatim(String quartiere, String citta, Double latitudine, Double longitudine) {
            this.quartiere = quartiere;
            this.citta = citta;
            this.latitudine = latitudine;
            this.longitudine = longitudine;
        }
    }

    /**
     * Risolve quartiere e città a partire da indirizzo e CAP.
     * Il CAP italiano identifica univocamente la città (es. 10151 = Torino).
     * 
     * @param indirizzo via/piazza (es. "Via Roma")
     * @param civico numero civico (opzionale)
     * @param cap codice postale (es. "10151")
     * @param provincia sigla provincia per contesto (es. "TO")
     * @return oggetto con quartiere e città, oppure null se non trovato
     */
    public RispostaNominatim resolveIndirizzo(String indirizzo, String civico, String cap, String provincia) {
        // Costruisce la query: "Via Roma 10, 10151, TO, Italia"
        StringBuilder qBuilder = new StringBuilder();
        qBuilder.append(indirizzo);
        if (civico != null && !civico.isBlank()) {
            qBuilder.append(" ").append(civico);
        }
        qBuilder.append(", ").append(cap);
        if (provincia != null && !provincia.isBlank()) {
            qBuilder.append(", ").append(provincia);
        }
        qBuilder.append(", Italia");
        
        String url = UriComponentsBuilder.fromHttpUrl("https://nominatim.openstreetmap.org/search")
                .queryParam("q", qBuilder.toString())
                .queryParam("format", "json")
                .queryParam("addressdetails", "1")
                .queryParam("limit", "1")
                .build()
                .toUriString();
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Immobiliaris/1.0");
        ResponseEntity<String> resp = rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        
        try {
            List<?> list = mapper.readValue(resp.getBody(), List.class);
            if (list.isEmpty()) return null;
            
            Object first = list.get(0);
            Map<?,?> obj = (Map<?,?>) first;
            Map<?,?> addr = (Map<?,?>) obj.get("address");
            if (addr == null) return null;
            
            // Estrae latitudine e longitudine
            Double lat = null;
            Double lon = null;
            Object latObj = obj.get("lat");
            Object lonObj = obj.get("lon");
            if (latObj != null) lat = Double.parseDouble(latObj.toString());
            if (lonObj != null) lon = Double.parseDouble(lonObj.toString());
            
            // Estrae il quartiere (prova vari campi in ordine di priorità)
            String quartiere = null;
            Object n = addr.get("neighbourhood");
            if (n == null) n = addr.get("suburb");
            if (n == null) n = addr.get("quarter");
            if (n == null) n = addr.get("city_district");
            if (n != null) quartiere = n.toString();
            
            // Estrae la città (prova vari campi)
            String citta = null;
            Object c = addr.get("city");
            if (c == null) c = addr.get("town");
            if (c == null) c = addr.get("municipality");
            if (c == null) c = addr.get("village");
            if (c != null) citta = c.toString().toUpperCase(); // Uppercase per match con DB OMI
            
            return new RispostaNominatim(quartiere, citta, lat, lon);
        } catch (Exception e) {
            return null;
        }
    }
}
