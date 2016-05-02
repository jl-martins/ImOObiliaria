import java.util.GregorianCalendar;

public class Consulta
{   
    // Variaveis de instancia
    private GregorianCalendar data; // data da consulta
    private String email; // email do utilizador que fez a consulta (valor opcional) 
    
    /**
     * Construtor por omissao
     */
    public Consulta(){
        data = new GregorianCalendar(); // data atual
        email = null;
    }
    
    /**
     * Construtor parametrizado
     */
    public Consulta(GregorianCalendar data, String email){
        this.data = (data != null) ? (GregorianCalendar) data.clone() : new GregorianCalendar();
        this.email = email;
    }
    
    /**
     * Construtor de copia
     */
    public Consulta(Consulta c){
        this(c.getData(), c.getEmail());
    }
    
    // Getters
    public GregorianCalendar getData(){
        return (GregorianCalendar) data.clone();
    }
    
    public String getEmail(){
        return email;
    }
    
    // Setters
    public void setData(GregorianCalendar data){
        if(data != null) // impede que se guarde null na variável de instancia 'data'
            this.data = (GregorianCalendar) data.clone();
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public Consulta clone(){
        return new Consulta(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        Consulta c = (Consulta) o;
        return data.equals(c.getData()) && email.equals(c.getEmail());
    }
    
    public String toString(){
        return "";
    }
}
