import java.util.List;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Vendedor extends Utilizador
{
    
    List<Imovel> emVenda;
    List<Imovel> vendidos;
    
    /** Construtor por omissao */
    public Vendedor()
    {
        super();
        emVenda = new ArrayList<Imovel>();
        vendidos = new ArrayList<Imovel>();
    }
    
    /** Construtor parametrizado */
    public Vendedor(
        String email,
        String nome,
        String password,
        String morada,
        GregorianCalendar dataNascimento,
        List<Imovel> emVenda,
        List<Imovel> vendidos
    ){
        super(email, nome, password, morada, dataNascimento);
        setEmVenda(emVenda);
        setVendidos(vendidos);
    }
    
    /** Construtor de cópia */
    public Vendedor(Vendedor v){
        this(v.getEmail(), v.getNome(), v.getPassword(), v.getMorada(), v.getDataNascimento(), v.getEmVenda(), v.getVendidos());
    }
    
    public List<Imovel> getEmVenda(){
        List<Imovel> emVenda = new ArrayList<Imovel>();
        
        for(Imovel imovel : this.emVenda)
            emVenda.add(imovel.clone());
        return emVenda;
    }
    
    public List<Imovel> getVendidos(){
        List<Imovel> vendidos = new ArrayList<Imovel>();
        
        for(Imovel imovel : this.vendidos)
            vendidos.add(imovel.clone());
        return vendidos;
    }
    
    public void setEmVenda(List<Imovel> emVenda){
        this.emVenda.clear();
        
        for(Imovel imovel : emVenda)
            this.emVenda.add(imovel.clone());
    }
    
    public void setVendidos(List<Imovel> vendidos){
        this.vendidos.clear();
        
        for(Imovel imovel : vendidos)
            this.vendidos.add(imovel.clone());
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
        return "";
    }
}
