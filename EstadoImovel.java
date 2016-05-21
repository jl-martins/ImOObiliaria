/**
 * Enumeração dos estados de um Imovel.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */

import java.io.Serializable;

public enum EstadoImovel implements Serializable
{
    EM_VENDA, RESERVADO, VENDIDO;
    
    public static EstadoImovel fromString(String str) throws EstadoInvalidoException {
        EstadoImovel estado = null;
        try {
            if(str != null)
                estado = valueOf(str.trim().toUpperCase());
        }
        catch (IllegalArgumentException e) {throw new EstadoInvalidoException("O estado '" + str + "' é inválido.");}
        
        return estado;
    }
}
