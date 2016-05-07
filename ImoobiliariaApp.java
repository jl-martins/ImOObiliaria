import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.System.out;
import static java.lang.System.err;

/**
 * Write a description of class ImoobiliariaApp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ImoobiliariaApp
{
    private static Imoobiliaria imoobiliaria;
    private static MenuMain menu;
    
    private static final String[] opcoesMenu = {
        "Registar novo utilizador.",
        "Iniciar sessão.",
        "Fechar sessão.",
        "Registar um imóvel (opção de Vendedor).",
        "Obter as 10 últimas consultas (opção de Vendedor).",
        "Alterar o estado de um imóvel (opção de Vendedor).",
        "Obter imóveis com mais de N consultas (opção de Vendedor)",
        "Listar imóveis de um certo tipo.",
        "Obter lista dos imóveis habitáveis.",
        "Obter correspondência entre imóveis e vendedores.",
        "Marcar um imóvel como favorito (opção de Comprador).",
        "Consultar imóveis favoritos (opção de Comprador).",
        "Iniciar leilão (opção de Vendedor).",
        "Adicionar comprador ao leilão atual (opção de Vendedor)."
        "Encerrar leilão (opção de Vendedor)."
    };
    
    public static void main(String[] args){
        menu = new MenuMain(opcoesMenu);
        imoobiliaria = initApp();
        
        do{
            int numOpcao = -1; // inicialização com valor arbitrário diferente de 0 para não sairmos da aplicação de getOpcao() falhar
            menumain.executa();
            try{
                numOpcao = menumain.getOpcao();
                if(numOpcao >= 1 && numOpcao <= 15){
                    Method m = ImoobiliariaApp.class.getDeclaredMethod("opcao" + numOpcao, null);
                    Object o = m.invoke(null, null); // 1º null indica que o método é static. O 2º indica que este não tem argumentos.
                }
                else if(numOpcao > 15)
                    err.println("Opção inválida!\n");
            }
            catch(InputMismatchException | NoSuchElementException){
                Scanner input = new Scanner(System.in);
                sc.nextLine(); // consome os carateres que ficaram no stdin.
                err.println("O valor que introduziu é inválido!");
            }
        } while(numOpcao != 0);
        
        try{ // Tenta gravar os dados em ficheiro.
            imoobiliaria.gravaObj("imoobiliaria.dat");
            imoobiliaria.log("log.txt");
        }
        catch(IOException e){err.println("Não foi possível gravar os dados!");}
        out.println("A sair da aplicação...");
    }

    /*              switch(opcao) {
                        case 1: // regista utilizador
                            break;
                        case 2: // inicia sessão
                            break;
                        case 3: // fecha sessão
                            break;
                        // Vendedor
                        case 4: // regista imóvel
                            break;
                        case 5: // obtem as 10 últimas consultas
                            break;
                        case 6: // altera estado de um imóvel
                            break;
                        case 7: // topImoveis
                            break;
                        // Todos os utilizadores
                        case 8: // lista imóveis de um dado tipo
                            break;
                        case 9: // obtem lista dos imóveis habitáveis
                            break;
                        case 10: // obtem mapeamento entre os imóveis e respectivos vendedores.
                            break;
                        // Compradores
                        case 11: // marca um imóvel como favorito
                            break;
                        case 12: // consulta imóveis favoritos
                            break;
                        // Leilões
                        case 13: // Iniciar leilão (funcionalidade do vendedor)
                            break;
                        case 14: // Adicionar comprador 
                            break;
                        case 15: // Encerra o leilão
                            break;
                    }
    */
    /**
     * Pede ao utilizador para introduzir o seu email, nome, password, morada, data de nascimento
     * e tipo de conta de utilizador pretendida. Se os dados introduzidos forem válidos e não
     * existir qualquer utilizador com o email introduzido, regista o novo utilizador na Imoobiliaria.
     */
    private static void opcao1(){
        Scanner input = new Scanner(System.in);
        String email, nome, password, morada, strData;
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("aaaa-mm-dd");
        LocalDate dataNascimento;
        TipoUtilizador tipoUtilizador;
        Utilizador novoUtilizador;
        
        try{
            out.print("Email: ");
            email = input.nextLine();
            if(validadorEmail.validar(email) == true){
                out.print("Nome: ");
                nome = input.nextLine();
                out.print("Password: ");
                password = input.nextLine();
                out.print("Morada: ");
                morada = input.nextLine();
                out.print("Data de nascimento (aaaa-mm-dd): ");
                strData = input.nextLine();
                dataNascimento = LocalDate.parse(strData, formatador);
                out.print("Tipo de utilizador [Comprador/Vendedor]: ");
                tipoUtilizador = TipoUtilizador.fromString(input.nextLine());
                switch(tipoUtilizador){
                    case COMPRADOR:
                        novoUtilizador = new Comprador(email, nome, password, morada, dataNascimento);
                        break;
                    case VENDEDOR:
                        novoUtilizador = new Vendedor(email, nome, password, morada, dataNascimento);
                        break;
                }
                imoobiliaria.registaUtilizador(novoUtilizador);
            }
            else // o email introduzido é inválido
                err.print("O email: '" + email + "' é inválido.\n");  
        }
        catch(NoSuchElementException){err.println("Erro: Introduziu uma linha em branco.");}
        catch(DateTimeParseException){err.println("Erro: A data de nascimento '" + dataNascimento + "' é inválida.\nFormato esperado: aaaa-mm-dd.");}
        catch(UtilizadorExistenteException msg){err.println(msg);}
    }
    
    /** Inicia sessão. */
    private static void opcao2(){
        Scanner input = new Scanner(System.in);
        String email, password;
        
        try{
            out.print("Email: ");
            email = input.nextLine();
            out.print("Password: ");
            password = input.nextLine();
        }
        catch(NoSuchElementException){err.println("Erro: Introduziu uma linha em branco."); return;}
        
        try{
            imoobiliaria.iniciaSessao(email, password);
        }
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    /** Fecha sessão. */
    private static void opcao3(){
        imoobiliaria.fechaSessao();
    }
    
    /** Regista um imóvel. */
    private static void opcao4(){}
    
    private static void opcao5(){}
   
    private static void opcao6(){}
    
    private static void opcao7(){}
    
    private static void opcao8(){}
    
    private static void opcao9(){}
    
    private static void opcao10(){}
    
    private static void opcao11(){}
    
    private static void opcao12(){}
    
    private static void opcao13(){}
    
    private static void opcao14(){}
    
    private static void opcao15(){}
    
    public static Imoobiliaria initApp(){
        Imoobiliaria imoobiliaria = null;
        
        try{
            imoobiliaria = leImoobiliaria("imoobiliaria.dat");
        }
        catch(IOException e){
            imoobiliaria = new Imoobiliaria();
            err.println("Não foi possível ler os dados!\nErro de leitura.");
        }
        catch(ClassNotFoundException){
            imoobiliaria = new Imoobiliaria();
            err.println("Não foi possível ler os dados!\nFicheiro com formato desconhecido.");
        }
        catch(ClassCastException e){
            imoobiliaria = new Imoobiliaria();
            err.println("Não foi possível ler os dados!\nErro de formato.");
        }
    }
        return imoobiliaria;
    }
    
    // Mudar para a Imoobiliaria
    public void gravaImoobiliaria(String fich) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fich));
        
        oos.writeObject(imoobiliaria);
        oos.flush();
        oos.close();
    }
    
    // Mudar para a Imoobiliaria
    public static Imoobiliaria leImoobiliaria(String fich) throws IOException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich));
        
        Imoobiliaria imoobiliaria = (Imoobiliaria) ois.readObject();
        ois.close();
        return imoobiliaria;
    }
    
}
