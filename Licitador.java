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
    private int limite, minutos, incrementos;
    private int proximaLicitacao; /* serve como referencia para a ordem com que os Licitadores devem licitar */

    private Licitador() {}

    public Licitador(String idComprador, int limite, int incrementos, int minutos){
        this.idComprador = idComprador;
        this.limite = limite;
        this.incrementos = incrementos;
        this.minutos = minutos;
    }

    public Licitador(Licitador l){
        this(l.idComprador, l.limite, l.incrementos, l.minutos);
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
