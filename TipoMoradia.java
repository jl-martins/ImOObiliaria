import java.io.Serializable;
/**
 * Enumeration class TipoMoradia - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum TipoMoradia
{
    ISOLADA, GEMINADA, BANDA, GAVETO;
    
    public static TipoMoradia fromString(String str) throws TipoInvalidoException{
        TipoMoradia tipo = null;
        
        try{
            if(str != null)
                tipo = valueOf(str.trim().toUpperCase());
        }
        catch(IllegalArgumentException e){throw new TipoInvalidoException("O tipo '" + str + "' é inválido.");}
        
        return tipo;
    }
}
