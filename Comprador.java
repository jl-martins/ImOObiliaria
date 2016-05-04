import java.util.Set;
import java.util.TreeSet;
import java.util.GregorianCalendar;

public class Comprador extends Utilizador
{
    Set<Imovel> favoritos; // conjunto dos imoveis favoritos do Comprador
    
    /**
     * Construtor por omissao
     */
    public Comprador(){
        super();
        favoritos = new TreeSet<Imovel>();
    }
    
     /**
      * Construtor parametrizado.
      */
    public Comprador(String email, String nome, String password, String morada,
                     GregorianCalendar dataNascimento, Set<Imovel> favoritos)
    {
        super(email, nome, password, morada, dataNascimento);
        setFavoritos(favoritos);
    }
    
    /**
     * Construtor de copia.
     */
    public Comprador(Comprador comp){
        super(comp);
        setFavoritos(comp.getFavoritos());
    }
    
    public TreeSet<Imovel> getFavoritos(){
        TreeSet<Imovel> copiasFav = new TreeSet<Imovel>();
        
        for(Imovel fav : this.favoritos)
            copiasFav.add(fav.clone());
        return copiasFav;
    }
    
    public void setFavoritos(Set<Imovel> favoritos){
        this.favoritos.clear();
        
        for(Imovel fav : favoritos)
            this.favoritos.add(fav.clone());
    }
    
    public Comprador clone(){
        return new Comprador(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        else if(o == null || this.getClass() != o.getClass())
            return false;
        else{
            Comprador comp = (Comprador) o;
            
            return super.equals(comp) && favoritos.equals(comp.getFavoritos());
        }
    }
    
    public String toString(){
        return "";
    }
}
