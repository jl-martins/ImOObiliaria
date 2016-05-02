

public class LojaHabitavel extends Loja
{   
    Apartamento apartamento;
    
    public LojaHabitavel(){
        super();
        apartamento = new Apartamento();
    }
    
    public LojaHabitavel(String id, String rua, double precoPedido, double precoMinimo, int area,
                         boolean temWC, String tipoNegocio, int numDaPorta, Apartamento apartamento)
    {
        super(id, rua, precoPedido, precoMinimo, area, temWC, tipoNegocio, numDaPorta);
        setApartamento(apartamento);
    }
    
    public LojaHabitavel(LojaHabitavel lh){
        super(lh);
        apartamento = lh.getApartamento();
    }
        
    public Apartamento getApartamento(){
        return apartamento.clone();
    }
    
    public void setApartamento(Apartamento apartamento){
        this.apartamento = apartamento.clone();
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
        return "";
    }
}
