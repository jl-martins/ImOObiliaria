import java.io.Serializable;
import java.util.Set;
import Java.util.TreeSet;

/**
 * Write a description of class Leilao here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Leilao implements Serializable /* implementar Comparable se for para fazer varios leiloes */
{
    private String emailResponsavelLeilao;
    private String idImovelEmLeilao;
    private Set<Licitador> licitadores;
    private boolean terminou; 

    /* referir estrutura que vai conter os compradores inscritos no leilao */
    //    private Leilao(){};
    public Leilao(String idVendedor){
        this.emailResponsavelLeilao = idVendedor;
        licitadores = new TreeSet<Licitador>();
    }

    public void iniciaLeilao(Imovel im, int horas) /*throws SemAutorizacaoException*/{

    }

    public void adicionaCompradorLeilao(String idComprador, int limite, int incrementos, int minutos) 
    throws LeilaoTerminadoException{
        if(terminou)
            throw new LeilaoTerminadoException("Não pode adicionar mais vendedores, o leilão terminou.");

        Licitador novoLicitador = new Licitador(idComprador, limite, incrementos, minutos);
        

    }

    public boolean terminouLeilao(){
        return terminou;
    }

    public String simulaVencedor(){

    }

    public String encerraLeilao(){

    }
}
