package Immobiliaris.Progetto_Rooftop.Services;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;

/**
 * Servizio per la conversione di coordinate geografiche.
 * Converte da WGS84 (lat/lon usato da Nominatim) a UTM 32N (usato nei poligoni OMI).
 */
@Service
public class ServiceCoordinate {

    private final CoordinateTransform wgs84ToUtm32n;
    private final CoordinateTransform utm32nToWgs84;

    public ServiceCoordinate() {
        CRSFactory crsFactory = new CRSFactory();
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();

        // WGS84 - Sistema usato da GPS e Nominatim (lat/lon in gradi)
        CoordinateReferenceSystem wgs84 = crsFactory.createFromName("EPSG:4326");
        
        // UTM zona 32N - Sistema usato nei dati OMI italiani (metri)
        CoordinateReferenceSystem utm32n = crsFactory.createFromName("EPSG:32632");

        this.wgs84ToUtm32n = ctFactory.createTransform(wgs84, utm32n);
        this.utm32nToWgs84 = ctFactory.createTransform(utm32n, wgs84);
    }

    /**
     * Converte coordinate da WGS84 (lat/lon) a UTM 32N (x/y in metri).
     * 
     * @param latitudine latitudine in gradi decimali (es. 45.0703)
     * @param longitudine longitudine in gradi decimali (es. 7.6869)
     * @return array [x, y] in metri UTM 32N
     */
    public double[] wgs84ToUtm(double latitudine, double longitudine) {
        ProjCoordinate src = new ProjCoordinate(longitudine, latitudine); // lon, lat
        ProjCoordinate dst = new ProjCoordinate();
        wgs84ToUtm32n.transform(src, dst);
        return new double[] { dst.x, dst.y };
    }

    /**
     * Converte coordinate da UTM 32N (x/y in metri) a WGS84 (lat/lon).
     * 
     * @param x coordinata Est in metri
     * @param y coordinata Nord in metri
     * @return array [latitudine, longitudine] in gradi decimali
     */
    public double[] utmToWgs84(double x, double y) {
        ProjCoordinate src = new ProjCoordinate(x, y);
        ProjCoordinate dst = new ProjCoordinate();
        utm32nToWgs84.transform(src, dst);
        return new double[] { dst.y, dst.x }; // lat, lon
    }

    /**
     * Classe per coordinate UTM.
     */
    public static class CoordinateUtm {
        public final double x;
        public final double y;

        public CoordinateUtm(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Converte e restituisce un oggetto CoordinateUtm.
     */
    public CoordinateUtm toUtm(double latitudine, double longitudine) {
        double[] utm = wgs84ToUtm(latitudine, longitudine);
        return new CoordinateUtm(utm[0], utm[1]);
    }
}
