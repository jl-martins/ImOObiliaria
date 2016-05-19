/**
 * Classe que representa um leilão.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Random;
import java.lang.Math;

public class Leilao implements Serializable
{
    private String responsavel; // id do vendedor responsável pelo Leilão
    private String imovelEmLeilao;
    private List<Licitador> licitadores; // Todos os participantes no leilão
    private int duracao, precoMinimo;
    private boolean leilaoBloqueado; // indica se o leilão terminou
    
    /** Construtor parametrizado (declarado como 'private' para não ser possível construir leilões sem especificar os dados dos mesmos) */
    private Leilao(){}
    
    /** Construtor parametrizado. */
    public Leilao(String imovelEmLeilao, String responsavel, int duracao, int precoMinimo){
        this.responsavel = responsavel;
        this.imovelEmLeilao = imovelEmLeilao;
        this.licitadores = new ArrayList<>();
        this.duracao = duracao;
        this.precoMinimo = precoMinimo;
        this.leilaoBloqueado = false;
    }
    
    /** Construtor de cópia. */
    public Leilao(Leilao leilao){
        this.responsavel = leilao.getResponsavel();
        this.imovelEmLeilao = leilao.getImovelEmLeilao();
        this.licitadores = new ArrayList<Licitador>();
        for(Licitador licitador : leilao.licitadores)
            this.licitadores.add(licitador.clone());
        this.duracao = leilao.duracao;
        this.precoMinimo = leilao.precoMinimo;
        this.leilaoBloqueado = leilao.leilaoBloqueado;
    }

    /** @return id do vendedor responsável pelo leilão. */
    public String getResponsavel(){
        return responsavel;
    }
    
    /** @return id do Imovel que está a ser leiloado. */
    public String getImovelEmLeilao(){
        return imovelEmLeilao;
    }

    /** 
     *  Regista um novo comprador no leilão.
     *  @param idComprador email do Comprador que se pretende registar no leilão.
     *  @param limite Quantidade máxima que o comprador está disposto a pagar.
     *  @param incrementos Em quanto é que o Comprador deve aumentar o preco atual do imóvel quando faz uma oferta.
     *  @param minutos De quanto em quanto tempo é que o Comprador deve tentar fazer uma oferta (minutos).
     *  @throws LeilaoTerminadoException se o leilao já tiver sido terminado(pela invocação do método bloqueiaLeilao).
     */
    public void registaCompradorLeilao(String idComprador, int limite, int incrementos, int minutos) 
        throws LeilaoTerminadoException
    {
        if(leilaoBloqueado)
            throw new LeilaoTerminadoException("Não pode adicionar mais vendedores, o leilão terminou.");
        Licitador novoLicitador = new Licitador(idComprador, limite, incrementos, minutos);
        licitadores.add(novoLicitador);
    }

    public boolean terminouLeilao(){
        return leilaoBloqueado;
    }

    /** 
     *  Simula o leilão e faz log das licitações para a PrintStream passada como argumento. Esta simulação é não destrutiva,
     *  i.e. depois de fazer uma simulação, é possível voltar a fazer  uma simulação válida com o mesmo objeto Leilao. O resultado
     *  é não determínistico pelo que invocações diferentes poderão ter resultados diferentes. Devolve o id do vencedor do leilão
     *  ou null se nunhuma oferta supera o preço minimo. Esta simulação é feita numa escala de tempo de forma em que 1 hora de leilão
     *  passa num minuto e 1 minuto passa num segundo.
     */    
    public String simulaLeilao(PrintStream impressora) throws java.io.IOException{
        int duracaoMinutos = 60 * duracao;
        int precoAtual = 0;
        int proxI;
        Random random = new Random();
        Licitador aGanhar = null;
        //FileWriter fw = new FileWriter("leilao.txt", false); // vai fazer log dos dados do leilao para o ficheiro
        
        // copia estado antes de executar para ser possivel fazer simulaçoes nao-destrutivas dos leiloes
        List<Licitador> copiaLicitadores = new ArrayList<>(licitadores);

        impressora.println("-> Participantes: ");
        for(Licitador l : copiaLicitadores){
            impressora.println(l.entradaLog());
        } 
        impressora.println("\n-> Licitações: ");

        for(int i = 0; !copiaLicitadores.isEmpty() && i < duracaoMinutos; i = proxI){
            proxI = i + 1;
            Iterator<Licitador> iter = copiaLicitadores.iterator();
            ArrayList<Licitador> candidatosLicitar = new ArrayList<>(); // vai guardar a lista de candidatos a Licitar que correspondem aos licitadores com ofertas mais altas
            int melhorOferta = 0; // melhor Oferta realizada por um licitador

            while(iter.hasNext()){  
                Licitador l = iter.next();

                int ofertaAtual;
                int minutoProxLicitacao = l.getQuandoProximaLicitacao();

                if(minutoProxLicitacao == i){ // se estiver na altura de fazer uma oferta
                    l.atualizaQuandoProxLicitacao();
                    if(l != aGanhar){
                        if(l.getLimite() <= precoAtual) // se o utilizador nao pode fazer uma oferta que supere o preço atual do imovel
                            iter.remove();                    
                        else{
                            ofertaAtual = l.aumentaLicitacao(precoAtual);
                            if(ofertaAtual > melhorOferta){ 
                                candidatosLicitar = new ArrayList<>(); // a lista dos candidatos a Licitar é limpa para ter apenas Licitadores que oferecem a nova melhor oferta
                                candidatosLicitar.add(l);
                                melhorOferta = ofertaAtual;
                            } else if(ofertaAtual == melhorOferta){
                                candidatosLicitar.add(l);
                            }
                        }    
                    }
                } else if(minutoProxLicitacao < proxI){ // para poupar iteracoes desnecessarias
                    proxI = i;  
                }
            }

            if(!candidatosLicitar.isEmpty()){// se alguem superou a oferta anterior
                precoAtual = melhorOferta; // atualiza o preco atual do imovel
                aGanhar = candidatosLicitar.get(random.nextInt(candidatosLicitar.size())); // escolhe um Licitador à sorte dos candidatos a atualizar
                impressora.println(aGanhar.getIdComprador() + " ofereceu " + precoAtual + "€ no minuto " + i); // regista a Licitação no log
            }

            try{
                Thread.sleep(Math.min(proxI-i, duracaoMinutos-i) * 1000);
            }catch(Exception e){}
        }

        // Faz reset para poder voltar a fazer simulacoes do leilao
        for(Licitador l : licitadores)
            l.reset();
        return (aGanhar == null || precoAtual < precoMinimo)? null : aGanhar.getIdComprador();
    }

    /** Bloqueia um leilao, impedino que sejam adicionados novos participantes */
    public void bloqueiaLeilao(){
        this.leilaoBloqueado = true;
    }
    
    public Leilao clone(){
        return new Leilao(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        else if(o == null || this.getClass() != o.getClass())
            return false;

        Leilao l = (Leilao) o;
        return responsavel.equals(l.responsavel) && imovelEmLeilao.equals(l.imovelEmLeilao) && 
               licitadores.equals(l.licitadores) && duracao == l.duracao && precoMinimo == l.precoMinimo &&
               leilaoBloqueado == l.leilaoBloqueado;
    }
    
        public String toString(){
        StringBuilder sb = new StringBuilder("-> Leilão:\n");

        sb.append("Responsável: " + responsavel + "\n");
        sb.append("Imóvel em leilão: " + imovelEmLeilao + "\n");
        sb.append("Licitadores:\n");
        for(Licitador l : licitadores)
            sb.append(l.toString() + "\n");
        sb.append("Duração: " + duracao + "\n");
        // o preço mínimo não é apresentado propositadamente
        sb.append("Leilão bloqueado: " + (leilaoBloqueado ? "sim" : "não") + "\n");
        return sb.toString();
    }

    /** @return Valor do hash code deste Leilao. */
    public int hashCode(){
        int hash = 7;

        hash = 31*hash + responsavel.hashCode();
        hash = 31*hash + imovelEmLeilao.hashCode();
        hash = 31*hash + licitadores.hashCode();
        hash = 31*hash + duracao;
        hash = 31*hash + precoMinimo;
        hash = 31*hash + (leilaoBloqueado ? 1 : 0);
        return hash;
    }
}
