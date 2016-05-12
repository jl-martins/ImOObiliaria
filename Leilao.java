import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Write a description of class Leilao here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Leilao implements Serializable /* implementar Comparable se for para fazer varios leiloes */
{
    private String responsavel;
    private String imovelEmLeilao;
    private Set<Licitador> licitadores; /* usar uma heap? */
    private int duracao;
    private long inicioLeilao;

    private Leilao(){}

    public Leilao(String imovelEmLeilao, String responsavel, int duracao){
        this.responsavel = responsavel;
        this.imovelEmLeilao = imovelEmLeilao;
        this.duracao = duracao;
        this.licitadores = new TreeSet<>();
        this.inicioLeilao = System.currentTimeMillis();
    };

    public String getResponsavel(){
        return responsavel;
    }

    /* o id do comprador corresponde ao seu mail*/
    public void registaCompradorLeilao(String idComprador, int limite, int incrementos, int minutos) 
    throws LeilaoTerminadoException{
        if(this.terminouLeilao())
            throw new LeilaoTerminadoException("Não pode adicionar mais vendedores, o leilão terminou.");
        int minutosDesdeInicioLeilao = (int) ((System.currentTimeMillis() - inicioLeilao)/60000);
        Licitador novoLicitador = new Licitador(idComprador, limite, incrementos, minutos, minutosDesdeInicioLeilao);
        licitadores.add(novoLicitador);
    }

    public boolean terminouLeilao(){
        long fimDoLeilao = inicioLeilao + duracao * 60 * 60 * 1000;
        return System.currentTimeMillis() >= fimDoLeilao;
    }

    public String simulaLeilao(){
        /* acabar isto */
        return "";
    }

    public String encerraLeilao(){
        return simulaLeilao();
    }
}
