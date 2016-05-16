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
        int novoValor = n + incrementos;
        this.valorProximaLicitacao = (novoValor > this.limite)? this.limite : novoValor;
        return this.valorProximaLicitacao;
    }

    public String getIdComprador(){
        return this.idComprador;
    }

    public String entradaLog(){
        return idComprador + " lim:" + limite + " interval:" + intervaloIncrementos + " inc:" + incrementos + " entradaLeilao" + minutosDesdeInicio + "\n";
    }

    public boolean equals(Object o){
        if(this == o)
            return true;
        else if(o == null || this.getClass() != o.getClass())
            return false;

        Licitador l = (Licitador) o;
        return idComprador.equals(l.idComprador) && limite == l.limite &&
               intervaloIncrementos == l.intervaloIncrementos && incrementos == l.incrementos &&
               minutosDesdeInicio == l.minutosDesdeInicio &&
               quandoProximaLicitacao == l.quandoProximaLicitacao &&
               valorProximaLicitacao == l.valorProximaLicitacao;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("-> Licitador:\n");

        sb.append("ID do comprador: " + idComprador + "\n");
        sb.append("Limite: " + limite + "\n");
        sb.append("Incrementos: " + incrementos + "\n");
        sb.append("Intervalo entre incrementos: " + intervaloIncrementos + "\n");
        sb.append("Minutos deste o início: " + minutosDesdeInicio + "\n");
        sb.append("Instante da próxima licitação: " + quandoProximaLicitacao + "\n");
        sb.append("Valor da próxima licitação: " + valorProximaLicitacao + "\n");
        return sb.toString();
    }

    public int hashCode(){
        int hash = 7;

        hash = 31*hash + idComprador.hashCode();
        hash = 31*hash + limite;
        hash = 31*hash + incrementos;
        hash = 31*hash + intervaloIncrementos;
        hash = 31*hash + minutosDesdeInicio;
        hash = 31*hash + quandoProximaLicitacao;
        hash = 31*hash + valorProximaLicitacao;
        return hash;
    }
}
