import java.util.GregorianCalendar;

public class Consulta implements Comparable<Consulta>
{   
    // Variaveis de instancia
    private GregorianCalendar data; // data da consulta
    private String email; // email do utilizador que fez a consulta (valor opcional) 
    
    /**
     * Construtor por omissao
     */
    public Consulta(){
        data = new GregorianCalendar(); // data atual
        email = null; // e-mail desconhecido
    }
    
    /**
     * Construtor parametrizado
     */
    public Consulta(String email){
        this.email = email;
        this.data = new GregorianCalendar();
    }
      
    /**
     * Construtor de copia
     */
    public Consulta(Consulta c){
        this.email = c.getEmail();
        this.data = c.getData();
    }
    
    // Getters
    public GregorianCalendar getData(){
        return (data == null)? null : (GregorianCalendar) data.clone();
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
        return this.data.compareTo(c.data);
    }
    
    public String toString(){
        String aux = (data == null) ? "n/a" : data.toString(); 
        return "E-mail: " + email + "\nData: " + aux + "\n";
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
