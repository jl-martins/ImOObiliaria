
/**
 * Exceção atirada quando um vendedor pretende alterar o estado de um Imovel para um estado inválido.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */
public class EstadoInvalidoException extends Exception
{
    public EstadoInvalidoException(String msg){
        super(msg);
    }
}
