import java.time.LocalDate;
import java.io.Serializable;

public final class Consulta implements Comparable<Consulta>, Serializable
{   
    // Variaveis de instancia
    private final LocalDate data; // data da consulta
    private final String email; // email do utilizador que fez a consulta (valor opcional) 
    
    /**
     * Construtor por omissao
     */
    public Consulta(){
        data = LocalDate.now(); // data atual
        email = "n/a"; // e-mail desconhecido
    }
    
    /**
     * Construtor parametrizado
     */
    public Consulta(String email){
        this.data = LocalDate.now();
        this.email = email;
    }
      
    /**
     * Construtor de copia
     */
    public Consulta(Consulta c){
        this.email = c.getEmail();
        this.data = c.getData();
    }
    
    // Getters
    public LocalDate getData(){
        return data; // objetos da classe LocalDate são imutáveis. Não precisamos de fazer clone().
    }
    
    public String getEmail(){
        return email;
    }

    /* Este tipo é Imutável, depois de feita uma consulta, esta não pode ser alterada */
    
    public boolean feitaPorUtilizadorRegistado(){
        return email != null;
    }
    
    public Consulta clone(){
        return new Consulta(this);
    }
    
    public boolean equals(Object o){
        boolean eIgual = true;
        
        if(this == o)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Consulta c = (Consulta) o;
        if(data == null && c.getData() != null)
            eIgual = false;
        if(email == null && c.getEmail() != null) 
            eIgual = false;
        return eIgual && data.equals(c.getData()) && email.equals(c.getEmail());
    }
    
    @Override
    public int compareTo(Consulta c){
        return data.compareTo(c.getData());
    }
    
    public String toString(){
        return "E-mail: " + email + "\nData: " + data.toString() + "\n";
    }
    
    public int hashCode(){
        int hash, hashEmail, hashData;
        hash = 7;
        hashEmail = email == null ? 0 : email.hashCode();
        hashData = data == null ? 0 : data.hashCode();
        
        hash = 31 * hash + hashEmail;
        hash = 31 * hash + hashData;
        return hash;
    }
}
