
/**
 * Exceção atirada quando um comprador tenta entrar num leilão que já terminou.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */
public class LeilaoTerminadoException extends Exception
{
    public LeilaoTerminadoException(String msg){
        super(msg);
    }
}
