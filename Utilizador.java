import java.time.LocalDate;
import java.io.Serializable;

public abstract class Utilizador implements Serializable
{
    // variáveis de instancia
    private String email;
    private String nome;
    private String password;
    private String morada;
    private LocalDate dataNascimento;
    
    public Utilizador(){
        this("n/a", "n/a", "n/a", "n/a", null);
    }
    
    /**
     * Construtor parametrizado.
     */
    public Utilizador(String email, String nome, String password, String morada, LocalDate dataNascimento){
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.morada = morada;
        this.dataNascimento = dataNascimento; // As instâncias de LocalDate são imutáveis, logo não precisamos de fazer clone()
    }
    
    /**
     * Construtor de cópia
     */
    public Utilizador(Utilizador original){
        this(original.getEmail(), original.getNome(), original.getMorada(),
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
    
    public LocalDate getDataNascimento(){
        return dataNascimento;
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
    
    public void setDataNascimento(LocalDate dataNascimento){
        this.dataNascimento = dataNascimento; // As instâncias de LocalDate são imutáveis, logo não precisamos de fazer clone()
    }
    
    public boolean validaPassword(String password){
        return this.password.equals(password);
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
        // a password não é adicionada à String devolvida para que não a revelemos.
        sb.append("Data de nascimento: " + dataNascimento.toString());
        return sb.toString();
    }
}
