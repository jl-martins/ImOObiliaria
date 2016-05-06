
/**
 * Enumeration class TipoMoradia - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum TipoMoradia
{
    ISOLADA, GEMINADA, BANDA, GAVETO;
    
    public static TipoMoradia fromString(String str) throws IllegalArgumentException{
        TipoMoradia tipo = null;
        
        if(str != null)
            tipo = valueOf(str.trim().toUpperCase());
        return tipo;
    }
}
