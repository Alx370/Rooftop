package Immobiliaris.Progetto_Rooftop.Services;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ServiceOmi {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static class Prezzi {
        public final BigDecimal min;
        public final BigDecimal max;
        public final String zona;
        public Prezzi(BigDecimal min, BigDecimal max, String zona) {
            this.min = min;
            this.max = max;
            this.zona = zona;
        }
    }

    public Optional<Prezzi> findPrezziByQuartiere(String provincia, String comune, String quartiere, String statoPreferito) {
        String sql = "SELECT zona, prezzo_min_mq, prezzo_max_mq FROM omi_zone WHERE provincia = ? AND comune = ? AND LOWER(descrizione_quartiere) LIKE LOWER(CONCAT('%', ?, '%')) AND codice_tipologia IN (19.00,20.00,21.00) ORDER BY CASE WHEN stato = ? THEN 0 ELSE 1 END LIMIT 1";
        return jdbcTemplate.query(sql, ps -> { ps.setString(1, provincia); ps.setString(2, comune); ps.setString(3, quartiere); ps.setString(4, statoPreferito); }, rs -> {
            if (rs.next()) {
                return Optional.of(new Prezzi(rs.getBigDecimal(2), rs.getBigDecimal(3), rs.getString(1)));
            } else {
                return Optional.empty();
            }
        });
    }

    public Optional<Prezzi> findPrezziByQuartiereTipologia(String provincia, String comune, String quartiere, String statoPreferito, BigDecimal codiceTipologia) {
        String sql = "SELECT zona, prezzo_min_mq, prezzo_max_mq FROM omi_zone WHERE provincia = ? AND comune = ? AND LOWER(descrizione_quartiere) LIKE LOWER(CONCAT('%', ?, '%')) AND codice_tipologia = ? ORDER BY CASE WHEN stato = ? THEN 0 ELSE 1 END LIMIT 1";
        return jdbcTemplate.query(sql, ps -> { ps.setString(1, provincia); ps.setString(2, comune); ps.setString(3, quartiere); ps.setBigDecimal(4, codiceTipologia); ps.setString(5, statoPreferito); }, rs -> {
            if (rs.next()) {
                return Optional.of(new Prezzi(rs.getBigDecimal(2), rs.getBigDecimal(3), rs.getString(1)));
            } else {
                return Optional.empty();
            }
        });
    }

    public Optional<BigDecimal> findPrezzoMedioByComune(String provincia, String comune, String statoPreferito) {
        String sql = "SELECT AVG((prezzo_min_mq + prezzo_max_mq)/2) FROM omi_zone WHERE provincia = ? AND comune = ? AND codice_tipologia IN (19.00,20.00,21.00) AND stato = ?";
        BigDecimal val = jdbcTemplate.query(sql, ps -> { ps.setString(1, provincia); ps.setString(2, comune); ps.setString(3, statoPreferito); }, rs -> {
            if (rs.next()) return rs.getBigDecimal(1); else return null;
        });
        if (val == null) {
            String sql2 = "SELECT AVG((prezzo_min_mq + prezzo_max_mq)/2) FROM omi_zone WHERE provincia = ? AND comune = ? AND codice_tipologia IN (19.00,20.00,21.00) AND stato = 'NORMALE'";
            val = jdbcTemplate.query(sql2, ps -> { ps.setString(1, provincia); ps.setString(2, comune); }, rs -> { if (rs.next()) return rs.getBigDecimal(1); else return null; });
        }
        return Optional.ofNullable(val);
    }

    public Optional<BigDecimal> findPrezzoMedioByComuneTipologia(String provincia, String comune, String statoPreferito, BigDecimal codiceTipologia) {
        String sql = "SELECT AVG((prezzo_min_mq + prezzo_max_mq)/2) FROM omi_zone WHERE provincia = ? AND comune = ? AND codice_tipologia = ? AND stato = ?";
        BigDecimal val = jdbcTemplate.query(sql, ps -> { ps.setString(1, provincia); ps.setString(2, comune); ps.setBigDecimal(3, codiceTipologia); ps.setString(4, statoPreferito); }, rs -> {
            if (rs.next()) return rs.getBigDecimal(1); else return null;
        });
        if (val == null) {
            String sql2 = "SELECT AVG((prezzo_min_mq + prezzo_max_mq)/2) FROM omi_zone WHERE provincia = ? AND comune = ? AND codice_tipologia = ? AND stato = 'NORMALE'";
            val = jdbcTemplate.query(sql2, ps -> { ps.setString(1, provincia); ps.setString(2, comune); ps.setBigDecimal(3, codiceTipologia); }, rs -> { if (rs.next()) return rs.getBigDecimal(1); else return null; });
        }
        return Optional.ofNullable(val);
    }

    /**
     * Cerca la zona OMI tramite coordinate UTM.
     * Verifica se il punto (utmX, utmY) è contenuto nel poligono GeoJSON della zona.
     * 
     * Il poligono è memorizzato in formato GeoJSON con coordinate UTM 32N (EPSG:32632).
     * 
     * @param provincia sigla provincia (per velocizzare la query)
     * @param utmX coordinata Est in metri (UTM 32N)
     * @param utmY coordinata Nord in metri (UTM 32N)
     * @param statoPreferito stato preferito (NORMALE/OTTIMO)
     * @param codiceTipologia codice tipologia immobile
     * @return Optional con zona, prezzo min e max, quartiere trovato
     */
    public Optional<PrezziConQuartiere> findPrezziByCoordinate(String provincia, Double utmX, Double utmY, String statoPreferito, BigDecimal codiceTipologia) {
        if (utmX == null || utmY == null) return Optional.empty();
        
        // Carica tutte le zone OMI con poligono per la provincia/tipologia
        // Poi verifica in Java quale poligono contiene il punto UTM
        String sql = """
            SELECT zona, prezzo_min_mq, prezzo_max_mq, descrizione_quartiere, poligono, stato
            FROM omi_zone 
            WHERE provincia = ? 
              AND codice_tipologia = ?
              AND poligono IS NOT NULL
            """;
        
        return jdbcTemplate.query(sql, ps -> { 
            ps.setString(1, provincia); 
            ps.setBigDecimal(2, codiceTipologia);
        }, rs -> {
            PrezziConQuartiere risultato = null;
            boolean trovatoConStatoPreferito = false;
            
            while (rs.next()) {
                String poligonoJson = rs.getString(5);
                if (isPointInPolygon(utmX, utmY, poligonoJson)) {
                    String statoCorrente = rs.getString(6);
                    boolean isStatoPreferito = statoPreferito.equals(statoCorrente);
                    
                    // Preferisci lo stato desiderato, altrimenti prendi il primo match
                    if (isStatoPreferito || !trovatoConStatoPreferito) {
                        risultato = new PrezziConQuartiere(
                            rs.getBigDecimal(2), 
                            rs.getBigDecimal(3), 
                            rs.getString(1),
                            rs.getString(4)
                        );
                        if (isStatoPreferito) {
                            trovatoConStatoPreferito = true;
                        }
                    }
                }
            }
            return Optional.ofNullable(risultato);
        });
    }

    /**
     * Cerca la zona OMI tramite coordinate UTM (senza filtrare per tipologia).
     */
    public Optional<PrezziConQuartiere> findPrezziByCoordinateSenzaTipologia(String provincia, Double utmX, Double utmY, String statoPreferito) {
        if (utmX == null || utmY == null) return Optional.empty();
        
        String sql = """
            SELECT zona, prezzo_min_mq, prezzo_max_mq, descrizione_quartiere, poligono, stato
            FROM omi_zone 
            WHERE provincia = ? 
              AND codice_tipologia IN (19.00, 20.00, 21.00)
              AND poligono IS NOT NULL
            """;
        
        return jdbcTemplate.query(sql, ps -> { 
            ps.setString(1, provincia); 
        }, rs -> {
            PrezziConQuartiere risultato = null;
            boolean trovatoConStatoPreferito = false;
            
            while (rs.next()) {
                String poligonoJson = rs.getString(5);
                if (isPointInPolygon(utmX, utmY, poligonoJson)) {
                    String statoCorrente = rs.getString(6);
                    boolean isStatoPreferito = statoPreferito.equals(statoCorrente);
                    
                    if (isStatoPreferito || !trovatoConStatoPreferito) {
                        risultato = new PrezziConQuartiere(
                            rs.getBigDecimal(2), 
                            rs.getBigDecimal(3), 
                            rs.getString(1),
                            rs.getString(4)
                        );
                        if (isStatoPreferito) {
                            trovatoConStatoPreferito = true;
                        }
                    }
                }
            }
            return Optional.ofNullable(risultato);
        });
    }
    
    /**
     * Verifica se un punto (x,y) è all'interno di un poligono GeoJSON.
     * Usa l'algoritmo ray-casting.
     */
    private boolean isPointInPolygon(double x, double y, String geoJson) {
        if (geoJson == null || geoJson.isBlank()) return false;
        
        try {
            // Parse semplice del GeoJSON per estrarre le coordinate
            // Formato atteso: {"type":"Polygon","coordinates":[[[x1,y1],[x2,y2],...,[x1,y1]]]}
            int coordStart = geoJson.indexOf("\"coordinates\"");
            if (coordStart == -1) return false;
            
            // Estrai la lista di coordinate
            int bracketStart = geoJson.indexOf("[[[", coordStart);
            int bracketEnd = geoJson.lastIndexOf("]]]");
            if (bracketStart == -1 || bracketEnd == -1) return false;
            
            String coords = geoJson.substring(bracketStart + 3, bracketEnd);
            String[] points = coords.split("\\],\\[");
            
            double[] polyX = new double[points.length];
            double[] polyY = new double[points.length];
            
            for (int i = 0; i < points.length; i++) {
                String point = points[i].replaceAll("[\\[\\]]", "");
                String[] xy = point.split(",");
                polyX[i] = Double.parseDouble(xy[0].trim());
                polyY[i] = Double.parseDouble(xy[1].trim());
            }
            
            // Algoritmo ray-casting
            boolean inside = false;
            int n = polyX.length;
            for (int i = 0, j = n - 1; i < n; j = i++) {
                if ((polyY[i] > y) != (polyY[j] > y) &&
                    (x < (polyX[j] - polyX[i]) * (y - polyY[i]) / (polyY[j] - polyY[i]) + polyX[i])) {
                    inside = !inside;
                }
            }
            return inside;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Classe estesa che include anche il quartiere trovato.
     */
    public static class PrezziConQuartiere {
        public final BigDecimal min;
        public final BigDecimal max;
        public final String zona;
        public final String quartiere;
        
        public PrezziConQuartiere(BigDecimal min, BigDecimal max, String zona, String quartiere) {
            this.min = min;
            this.max = max;
            this.zona = zona;
            this.quartiere = quartiere;
        }
    }
}