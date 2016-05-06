
/**
 * Enumeration class EstadoImovel - write a description of the enum class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
public enum EstadoImovel
{
    EM_VENDA, RESERVADO, VENDIDO;
    
    public static EstadoImovel fromString(String str) throws IllegalArgumentException{
        EstadoImovel estado = null;
        
        if(str != null)
            estado = valueOf(str.trim().toUpperCase());
        return estado;
    }
}
