import java.util.Set;
import java.util.TreeSet;
import java.util.GregorianCalendar;

public class Vendedor extends Utilizador
{ 
    private Set<String> emVenda = null;
    private Set<String> vendidos = null;
    
    /** Construtor parametrizado */
    public Vendedor(String email, String nome, String password, String morada,
                    GregorianCalendar dataNascimento){
        super(email, nome, password, morada, dataNascimento);
        this.emVenda = new TreeSet<String>();
        this.vendidos = new TreeSet<String>();
    }
    
    /** Construtor de cópia */
    public Vendedor(Vendedor v){
        super(v);
        setEmVenda(v.getEmVenda());
        setVendidos(v.getVendidos());
    }
    
    public Set<String> getEmVenda(){
        Set<String> emVenda = new TreeSet<String>();
        
        for(String idImovel : this.emVenda)
            emVenda.add(idImovel);
        return emVenda;
    }
    
    public Set<String> getVendidos(){
        Set<String> vendidos = new TreeSet<String>();
        
        for(String idImovel : this.vendidos)
            vendidos.add(idImovel);
        return vendidos;
    }
    
    public void setEmVenda(Set<String> emVenda){
        this.emVenda = new TreeSet<String>();
        
        for(String idImovel : emVenda)
            this.emVenda.add(idImovel);
    }
    
    public void setVendidos(Set<String> vendidos){
        this.vendidos = new TreeSet<String>();
        
        for(String idImovel : vendidos)
            this.vendidos.add(idImovel);
    }
    
    public void poeAVenda(String idImovel){
        this.emVenda.add(idImovel);
    }
    
    public Vendedor clone(){
        return new Vendedor(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        else if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            Vendedor vendedor = (Vendedor) o;
            
            return super.equals(vendedor) && emVenda.equals(vendedor.getEmVenda()) && vendidos.equals(vendedor.getVendidos());
        }
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder("-> Vendedor\n");
        
        sb.append(super.toString());
        sb.append("Em venda:\n");
        for(String idImovel : emVenda)
            sb.append(idImovel + "\n");
        
        sb.append("Vendidos:\n");
        for(String idImovel : vendidos)
            sb.append(idImovel + "\n");
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = super.hashCode();
        
        hash = 31*hash + ((emVenda == null) ? 0 : emVenda.hashCode());
        hash = 31*hash + ((vendidos == null) ? 0 : vendidos.hashCode());
        return hash;
    }
}
