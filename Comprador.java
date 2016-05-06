import java.util.Set;
import java.util.TreeSet;
import java.util.GregorianCalendar;

public class Comprador extends Utilizador
{
    private Set<String> favoritos = null; // conjunto de ids dos imoveis favoritos do Comprador
    
     /**
      * Construtor parametrizado.
      */
    public Comprador(String email, String nome, String password, String morada,
                     GregorianCalendar dataNascimento)
    {
        super(email, nome, password, morada, dataNascimento);
        favoritos = new TreeSet<String>();
    }
    
    /**
     * Construtor de copia.
     */
    public Comprador(Comprador comp){
        super(comp);
        setFavoritos(comp.getFavoritos());
    }
    
    private TreeSet<String> getFavoritos(){
        TreeSet<String> copiaFav = new TreeSet<String>();
        
        for(String idFav : favoritos)
            copiaFav.add(idFav);
        return copiaFav;
    }
    
    private void setFavoritos(Set<String> favoritos){
        this.favoritos = new TreeSet<String>(); 
        
        for(String idFav : favoritos)
            this.favoritos.add(idFav);
    }
    
    public void setFavorito(String idImovel){
        this.favoritos.add(idImovel);
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
        StringBuilder sb = new StringBuilder("-> Comprador\n");
        
        sb.append(super.toString());
        sb.append("Ids dos imóveis favoritos: ");
        for(String str : favoritos)
            sb.append(str + "\n");
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = super.hashCode();
        
        hash = 31*hash + (favoritos == null ? 0 : favoritos.hashCode());
        return hash;
    }
}
