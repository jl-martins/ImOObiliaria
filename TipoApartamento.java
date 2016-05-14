import java.io.Serializable;
/**
 * Enumeration class TipoApartamento - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum TipoApartamento implements Serializable
{
    SIMPLES, DUPLEX, TRIPLEX;
    
    public static TipoApartamento fromString(String str) throws TipoInvalidoException{
        TipoApartamento tipo = null;
        
        try{
            if(str != null)
                tipo = valueOf(str.trim().toUpperCase());
        }
        catch(IllegalArgumentException e){throw new TipoInvalidoException("O tipo de apartamento '" + str + "' é inválido!");}    
        
        return tipo;
    }
}
