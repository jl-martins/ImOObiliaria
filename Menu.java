/**
 * Classe de objetos do tipo menu.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;
import static java.lang.System.out;

final public class Menu
{   
    private String titulo;
    private String separador; // separa o título das opções e a última opção da prompt
    private String idUtilizador;
    private final String[] opcoes;
    private boolean temOpcaoSair;
    private int op;
    
    /**
     * Construtor por omissão
     * (declarado como privado para não ser possível construir menus vazios)
     */
    private Menu(){
        titulo = separador = idUtilizador = null;
        opcoes = null;
        temOpcaoSair = false;
        op = -1;
    }   
    
    /**
     * Construtor parametrizado.
     * @param opcoes Opções do menu a criar.
     */
    public Menu(String titulo, String[] opcoes, boolean temOpcaoSair){
        this.titulo = titulo;
        this.opcoes = new String[opcoes.length];
        System.arraycopy(opcoes, 0, this.opcoes, 0, opcoes.length);
        this.separador = geraSeparador(titulo, opcoes); // gera um separador adequado a este menu.
        this.temOpcaoSair = temOpcaoSair;
        this.idUtilizador = null;
    }
    
    /**
     * Construtor de cópia.
     * @param menu Menu a copiar.
     */
    public Menu(Menu menu){
        this(menu.getTitulo(), menu.getOpcoes(), menu.getTemOpcaoSair());
        setIdUtilizador(menu.getIdUtilizador());
    }
    
    /** @return Título deste menu. */
    public String getTitulo(){
        return titulo;
    }
    
    /** @return Array de String com as opções deste menu. */
    public String[] getOpcoes(){
        String[] copia = new String[opcoes.length];
        System.arraycopy(opcoes, 0, copia, 0, opcoes.length);
        return copia;
    }
    
    /** @return true se o menu tiver opção de sair. */
    public boolean getTemOpcaoSair(){
        return temOpcaoSair;
    }
    
    /** @return id do utilizador do menu se este tiver sido definido; null caso contrário. */
    public String getIdUtilizador(){
        return idUtilizador;
    }
    
    /** @return Número da opção atual deste menu. */
    public int getOpcao(){
        return op;
    }
    
    /** Altera o titulo deste menu, se a String passada como parâmetro for diferente de null. */
    public void setTitulo(String titulo){
        if(titulo != null){
            this.titulo = titulo;
            separador = geraSeparador(titulo, opcoes); // recalcula o separador
        }
    }
    
    /** Altera o id do utilizador que está a usar o menu. */
    public void setIdUtilizador(String id){
        this.idUtilizador = id;
    }
    
    /** Imprime as opções deste menu. */
    public void executa(){
        do{
            apresentaMenu();
            op = lerOpcao();
        } while(op == -1);
    }
    
    /** Apresenta este menu no stdout. */
    private void apresentaMenu(){
        out.println(separador);
        out.println(titulo);
        out.println(separador);
        for(int i = 0; i < opcoes.length; ++i)
            out.printf("%2d. %s\n", i+1, opcoes[i]);
        
        if(temOpcaoSair)
            out.println(" 0. Sair");
        out.println(separador);
    }
    
    /* Gera o separador a colocar entre a última opção e a prompt. */
    private static String geraSeparador(String titulo, String[] opcoes){
        int maxLen = titulo.length();
        
        for(int i = 0; i < opcoes.length; ++i)
            if((opcoes[i].length() + 4) > maxLen) // o número da opção seguido do carater '.' e de um espaço correspondem a 4 caratéres
                maxLen = opcoes[i].length() + 4;

        return new String(new char[maxLen+5]).replace("\0", "*"); // string com '-' repetido (maxLen+5) vezes
    }
    
    /** 
     * Lê uma opção do stdin. Se a opção introduzida for inválida, apresenta
     * uma mensagem de erro e lê outra opção se não devolve a opção lida.
     * @return A opção lida.
     */
    private int lerOpcao(){
        int op;
        Scanner input = new Scanner(System.in);
        
        if(idUtilizador != null)
            out.print("(" + idUtilizador + ")");
        
        out.print(" $ ");
        try {
            op = input.nextInt(); input.nextLine(); // lê um inteiro e consome o \n que ficou no buffer do stdin. 
        }
        catch(InputMismatchException e){
            op = -1;
            input.nextLine(); // consome a linha que ficou no buffer do stdin 
        }
        
        if(op > opcoes.length || (temOpcaoSair && op < 0) || (!temOpcaoSair && op < 1)){
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
        
        return titulo.equals(menu.getTitulo()) && Arrays.equals(opcoes, menu.getOpcoes()) && temOpcaoSair == menu.getTemOpcaoSair();
    }
    
    /** @return Representação textual dos campos deste menu. */
    public String toString(){
        StringBuilder sb = new StringBuilder("Menu:\n");
        
        sb.append("Título: " + titulo);
        sb.append("\nOpções:\n");
        for(int i = 0; i < opcoes.length; ++i)
            sb.append(opcoes[i]).append("\n");
        
        if(temOpcaoSair)
            sb.append("Sair\n");
        sb.append("Número de opções: " + opcoes.length);
        
        if(idUtilizador != null)
            sb.append("ID do utilizador atual: " + idUtilizador);
        sb.append("Opção atual: " + op);
        return sb.toString();
    }
    
    /** @return Valor do hash code deste menu. */
    public int hashCode(){
        int hash = 7;
        
        for(int i = 0; i < opcoes.length; ++i)
            hash = 31*hash + opcoes[i].hashCode();
        hash = 31*hash + op;
        hash = 31*hash + (temOpcaoSair ? 1 : 0);
        hash = 31*hash + (idUtilizador == null ? 0 : idUtilizador.hashCode());
        return hash;
    } 
}
