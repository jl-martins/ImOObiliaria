import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

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
    private List<Licitador> licitadores; /* vai ter a lista de todos os licitadores por ordem de registo no leilao */
    private int duracao;
    private long inicioLeilao;

    private Leilao(){}

    public Leilao(String imovelEmLeilao, String responsavel, int duracao){
        this.responsavel = responsavel;
        this.imovelEmLeilao = imovelEmLeilao;
        this.duracao = duracao;
        this.licitadores = new ArrayList<>();
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
        int duracaoMinutos = 60 * duracao;
        int precoAtual = 0;
        int proxI;
        Licitador aGanhar = null;
        /*copiar estado antes de executar para ser possivel fazer simulaçoes nao-destrutivas dos leiloes*/
        List<Licitador> copiaLicitadores = new ArrayList<>(licitadores);

        for(int i = 0; i <= duracaoMinutos; i = proxI){
            proxI = duracaoMinutos + 1;
            for(Licitador l : copiaLicitadores){
                int minutoProxLicitacao = l.getQuandoProximaLicitacao();

                if(minutoProxLicitacao == i){
                    if(l == aGanhar){
                        l.atualizaQuandoProxIncremento();
                    }
                    else if(l.getLimite() <= precoAtual){ 
                        copiaLicitadores.remove(l);                        
                    }    
                    else{
                        precoAtual = l.setMenorLicitacaoQuePasse(precoAtual);
                        aGanhar = l;
                        l.atualizaQuandoProxIncremento();
                        l.atualizaValorProxIncremento();
                    }
                } else if(minutoProxLicitacao < proxI){
                    proxI = i;
                }
            }
        }
        /* fazer reset a todos os licitadores */
        for(Licitador l : licitadores)
            l.reset();
        return (aGanhar == null)? null : aGanhar.getIdComprador();
    }
}
