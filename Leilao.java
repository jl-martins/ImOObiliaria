
/**
 * Write a description of class Leilao here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Leilao implements Serializable
{
    private Vendedor responsavelLeilao;
    private Imovel imovelEmLeilao;
    
    public void iniciaLeilao(Imovel im, int horas) throws SemAutorizacaoException{
        
    }

    public void adicionaComprador(String idComprador, double limite, double incrementos, double minutos) 
    throws LeilaoTerminadoException{

    }

    public Comprador encerraLeilao(){

    }    
}
