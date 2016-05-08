

public class LojaHabitavel extends Loja
        implements Habitavel
{   
    Apartamento apartamento;
    
    public LojaHabitavel(){
        super();
        apartamento = new Apartamento();
    }
    
    public LojaHabitavel(String id, String rua, double precoPedido, double precoMinimo, int area,
                         boolean temWC, String tipoNegocio, int numDaPorta, Apartamento apartamento){
        
        super(id, rua, precoPedido, precoMinimo, area, temWC, tipoNegocio, numDaPorta);
        setApartamento(apartamento);
    }
    
    public LojaHabitavel(Loja l, Apartamento a){
        super(l);
        apartamento = new Apartamento(a);
    }
    
    public LojaHabitavel(LojaHabitavel lh){
        super(lh);
        apartamento = lh.getApartamento();
    }
        
    public Apartamento getApartamento(){
        return (apartamento == null) ? null : apartamento.clone();
    }
    
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
        StringBuilder sb = new StringBuilder(super.toString());
        
        sb.append(apartamento.toString());
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = super.hashCode();
        
        hash = 31*hash + ((apartamento == null) ? 0 : apartamento.hashCode());
        return hash;
    }
}
