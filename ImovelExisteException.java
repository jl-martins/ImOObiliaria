
/**
 * Exceção atirada quando um vendedor tenta registar um Imovel que já existe.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */
public class ImovelExisteException extends Exception
{
    public ImovelExisteException(String msg){
        super(msg);
    }
}
