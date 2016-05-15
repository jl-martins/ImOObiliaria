/**
 * Classe de licitadores (compradores que participam num leilão).
 * 
 * @author Grupo12
 * @version 15/05/2016
 */

import java.io.Serializable;
import java.lang.Comparable;

public class Licitador implements Serializable
{
    private String idComprador;
    private int limite, 
    intervaloIncrementos, /* indica de quanto em quanto tempo é que o utilizador faz incrementos */
    incrementos,
    minutosDesdeInicio, /* indica quantos minutos passaram entre o inicio do leilao e o registo do cliente */
    quandoProximaLicitacao, /* indica qual o minuto (relativo ao inicio do leilao) em que o utilizador vai incrementar a sua oferta */
    valorProximaLicitacao;

    private Licitador() {}

    public Licitador(String idComprador, int limite, int incrementos, int intervaloIncrementos, int minutosDesdeInicio){
        this.idComprador = idComprador;
        this.limite = limite;
        this.incrementos = incrementos;
        this.intervaloIncrementos = intervaloIncrementos;
        this.minutosDesdeInicio = minutosDesdeInicio;
        this.quandoProximaLicitacao = minutosDesdeInicio;
        this.valorProximaLicitacao = 0;
    }

    public Licitador(Licitador l){
        this(l.idComprador, l.limite, l.incrementos, l.intervaloIncrementos, l.minutosDesdeInicio);
    }

    public void reset(){
        this.quandoProximaLicitacao = this.minutosDesdeInicio;
        this.valorProximaLicitacao = 0;
    }

    public int getQuandoProximaLicitacao(){
        return quandoProximaLicitacao;
    }

    public void atualizaQuandoProxIncremento(){
        this.quandoProximaLicitacao += intervaloIncrementos;
    }

    public void atualizaValorProxIncremento(){
        this.valorProximaLicitacao += incrementos;
    }

    public int getLimite(){
        return limite;
    }

    public int setMenorLicitacaoQuePasse(int n){
        int novoValor = valorProximaLicitacao + (int) Math.ceil((n - valorProximaLicitacao)/(incrementos * 1.0));
        this.valorProximaLicitacao = (novoValor > this.limite)? this.limite : novoValor;
        return this.valorProximaLicitacao;
    }
    
    public String getIdComprador(){
        return this.idComprador;
    }
}
