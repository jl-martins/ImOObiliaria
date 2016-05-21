
/**
 * Exceção atirada quando um utilizador escolhe uma opção que não tem autorização para executar.
 * 
 * @author Grupo12 
 * @version 15/05/2016
 */
public class SemAutorizacaoException extends Exception
{
    public SemAutorizacaoException(String msg){
        super(msg);
    }
}
