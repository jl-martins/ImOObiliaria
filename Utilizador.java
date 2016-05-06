import java.util.GregorianCalendar;

public abstract class Utilizador
{
    // variáveis de instancia
    private String email;
    private String nome;
    private String password;
    private String morada;
    private GregorianCalendar dataNascimento;
    
    public Utilizador(){
        this("n/a", "n/a", "n/a", "n/a", null);
    }
    
    /**
     * Construtor parametrizado.
     */
    public Utilizador(String email, String nome, String password, String morada, GregorianCalendar dataNascimento){
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.morada = morada;
        setDataNascimento(dataNascimento);
    }
    
    /**
     * Construtor de cópia
     */
    public Utilizador(Utilizador original){
        this(original.email, original.nome, original.morada,
             original.password, original.getDataNascimento());
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getMorada(){
        return morada;
    }
    
    public GregorianCalendar getDataNascimento(){
        return (dataNascimento == null) ? null : (GregorianCalendar) dataNascimento.clone();
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public void setMorada(String morada){
        this.morada = morada;
    }
    
    public void setDataNascimento(GregorianCalendar dataNascimento){
        this.dataNascimento = (dataNascimento == null) ? null : (GregorianCalendar) dataNascimento.clone() ;
    }
    
    public boolean validaPassword(String password){
        return this.password == password;
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        
        Utilizador utilizador = (Utilizador) o;
        return email.equals(utilizador.email) &&
               nome.equals(utilizador.nome) &&
               morada.equals(utilizador.morada) &&
               password.equals(utilizador.password) &&
               dataNascimento.equals(utilizador.getDataNascimento());
    }
    
    abstract public Utilizador clone();
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Email: " + email + "\n");
        sb.append("Nome: " + nome + "\n");
        sb.append("Morada: " + morada + "\n");
        sb.append("Password: " + password + "\n");
        sb.append("Data de nascimento: " + dataNascimento.get(GregorianCalendar.DAY_OF_YEAR) + "/"
                                         + dataNascimento.get(GregorianCalendar.MONTH) + "/"
                                         + dataNascimento.get(GregorianCalendar.YEAR) + "\n");
        return sb.toString();
    }
}
