package Immobiliaris.Progetto_Rooftop.Model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter JPA per persistere {@link ZonaProvinciaTorino} come stringa leggibile (displayName)
 * nel database e riconvertirla in enum in lettura.
 * Gestisce sia displayName sia eventuali valori salvati come enum name per retrocompatibilità.
 */
@Converter(autoApply = false)
public class ZonaProvinciaTorinoConverter implements AttributeConverter<ZonaProvinciaTorino, String> {

    @Override
    public String convertToDatabaseColumn(ZonaProvinciaTorino attribute) {
        return attribute == null ? null : attribute.getDisplayName();
    }

    @Override
    public ZonaProvinciaTorino convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        String value = dbData.trim();
        // Prima prova a mappare per displayName (case-insensitive)
        for (ZonaProvinciaTorino z : ZonaProvinciaTorino.values()) {
            if (z.getDisplayName().equalsIgnoreCase(value)) {
                return z;
            }
        }
        // Fallback: prova a mappare per enum name (case-insensitive)
        try {
            return ZonaProvinciaTorino.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Valore zona non valido: " + dbData);
        }
    }
}