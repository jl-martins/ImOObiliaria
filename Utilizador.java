import java.util.GregorianCalendar;

public abstract class Utilizador
{
    // variáveis de instancia
    private String email;
    private String nome;
    private String password;
    private String morada;
    private GregorianCalendar dataNascimento;

    /**
     * Construtor por omissao.
     */
    public Utilizador(){
        email = "n/a";
        nome = "n/a";
        password = "n/a";
        morada = "n/a";
        dataNascimento = new GregorianCalendar();
    }
    
    /**
     * Construtor parametrizado.
     */
    public Utilizador(String email, String nome, String password, String morada, GregorianCalendar dataNascimento){
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.morada = morada;
        this.dataNascimento = (GregorianCalendar) dataNascimento.clone();
    }
    
    /**
     * Construtor de cópia
     */
    public Utilizador(Utilizador original){
        this(original.getEmail(),
             original.getNome(),
             original.getMorada(),
             original.getPassword(),
             original.getDataNascimento()
        );
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
    
    public String getPassword(){
        return password;
    }
    
    public GregorianCalendar getDataNascimento(){
        return (GregorianCalendar) dataNascimento.clone();
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
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setDataNascimento(GregorianCalendar dataNascimento){
        this.dataNascimento = (GregorianCalendar) dataNascimento.clone();
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        
        Utilizador utilizador = (Utilizador) o;
        return email.equals(utilizador.getEmail()) &&
               nome.equals(utilizador.getNome()) &&
               morada.equals(utilizador.getMorada()) &&
               password.equals(utilizador.getPassword()) &&
               dataNascimento.equals(utilizador.getDataNascimento());
    }
    
    abstract public Utilizador clone();
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Utilizador:\n\n");
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
