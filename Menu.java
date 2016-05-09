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
    private final String titulo;
    private final String[] opcoes;
    private final int numOpcoes;
    private final String separador; // separa a última opção da prompt
    private int op;
    
    /**
     * Construtor por omissão
     * (declarado como privado para não ser possível construir menus vazios)
     */
    private Menu(){
        titulo = separador = null;
        opcoes = null;
        numOpcoes = 0;
        op = -1;
    }   
    
    /**
     * Construtor parametrizado.
     * @param opcoes Opções do menu a criar.
     */
    public Menu(String titulo, String[] opcoes){
        this.titulo = titulo;
        this.numOpcoes = opcoes.length;
        this.opcoes = new String[numOpcoes];
        this.separador = geraSeparador(titulo, opcoes); // gera um separador adequado a este menu.
        System.arraycopy(opcoes, 0, this.opcoes, 0, numOpcoes);
    }
    
    /**
     * Construtor de cópia.
     * @param menu Menu a copiar.
     */
    public Menu(Menu menu){
        this(menu.getTitulo(), menu.getOpcoes());
    }
    
    /** @return Título deste menu. */
    public String getTitulo(){
        return titulo;
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
        out.println(titulo);
        out.println(separador);
        for(int i = 0; i < numOpcoes; ++i)
            out.printf("%2d. %s\n", i+1, opcoes[i]);
        out.println(" 0. Sair");
        out.println(separador);
    }
    
    /* Gera o separador a colocar entre a última opção e a prompt. */
    private static String geraSeparador(String titulo, String[] opcoes){
        int maxLen = titulo.length();
        int numOpcoes = opcoes.length;
        
        for(int i = 0; i < numOpcoes; ++i)
            if((opcoes[i].length() + 4) > maxLen) // o número da opção seguido do carater '.' e de um espaço correspondem a 4 caratéres
                maxLen = opcoes[i].length() + 4;

        return new String(new char[maxLen]).replace("\0", "*"); // string com '-' repetido maxLen vezes
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
        
        return titulo.equals(menu.getTitulo()) && Arrays.equals(opcoes, menu.getOpcoes()); // se os arrays forem iguais, têm o mesmo número de opções.
    }
    
    /** @return Representação textual dos campos deste menu. */
    public String toString(){
        StringBuilder sb = new StringBuilder("Menu:\n");
        
        sb.append("Título: " + titulo);
        sb.append("\nOpções:\n");
        for(int i = 0; i < numOpcoes; ++i)
            sb.append(opcoes[i]).append("\n");
        sb.append("Sair\n");
        sb.append("Número de opções: " + numOpcoes);
        sb.append("Opção atual: " + op);
        return sb.toString();
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
