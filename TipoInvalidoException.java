
/**
 * Exceção atirada quando uma String introduzida pelo utilizador não corresponde a um tipo válido de moradia ou apartamento.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */
public class TipoInvalidoException extends Exception
{
    public TipoInvalidoException(String msg){
        super(msg);
    }
}
