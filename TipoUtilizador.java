import java.io.Serializable;
/**
 * Enumeration class TipoUtilizador - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum TipoUtilizador implements Serializable
{
    COMPRADOR, VENDEDOR;
    
    public static TipoUtilizador fromString(String str) throws IllegalArgumentException{
        TipoUtilizador tipo = null;
        
        if(str != null)
            tipo = valueOf(str.trim().toUpperCase());
        return tipo;
    }
}
