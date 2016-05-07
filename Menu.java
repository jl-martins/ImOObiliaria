/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;
import static java.lang.System.out;

final public class Menu
{
    private final String[] opcoes;
    private final int numOpcoes;
    private int op;
    
    /**
     * Construtor por omissão
     * (declarado como privado para não ser possível construir menus vazios)
     */
    private Menu(){
        opcoes = null;
        numOpcoes = 0;
        op = -1;
    }   
    
    /**
     * Construtor parametrizado.
     * @param opcoes Opções do menu a criar.
     */
    public Menu(String[] opcoes){
        numOpcoes = opcoes.length;
        this.opcoes = new String[numOpcoes];
        System.arraycopy(opcoes, 0, this.opcoes, 0, numOpcoes);
    }
    
    /**
     * Construtor de cópia.
     * @param menu Menu a copiar.
     */
    public Menu(Menu menu){
        this(menu.getOpcoes());
    }
    
    /** @return Array de String com as opções deste menu. */
    public String[] getOpcoes(){
        String[] copia = new String[numOpcoes];
        System.arraycopy(opcoes, 0, copia, 0, numOpcoes);
        return copia;
    }
    
    /** @return Número de opções deste menu. */
    public int getOpcao(){
        return op;
    }
    
    /** Imprime as opções deste menu. */
    public void executa(){
        do{
            apresentaMenu();
            op = lerOpcao();
        } while(op == -1);
    }
    
    private void apresentaMenu(){
        out.print(toString());
    }
    
    private int lerOpcao(){
        int op;
        Scanner input = new Scanner(System.in);
        
        out.print(">>> ");
        try {
            op = input.nextInt();
        }
        catch(InputMismatchException e){
            op = -1;
            input.nextLine(); // consome a linha que ficou no buffer do stdin 
        }
        
        if(op < 0 || op > opcoes.length){
            out.println("Opção inválida!");
            op = -1;
        }
        return op;
    }
    
    /** @return Clone deste menu. */
    public Menu clone(){
        return new Menu(this);
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
        Menu menu = (Menu) o;
        
        return Arrays.equals(opcoes, menu.getOpcoes()); // se os arrays forem iguais, têm o mesmo número de opções.
    }
    
    /** @return String com as opções deste menu e uma prompt no final das opções. */
    public String toString(){
        StringBuilder sb = new StringBuilder("\n *** Menu *** ");
        
        for(int i = 1; i <= numOpcoes; ++i)
            sb.append(i + ". " + opcoes[i-1] + "\n");
        return sb.toString(); // não acrescentamos numOpcoes à String, porque as opções já estão numeradas e dá para perceber quantas opções existem.
    }
    
    /** @return Valor do hash code deste menu. */
    public int hashCode(){
        int hash = 7;
        
        for(int i = 0; i < numOpcoes; ++i)
            hash = 31*hash + opcoes[i].hashCode();
        hash = 31*hash + op;
        return hash;
    } 
}
