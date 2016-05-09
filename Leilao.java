import java.io.Serializable;
/**
 * Write a description of class Leilao here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Leilao implements Serializable
{
    private String emailResponsavelLeilao;
    private String idImovelEmLeilao;
    private long fimDoLeilao;
    /* referir estrutura que vai conter os compradores inscritos no leilao */

//    private Leilao(){};

    public Leilao(String idVendedor){
        this.emailResponsavelLeilao = idVendedor;
    }

    public void iniciaLeilao(Imovel im, int horas) /*throws SemAutorizacaoException*/{
        /*if(!this.emailResponsavelLeilao.vendeImovel(im.getId()))
            throw new SemAutorizacaoException("Este utilizador nao tem autorização para iniciar o leilao");
        imovelEmLeilao = im.clone();*/
        fimDoLeilao = System.currentTimeMillis() + 3600 * 1000 * horas;

    }
    /*public void adicionaComprador(String idComprador, double limite, double incrementos, double minutos) 
    throws LeilaoTerminadoException{
        if(System.currentTimeMillis() > fimDoLeilao)
            throw new LeilaoTerminadoException("O leilão terminou, não pode adcionar mais compradores");

    }*/
    
    public void terminouLeilao() throws LeilaoTerminadoException{
        if(System.currentTimeMillis() > fimDoLeilao)
            throw new LeilaoTerminadoException("O leilão terminou, não pode adcionar mais compradores");

    }

    public String encerraLeilao(){

    }    
}
