import java.io.Serializable;
import java.lang.Comparable;
/**
 * Write a description of class Licitador here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Licitador implements Serializable, Comparable<Licitador>
{
    private String idComprador;
    private int limite, 
    intervaloIncrementos, /* indica de quanto em quanto tempo é que o utilizador faz incrementos */
    incrementos,
    minutosDesdeInicio, /* indica quantos minutos passaram entre o inicio do leilao e o registo do cliente */
    proximaLicitacao; /* indica qual o minuto (relativo ao inicio do leilao) em que o utilizador vai incrementar a sua oferta */

    private Licitador() {}

    public Licitador(String idComprador, int limite, int incrementos, int intervaloIncrementos, int minutosDesdeInicio){
        this.idComprador = idComprador;
        this.limite = limite;
        this.incrementos = incrementos;
        this.intervaloIncrementos = intervaloIncrementos;
        this.minutosDesdeInicio = minutosDesdeInicio;
        this.proximaLicitacao = minutosDesdeInicio;
    }

    public Licitador(Licitador l){
        this(l.idComprador, l.limite, l.incrementos, l.intervaloIncrementos, l.minutosDesdeInicio);
    }

    
    
    public int compareTo(Licitador l){
        /* que criterio devemos usar para compara-los?*/
        if(this.proximaLicitacao == l.proximaLicitacao)
            return 0;
        if(this.proximaLicitacao > l.proximaLicitacao)
            return 1;
        else return -1;      
    }
}
