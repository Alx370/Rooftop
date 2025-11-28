package Immobiliaris.Progetto_Rooftop.Services;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceNominatim {

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public String resolveQuartiere(String provincia, String citta, String indirizzo, String civico) {
        String q = indirizzo + (civico != null ? " " + civico : "") + ", " + citta + ", " + provincia + ", Italia";
        String url = UriComponentsBuilder.fromHttpUrl("https://nominatim.openstreetmap.org/search")
                .queryParam("q", q)
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
            Object n = addr.get("neighbourhood");
            if (n == null) n = addr.get("suburb");
            if (n == null) n = addr.get("quarter");
            if (n == null) n = addr.get("city_district");
            return n != null ? n.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
