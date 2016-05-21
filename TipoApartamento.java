/**
 * Enumeração dos tipos de apartamento.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */

import java.io.Serializable;

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
