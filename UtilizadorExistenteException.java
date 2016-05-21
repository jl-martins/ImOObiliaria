
/**
 * Exceção atirada quando os dados de um utilizador não correspondem a um utilizador registado.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */
public class UtilizadorExistenteException extends Exception
{
    public UtilizadorExistenteException(String msg){
        super(msg);
    }
}
