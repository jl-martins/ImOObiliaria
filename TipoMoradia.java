/**
 * Enumeração dos tipos de moradia.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */

import java.io.Serializable;

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
