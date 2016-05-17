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
    private int limite, // Limite que o Licitador está disposto a pagar
    intervaloIncrementos, // Indica de quanto em quanto tempo é que o utilizador faz ofertas 
    incrementos, // Em quanto é que o Licitador sobe a oferta
    quandoProximaLicitacao; // Indica em que minuto é que o Licitador vai fazer a proxima oferta

    private Licitador() {}
    
    public Licitador(String idComprador, int limite, int incrementos, int intervaloIncrementos){
        this.idComprador = idComprador;
        this.limite = limite;
        this.incrementos = incrementos;
        this.intervaloIncrementos = intervaloIncrementos;
        this.quandoProximaLicitacao = 0;
    }

    public Licitador(Licitador l){
        this(l.idComprador, l.limite, l.incrementos, l.intervaloIncrementos);
    }

    /** Deve-se fazer quando nao se pretende utilizar mais o licitador no leilao. Restaura o estado do objeto para fazer licitaçoes nos tempos corretos. */
    public void reset(){
        this.quandoProximaLicitacao = 0;
    }

    public int getIncrementos(){
        return incrementos;
    }
    
    /** Indica em que minuto do leilão vai ser feita a próxima Licitação. */
    public int getQuandoProximaLicitacao(){
        return quandoProximaLicitacao;
    }

    /** Faz com que o Licitador faça a próxima oferta depois de 'intervaloIncremento' minutos. */
    public void atualizaQuandoProxLicitacao(){
        this.quandoProximaLicitacao += intervaloIncrementos;
    }

    public int getLimite(){
        return limite;
    }

    /** Faz uma oferta de forma a superar o valor passado como argumento. */
    public int aumentaLicitacao(int n){
        int novoValor = n + incrementos;
        return (novoValor > this.limite)? this.limite : novoValor;
    }

    public String getIdComprador(){
        return this.idComprador;
    }

    /** Cria uma string com os dados do Licitador para ser registada no log do leilao. */
    public String entradaLog(){
        return idComprador + " limimte:" + limite + " intervalo:" + intervaloIncrementos + " incrementos:" + incrementos;
    }

    public boolean equals(Object o){
        if(this == o)
            return true;
        else if(o == null || this.getClass() != o.getClass())
            return false;

        Licitador l = (Licitador) o;
        return idComprador.equals(l.idComprador) && limite == l.limite &&
               intervaloIncrementos == l.intervaloIncrementos && incrementos == l.incrementos &&            
               quandoProximaLicitacao == l.quandoProximaLicitacao;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("-> Licitador:\n");

        sb.append("ID do comprador: " + idComprador + "\n");
        sb.append("Limite: " + limite + "\n");
        sb.append("Incrementos: " + incrementos + "\n");
        sb.append("Intervalo entre incrementos: " + intervaloIncrementos + "\n");
        sb.append("Instante da próxima licitação: " + quandoProximaLicitacao + "\n");
        return sb.toString();
    }

    public int hashCode(){
        int hash = 7;

        hash = 31*hash + idComprador.hashCode();
        hash = 31*hash + limite;
        hash = 31*hash + incrementos;
        hash = 31*hash + intervaloIncrementos;
        hash = 31*hash + quandoProximaLicitacao;
        return hash;
    }
}
