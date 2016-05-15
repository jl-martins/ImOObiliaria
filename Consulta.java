/**
 * Classe de objetos imutáveis que guardam informação sobre os vários dados relativos à consulta de um Imovel, 
 * nomeadamente a data, o id do Imovel e o email do utilizador que o consultou (quando disponível).
 * @author Grupo12 
 * @version 15/05/2016
 */

import java.time.LocalDate;
import java.io.Serializable;

public final class Consulta implements Comparable<Consulta>, Serializable
{   
    // Variaveis de instancia
    private final LocalDate data; // data da consulta
    private final String email; // email do utilizador que fez a consulta (valor opcional)
    private final String idImovel; // id do imóvel consultado
    
    /**
     * Construtor por omissao
     */
    public Consulta(){
        data = LocalDate.now(); // data atual
        email = idImovel = "n/a";
    }
    
    /**
     * Construtor parametrizado
     */
    public Consulta(String email, String idImovel){
        this.data = LocalDate.now();
        this.email = email;
        this.idImovel = idImovel;
    }
    
    public Consulta(Utilizador usr, String idImovel){
        this.data = LocalDate.now();
        this.email = (usr == null)? "n/a" : usr.getEmail();
        this.idImovel = (idImovel == null)? "n/a" : idImovel;
    }
      
    /**
     * Construtor de copia
     */
    public Consulta(Consulta c){
        this.email = c.getEmail();
        this.data = c.getData();
        this.idImovel = c.getIdImovel();
    }
    
    // Getters
    public LocalDate getData(){
        return data; // objetos da classe LocalDate são imutáveis. Não precisamos de fazer clone().
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getIdImovel(){
        return idImovel;
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
        return eIgual && data.equals(c.getData()) && email.equals(c.getEmail()) && idImovel.equals(c.getIdImovel()); 
    }
    
    @Override
    public int compareTo(Consulta c){
        return data.compareTo(c.getData());
    }
    
    public String toString(){
        return "E-mail: " + email + "\nData: " + data.toString() + "\nID do imóvel consultado: " + idImovel + "\n";
    }
    
    public int hashCode(){
        int hash = 7;
        
        hash = 31 * hash + (email == null ? 0 : email.hashCode());
        hash = 31 * hash + (data == null ? 0 : data.hashCode());
        hash = 31 * hash + (idImovel == null ? 0 : idImovel.hashCode());
        return hash;
    }
}
