/**
 * Classe que representa um vendedor da Imoobiliaria.
 * @author Grupo12
 * @version 15/05/2016
 */

import java.util.Set;
import java.util.TreeSet;
import java.time.LocalDate;
import java.io.Serializable;

public class Vendedor extends Utilizador implements Serializable
{ 
    private Set<String> emVenda = null;
    private Set<String> vendidos = null;
    
    /** Construtor parametrizado */
    public Vendedor(String email, String nome, String password, String morada, LocalDate dataNascimento){
        super(email, nome, password, morada, dataNascimento);
        emVenda = new TreeSet<String>();
        vendidos = new TreeSet<String>();
    }
    
    /** Construtor de cópia */
    public Vendedor(Vendedor v){
        super(v);
        setEmVenda(v.getEmVenda());
        setVendidos(v.getVendidos());
    }
    
    // Getters
    public Set<String> getEmVenda(){
        return new TreeSet<String>(emVenda); // Strings são imutáveis, logo não quebramos o encapsulamento
    }
    
    public Set<String> getVendidos(){
        return new TreeSet<String>(vendidos); // Strings são imutáveis, logo não quebramos o encapsulamento
    }
    
    // Setters
    public void setEmVenda(Set<String> emVenda){
        this.emVenda = new TreeSet<String>(emVenda);
    }
    
    public void setVendidos(Set<String> vendidos){
        this.vendidos = new TreeSet<String>(vendidos);
    }
    
    /** Testa se este vendedor registou um imóvel com id igual ao valor passado como parâmetro. */
    public boolean registouImovel(String idImovel){
        return emVenda.contains(idImovel) || vendidos.contains(idImovel);
    }
    
    /**
     * Verifica se este vendedor vende o imóvel com o id passado como parâmetro.
     * @param idImovel id de imóvel a procurar nos ids dos imóveis que estão à venda neste Vendedor.
     * @return true se este vendedor vender um imóvel com o id @code idImovel.
     */
    public boolean vendeImovel(String idImovel){
        return emVenda.contains(idImovel);
    }
    
    /**
     * Altera o estado de um imóvel.
     * @param idImovel id do imóvel cujo estado se pretende alterar.
     * @param estado Novo estado do imóvel.
     */
    public void alteraEstadoImovel(String idImovel, EstadoImovel estado) {
        if(estado == EstadoImovel.VENDIDO){
            emVenda.remove(idImovel);
            vendidos.add(idImovel);
        } else {
            emVenda.add(idImovel);
            vendidos.remove(idImovel);
        }
    }
    
    /** 
     * Se este Vendedor vender ou tiver vendido um imóvel com id igual à String
     * passada como parâmetro, remove esse imóvel dos registos do vendedor.
     */
    public void removeImovel(String idImovel){
        if(emVenda.remove(idImovel) == false)
            vendidos.remove(idImovel);
    }
    
    /** Acrescenta o id passado como parâmetro ao conjunto dos ids dos imóveis que estão à venda. */
    public void poeAVenda(String idImovel){
        emVenda.add(idImovel);
    }
    
    /** @return Conjunto de ids de todos os imóveis do vendedor. */
    public Set<String> todosImoveisVendedor(){
        Set<String> todosImoveis = new TreeSet<String>(emVenda);
        todosImoveis.addAll(vendidos);
        return todosImoveis;
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
        StringBuilder sb = new StringBuilder("-> Vendedor\n");
        
        sb.append(super.toString());
        sb.append("Em venda:\n");
        for(String idImovel : emVenda)
            sb.append(idImovel + "\n");
        
        sb.append("Vendidos:\n");
        for(String idImovel : vendidos)
            sb.append(idImovel + "\n");
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = super.hashCode();
        
        hash = 31*hash + ((emVenda == null) ? 0 : emVenda.hashCode());
        hash = 31*hash + ((vendidos == null) ? 0 : vendidos.hashCode());
        return hash;
    }
}
