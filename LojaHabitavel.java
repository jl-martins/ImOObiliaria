/**
 * Classe que representa uma loja com parte habitacional.
 * A parte habitacional corresponde a um objeto da classe Apartamento.
 * @author Grupo12 
 * @version 15/05/2016
 */
import java.io.Serializable;

public class LojaHabitavel extends Loja implements Serializable, Habitavel
{   
    private Apartamento apartamento;
    
    /** Construtor por omissão. */
    public LojaHabitavel(){
        super();
        apartamento = new Apartamento();
    }
    
    /** Construtor parametrizado. */
    public LojaHabitavel(String id, String rua, int precoPedido, int precoMinimo, int area,
                         boolean temWC, String tipoNegocio, int numDaPorta, Apartamento apartamento)
    {
        super(id, rua, precoPedido, precoMinimo, area, temWC, tipoNegocio, numDaPorta);
        setApartamento(apartamento);
    }
    
    /** Constrói uma loja habitável a partir da loja e do apartamento passados como parâmetro. */
    public LojaHabitavel(Loja l, Apartamento a){
        super(l);
        apartamento = a.clone();
    }
    
    /** Construtor de cópia. */
    public LojaHabitavel(LojaHabitavel lh){
        super(lh);
        apartamento = lh.getApartamento();
    }
    
    // Getters
    public Apartamento getApartamento(){
        return (apartamento == null) ? null : apartamento.clone();
    }
    
    // Setters
    public void setApartamento(Apartamento apartamento){
        this.apartamento = (apartamento == null) ? null : apartamento.clone();
    }
    
    public LojaHabitavel clone(){
        return new LojaHabitavel(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        LojaHabitavel lh = (LojaHabitavel) o;
        
        return super.equals(lh) && apartamento.equals(lh.getApartamento());
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder("-> Loja Habitável\n");
        sb.append(super.toStringParcial());
        sb.append(apartamento.toStringParcial());
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = super.hashCode();
        
        hash = 31*hash + ((apartamento == null) ? 0 : apartamento.hashCode());
        return hash;
    }
}
