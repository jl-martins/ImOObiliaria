/**
 * Write a description of class MenuMain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Arrays;
import java.util.Scanner;
import static java.lang.System.out;

final public class MenuMain
{
    private final String[] opcoes;
    private final int numOpcoes;
    
    /**
     * Construtor por omissão
     * (declarado como privado para não ser possível construir menus vazios)
     */
    private MenuMain(){
        opcoes = null;
        numOpcoes = 0;
    }   
    
    /**
     * Construtor parametrizado.
     * @param opcoes Opções do menu a criar.
     */
    public MenuMain(String[] opcoes){
        this.numOpcoes = opcoes.length;
        this.opcoes = new String[numOpcoes];
        System.arraycopy(opcoes, 0, this.opcoes, 0, this.numOpcoes);
    }
    
    /**
     * Construtor de cópia.
     * @param menu Menu a copiar.
     */
    public MenuMain(MenuMain menu){
        this(menu.getOpcoes());
    }
    
    /** @return Array de String com as opções deste menu. */
    public String[] getOpcoes(){
        String[] copia = new String[numOpcoes];
        System.arraycopy(opcoes, 0, copia, 0, numOpcoes);
        return copia;
    }
    
    /** @return Número de opções deste menu. */
    public int getNumOpcoes(){
        return numOpcoes;
    }
    
    /** Imprime as opções deste menu. */
    public void executa(){
        out.print(toString());
    }
    
    public int getOpcao(){
        Scanner input = new Scanner(System.in);
        return input.nextInt();
    }
    
    /** @return Clone deste menu. */
    public MenuMain clone(){
        return new MenuMain(this);
    }
    
    /** 
     * Testa se este menu é igual ao objeto passado como parâmetro.
     * @param o Objeto a comparar com este menu.
     * @return true se os objetos forem iguais.
     */
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        MenuMain menu = (MenuMain) o;
        
        return Arrays.equals(opcoes, menu.getOpcoes()); // se os arrays forem iguais, têm o mesmo número de opções.
    }
    
    /** @return String com as opções deste menu e uma prompt no final das opções. */
    public String toString(){
        StringBuilder sb = new StringBuilder("Opções:\n");
        
        for(int i = 1; i <= numOpcoes; ++i)
            sb.append(i + ". " + opcoes[i-1] + "\n");
        sb.append("\n>>> ");
        return sb.toString(); // não acrescentamos numOpcoes à String, porque as opções já estão numeradas e dá para perceber quantas opções existem.
    }
    
    /** @return Valor do hash code deste menu. */
    public int hashCode(){
        int hash = 7;
        
        for(int i = 0; i < numOpcoes; ++i)
            hash = 31*hash + opcoes[i].hashCode();
        hash = 31*hash + numOpcoes;
        return hash;
    } 
}
