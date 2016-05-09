import java.io.Serializable;
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
    private long fimDoLeilao;
    /* referir estrutura que vai conter os compradores inscritos no leilao */

    private Leilao(){};

    public Leilao(Vendedor v){
        this.responsavelLeilao = v;
    }

    public void iniciaLeilao(Imovel im, int horas) throws SemAutorizacaoException{
        if(!this.responsavelLeilao.vendeImovel(im.getId()))
            throw new SemAutorizacaoException("Este utilizador nao tem autorização para iniciar o leilao");
        imovelEmLeilao = im;
        fimDoLeilao = System.currentTimeMillis() + 3600 * 1000 * horas;

    }
    public void adicionaComprador(String idComprador, double limite, double incrementos, double minutos) 
    throws LeilaoTerminadoException{

    }

    public Comprador encerraLeilao(){

    }    
}
