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
    private int limite;     // Limite que o Licitador está disposto a pagar
    private int intervaloIncrementos; // Indica de quanto em quanto tempo é que o utilizador faz ofertas 
    private int incrementos; // Em quanto é que o Licitador sobe a oferta
    private int quandoProximaLicitacao; // Indica em que minuto é que o Licitador vai fazer a proxima oferta
    
    /** Construtor parametrizado (declarado como 'private' para não se possível criar licitadores sem especificar os seus dados) */
    private Licitador() {}
    
    /** Construtor parametrizado. */
    public Licitador(String idComprador, int limite, int incrementos, int intervaloIncrementos){
        this.idComprador = idComprador;
        this.limite = limite;
        this.intervaloIncrementos = intervaloIncrementos;
        this.incrementos = incrementos;
        this.quandoProximaLicitacao = 0;
    }
    
    /** Construtor de cópia. */
    public Licitador(Licitador l){
        this(l.idComprador, l.limite, l.incrementos, l.intervaloIncrementos);
    }

    /** 
     * Restaura o estado do objeto para fazer licitações nos instantes corretos. 
     * Método a invocar quando não se pretende utilizar mais o licitador no leilão.
     */
    public void reset(){
        this.quandoProximaLicitacao = 0;
    }
    
    /** @return id do comprador que está a licitar. */
    public String getIdComprador(){
        return this.idComprador;
    }

    /** @return Valor dos incrementos a efetuar por este Licitador. */
    public int getIncrementos(){
        return incrementos;
    }
    
    /** @return Minuto do leilão será feita a próxima licitação. */
    public int getQuandoProximaLicitacao(){
        return quandoProximaLicitacao;
    }

    /** Faz com que o Licitador faça a próxima oferta depois de @code intervaloIncrementos minutos. */
    public void atualizaQuandoProxLicitacao(){
        this.quandoProximaLicitacao += intervaloIncrementos;
    }
    
    /** @return Valor máximo que o licitador está disposto a investir. */
    public int getLimite(){
        return limite;
    }

    /** Se possível, faz uma oferta de forma a superar o valor passado como argumento, se não oferece o valor limite deste Licitador. */
    public int aumentaLicitacao(int n){
        int novoValor = n + incrementos;
        return (novoValor > this.limite)? this.limite : novoValor;
    }
    

    /** @return String com os dados deste Licitador para ser registada no log de um Leilao. */
    public String entradaLog(){
        StringBuilder sb = new StringBuilder("Email: ");
        
        sb.append(idComprador);
        sb.append("; limite: " + limite + "€");
        sb.append("; intervalo: " + intervaloIncrementos + " min");
        sb.append("; incrementos: " + incrementos + "€.");
        return sb.toString();
    }
    
    public Licitador clone(){
        return new Licitador(this);
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
