import java.io.Serializable;
/**
 * Enumeration class EstadoImovel - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum EstadoImovel implements Serializable
{
    EM_VENDA, RESERVADO, VENDIDO;
    
    public static EstadoImovel fromString(String str) throws EstadoInvalidoException {
        EstadoImovel estado = null;
        try {
            if(str != null)
                estado = valueOf(str.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new EstadoInvalidoException(str + " não é um estado válido.");
            }
        return estado;
    }
}
