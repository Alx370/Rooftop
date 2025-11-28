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
        public Prezzi(BigDecimal min, BigDecimal max) {
            this.min = min;
            this.max = max;
        }
    }

    public Optional<Prezzi> findPrezziByQuartiere(String provincia, String comune, String quartiere, String statoPreferito) {
        String sql = "SELECT prezzo_min_mq, prezzo_max_mq FROM omi_zone WHERE provincia = ? AND comune = ? AND LOWER(descrizione_quartiere) LIKE LOWER(CONCAT('%', ?, '%')) AND codice_tipologia IN (19.00,20.00,21.00) ORDER BY CASE WHEN stato = ? THEN 0 ELSE 1 END LIMIT 1";
        return jdbcTemplate.query(sql, ps -> { ps.setString(1, provincia); ps.setString(2, comune); ps.setString(3, quartiere); ps.setString(4, statoPreferito); }, rs -> {
            if (rs.next()) {
                return Optional.of(new Prezzi(rs.getBigDecimal(1), rs.getBigDecimal(2)));
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
}
