
/**
 * Exceção atirada quando um utilizador seleciona um Imovel inexistente.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */
public class ImovelInexistenteException extends Exception
{
    public ImovelInexistenteException(String msg){
        super(msg);
    }
}
