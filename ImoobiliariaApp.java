import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import static java.lang.System.out;
import static java.lang.System.err;

/**
 * Classe principal do projeto Imoobiliaria.
 * 
 * @author Grupo 12
 * @version 07/05/2016
 */
public class ImoobiliariaApp
{
    private static Imoobiliaria imoobiliaria;
    private static Menu menuMain; // menu principal
    private static Menu menuTipoUtilizador, menuTipoImovel; // menus para selecionar o tipo de utilizador e o tipo de imóvel, respetivamente
    private static Menu menuSimNao; // menu para ler respostas do tipo sim/nao
    
    public static void splashScreen(){
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        try{
            bw.write("*********************************************************************************************************************\n");
            bw.write("*   __   ___  ___    ______     ______    ______    __   __       __       ___       ______       __       ___      *\n");
            bw.write("*  |  | |   \\/   |  /  __  \\   /  __  \\  |   _  \\  |  | |  |     |  |     /   \\     |   _  \\     |  |     /   \\     *\n");
            bw.write("*  |  | |  \\  /  | |  |  |  | |  |  |  | |  |_)  | |  | |  |     |  |    /  ^  \\    |  |_)  |    |  |    /  ^  \\    *\n");
            bw.write("*  |  | |  |\\/|  | |  |  |  | |  |  |  | |   _  <  |  | |  |     |  |   /  /_\\  \\   |      /     |  |   /  /_\\  \\   *\n");
            bw.write("*  |  | |  |  |  | |  `--'  | |  `--'  | |  |_)  | |  | |  `----.|  |  /  _____  \\  |  |\\  \\     |  |  /  _____  \\  *\n");
            bw.write("*  |__| |__|  |__|  \\______/   \\______/  |______/  |__| |_______||__| /__/     \\__\\ | _| `._\\    |__| /__/     \\__\\ *\n");
            bw.write("*                                                                                                                   *\n");
            bw.write("*********************************************************************************************************************\n");
            bw.write("                                            "); // permite centrar a mensagem de "prima ENTER para continuar"
            bw.flush();
            enterParaContinuar();
        }
        catch(IOException e){err.println("Erro de IO: Não foi possível apresentar o ecrã inicial.");}
    }
    
    private static void limparEcra(){System.out.print("\f");}
    
    private static void enterParaContinuar(){
        Scanner input = new Scanner(System.in);
        out.print("Prima ENTER para continuar... ");
        input.nextLine();
        limparEcra();
    }
    
    public static void main(String[] args){
        int numOpcao;
        splashScreen();
        carregarMenus();
        carregarDados();
        
        do{
            menuMain.executa();
            numOpcao = menuMain.getOpcao();
            limparEcra();
            if(numOpcao > 0){ // o resto das validações do número da opção são feitas na classe Menu
                try{
                    Method m = ImoobiliariaApp.class.getDeclaredMethod("opcao" + numOpcao);
                    m.invoke(null); // null indica que o método inovcado é static
                    enterParaContinuar();
                }
                catch(NoSuchMethodException e){err.println("O método escolhido não existe!");}
                catch(IllegalAccessException e){err.println("Tentativa de aceder a um método a que não tem acesso!");}
                catch(InvocationTargetException e){e.printStackTrace(); err.println("Exceção no método invocado com invoke()!");}
            }
        } while(numOpcao != 0);
        
        try{
            imoobiliaria.gravaObj("Imoobiliaria.ser");
            // IMPLEMENTAR ==> imoobiliaria.log("log.txt");
        }
        catch(IOException e){err.println("Não foi possível gravar os dados!");}
        out.println("Volte sempre!");
    }
    
    private static void carregarMenus(){
        String[] opcoesMain = {
            "Registar novo utilizador",
            "Iniciar sessão",
            "Fechar sessão",
            "Registar um imóvel (opção de Vendedor)",
            "Obter as 10 últimas consultas (opção de Vendedor)",
            "Alterar o estado de um imóvel (opção de Vendedor)",
            "Obter imóveis com mais de N consultas (opção de Vendedor)",
            "Listar imóveis de um certo tipo",
            "Obter lista dos imóveis habitáveis",
            "Obter correspondência entre imóveis e vendedores",
            "Marcar um imóvel como favorito (opção de Comprador)",
            "Consultar imóveis favoritos (opção de Comprador)",
            "Iniciar leilão (opção de Vendedor)",
            "Adicionar comprador ao leilão atual (opção de Vendedor)",
            "Encerrar leilão (opção de Vendedor)"
        };
        String[] opcoesTipoUtilizador = {
            "Comprador",
            "Vendedor"
        };
        String[] opcoesTipoImovel = {
            "Moradia",
            "Apartamento",
            "Loja não habitável",
            "Loja habitável",
            "Terreno"
        };
        String[] opcoesSimNao = {"Sim", "Não"};
        // o espaço inicial dos títulos é intencional
        menuMain = new Menu(" Menu Principal", opcoesMain);
        menuTipoUtilizador = new Menu(" Selecionar tipo de utilizador", opcoesTipoUtilizador);
        menuTipoImovel = new Menu(" Selecionar tipo de imóvel", opcoesTipoImovel);
        menuSimNao = new Menu(" Resposta Sim/Não", opcoesSimNao);
    }  
    
    private static void carregarDados(){
        imoobiliaria = Imoobiliaria.initApp();
    }
    
    /**
     * Pede ao utilizador para introduzir o seu email, nome, password, morada, data de nascimento
     * e tipo de conta de utilizador pretendida. Se os dados introduzidos forem válidos e não
     * existir qualquer utilizador com o email introduzido, regista o novo utilizador na Imoobiliaria.
     */
    private static void opcao1(){
        int numOpcao; // número da opção do menu
        Scanner input = new Scanner(System.in);
        String email, nome, password, morada, strData;
        DateTimeFormatter formatador = DateTimeFormatter.ISO_LOCAL_DATE; // data na forma aaaa-mm-dd
        LocalDate dataNascimento = null;
        Utilizador novoUtilizador = null;
        
        menuTipoUtilizador.executa();
        numOpcao = menuTipoUtilizador.getOpcao();
        if(numOpcao != 0){
            try{
                out.print("Email: ");
                email = input.nextLine();
                if(ValidadorEmail.validar(email) == true){ // ver se vale a pena criar uma EmailInvalidoException
                    out.print("Nome: ");
                    nome = input.nextLine();
                    out.print("Password: ");
                    password = input.nextLine();
                    out.print("Morada: ");
                    morada = input.nextLine();
                    out.print("Data de nascimento (aaaa-mm-dd): ");
                    strData = input.nextLine();
                    dataNascimento = LocalDate.parse(strData, formatador);
                    
                    switch(numOpcao){
                        case 1: // registar comprador
                            novoUtilizador = new Comprador(email, nome, password, morada, dataNascimento);
                            break;
                        case 2: // registar vendedor
                            novoUtilizador = new Vendedor(email, nome, password, morada, dataNascimento);
                            break;
                    }  
                    imoobiliaria.registarUtilizador(novoUtilizador); // só chegamos aqui se todos os dados foram lidos com sucesso.
                    out.println("\n-> Utilizador registado com sucesso. <-\n");
                }
                else // o email introduzido é inválido
                    err.print("O email: '" + email + "' é inválido.\n");  
            }
            catch(NoSuchElementException e){err.println("Erro: Introduziu uma linha em branco.");}
            catch(DateTimeParseException e){err.println("Erro: A data de nascimento '" + dataNascimento + "' é inválida.\nFormato esperado: aaaa-mm-dd.");}
            catch(UtilizadorExistenteException e){err.println(e.getMessage());}
        }
        else // o utilizador optou por sair
            out.println("Registo cancelado.");
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
            imoobiliaria.iniciaSessao(email, password);
            out.println("\n-> Sessão iniciada com sucesso! E-mail do utilizador autenticado: " + email + " <-\n");
        }
        catch(NoSuchElementException e){err.println("Erro: Introduziu uma linha em branco.");}
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    /** Fecha sessão. */
    private static void opcao3(){
        imoobiliaria.fechaSessao();
        out.println("-> Sessão fechada com sucesso. <-\n");
    }
    
    /** Regista um imóvel. */
    private static void opcao4(){
        String id, rua;
        int precoPedido, precoMinimo, numOpcao;
        Scanner input = new Scanner(System.in);
        Imovel im = null;
        
        menuTipoImovel.executa();
        numOpcao = menuTipoImovel.getOpcao();
        if(numOpcao != 0){
            try{
                out.print("ID do imóvel: ");
                id = input.nextLine();
                out.print("Rua: ");
                rua = input.nextLine();
                out.print("Preço pedido: ");
                precoPedido = input.nextInt();
                out.print("Preço mínimo: ");
                precoMinimo = input.nextInt();
                
                switch(numOpcao){
                    case 1:
                        im = leDadosMoradia(id, rua, precoPedido, precoMinimo);
                        break;
                    case 2:
                        im = leDadosApartamento(id, rua, precoPedido, precoMinimo);
                        break;
                    case 3:
                        im = leDadosLoja(id, rua, precoPedido, precoMinimo);
                        break;
                    case 4:
                        im = leDadosLojaHabitavel(id, rua, precoPedido, precoMinimo);
                        break;
                    case 5:
                        im = leDadosTerreno(id, rua, precoPedido, precoMinimo);
                        break;
                }
                if(im == null) // o utilizador optou por cancelar o registo
                    out.println("Registo cancelado.");
                else{
                    imoobiliaria.registaImovel(im);
                    out.println("\n-> Registo do imóvel " + id + " efetuado com sucesso. <-\n");
                }
            }
            catch(ImovelExisteException e){err.println(e.getMessage());}
            catch(SemAutorizacaoException e){err.println(e.getMessage());}
            catch(InputMismatchException e){err.println("Input inválido.");}
            // FALTA APANHAR EXCEÇÕES GERADAS QUANDO UMA STRING NAO PODE SER CONVERTIDA PRA ENUM
        }
        else // o utilizador optou por sair
            out.println("Registo cancelado.");
    }
    
    /** Pede ao utilizador para introduzir os dados relativos a uma moradia e, em caso de sucesso, devolve a Moradia criada. */
    private static Moradia leDadosMoradia(String id, String rua, int precoPedido, int precoMinimo){
        Scanner input = new Scanner(System.in);
        TipoMoradia tipo;
        int areaImplantacao, areaTotal, areaEnv;
        int numQuartos, numWCs, numDaPorta;
        
        out.print("Tipo de moradia [Isolada/Geminada/Banda/Gaveto]: ");
        tipo = TipoMoradia.fromString(input.nextLine());
        out.print("Área de implantação: ");
        areaImplantacao = input.nextInt();
        out.print("Área total coberta: ");
        areaTotal = input.nextInt();
        out.print("Área do terreno envolvente: ");
        areaEnv = input.nextInt();
        out.print("Número de quartos: ");
        numQuartos = input.nextInt();
        out.print("Número de WCs: ");
        numWCs = input.nextInt();
        out.print("Número da porta: ");
        numDaPorta = input.nextInt();
        return new Moradia(id, rua, precoPedido, precoMinimo, tipo, areaImplantacao,
                             areaTotal, areaEnv, numQuartos, numWCs, numDaPorta);
    }
    
    /** Pede ao utilizador para introduzir os dados relativos a um apartamento e, em caso de sucesso, devolve o Apartamento criado. */
    private static Apartamento leDadosApartamento(String id, String rua, int precoPedido, int precoMinimo){
        Scanner input = new Scanner(System.in);
        TipoApartamento tipo;
        int areaTotal, numQuartos, numWCs;
        int numDaPorta, andar, numOpcao;
        boolean temGaragem = false;
        
        out.print("Tipo de apartamento [Simples/Duplex/Triplex]: ");
        tipo = TipoApartamento.fromString(input.nextLine());
        out.print("Área total: ");
        areaTotal = input.nextInt();
        out.print("Número de quartos: ");
        numQuartos = input.nextInt();
        out.print("Número de WCs: ");
        numWCs = input.nextInt();
        out.print("Número da porta: ");
        numDaPorta = input.nextInt();
        out.print("Andar: ");
        andar = input.nextInt();
        
        out.println("O apartamento tem garagem?\n");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
        if(numOpcao == 0)
            return null;
        temGaragem = (numOpcao == 1);
        return new Apartamento(id, rua, precoPedido, precoMinimo, tipo, areaTotal,
                               numQuartos, numWCs, numDaPorta, andar, temGaragem);
    }
    
    /** Pede ao utilizador para introduzir os dados relativos a uma loja e, em caso de sucesso, devolve a Loja criada. */
    private static Loja leDadosLoja(String id, String rua, int precoPedido, int precoMinimo){
        Scanner input = new Scanner(System.in);
        int area, numDaPorta, numOpcao;
        String tipoNegocio;
        boolean temWC;
        
        out.print("Área da loja: ");
        area = input.nextInt();
        
        out.println("A loja tem WC?\n");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
        if(numOpcao == 0)
            return null; // permite indicar que o registo foi cancelado
        temWC = (numOpcao == 1);
        
        out.print("Tipo de negócio: ");
        tipoNegocio = input.nextLine();
        out.print("Número da porta: ");
        numDaPorta = input.nextInt();
        return new Loja(id, rua, precoPedido, precoMinimo, area, temWC, tipoNegocio, numDaPorta);
    }
    
    /** Pede ao utilizador para introduzir os dados relativos a um terreno e, em caso de sucesso, devolve o Terreno criado. */
    private static Terreno leDadosTerreno(String id, String rua, int precoPedido, int precoMinimo){
        Scanner input = new Scanner(System.in);
        int area, numOpcao;
        double diamCanalizacoes, maxKWh;
        boolean terrenoHab, terrenoArm, temRedeEsgotos;
        
        out.print("Área do terreno: ");
        area = input.nextInt();
        
        out.println("O terreno é apropriado para construção de habitação?\n");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
        if(numOpcao == 0)
            return null;
        terrenoHab = (numOpcao == 1);
        
        out.println("O terreno é apropriado para construção de armazéns?\n");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
        if(numOpcao == 0)
            return null;
        terrenoArm = (numOpcao == 1);
        out.print("Diâmetro das canalizações (em mm): ");
        diamCanalizacoes = input.nextDouble();
        out.print("kWh máximos: ");
        maxKWh = input.nextDouble();
        
        out.println("O terreno tem rede de esgotos?\n");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
        if(numOpcao == 0)
            return null;
        temRedeEsgotos = (numOpcao == 1);
        return new Terreno(id, rua, precoPedido, precoMinimo, area, terrenoHab, 
                           terrenoArm, diamCanalizacoes, maxKWh, temRedeEsgotos);
    }
    
    /** Pede ao utilizador para introduzir os dados relativos a uma loja habitável e, em caso de sucesso, devolve o LojaHabitavel criada. */
    private static LojaHabitavel leDadosLojaHabitavel(String id, String rua, int precoPedido, int precoMinimo){
        Loja loja = leDadosLoja(id, rua, precoPedido, precoMinimo);
        Apartamento apartamento = leDadosApartamento(id, rua, precoPedido, precoMinimo);
        
        return new LojaHabitavel(loja, apartamento); // ADICIONAR CONSTRUTOR A LOJA HABITAVEL!
    }
    
    /** Apresenta as 10 últimas consultas (opção de vendedor). */
    private static void opcao5(){
        try{
            List<Consulta> consultas = imoobiliaria.getConsultas();
            
            if(consultas == null || consultas.isEmpty())
                out.println("Não existem consultas para apresentar.");
            else{
                for(Consulta c : consultas) // se consultas puder ser null, temos que mudar este ciclo.
                    out.println(c.toString());
            }
        }
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    /** Altera o estado de um imóvel, de acordo com as ações feitas sobre ele. */
    private static void opcao6(){
        Scanner input = new Scanner(System.in);
        String idImovel, novoEstado;
        
        try{
            out.print("Id do imóvel cujo estado pretende alterar: ");
            idImovel = input.nextLine();
            out.print("Novo estado do imóvel: ");
            novoEstado = input.nextLine();
            imoobiliaria.setEstado(idImovel, novoEstado);
            out.println("\n-> Estado do imóvel " + idImovel + " alterado com sucesso para: " + novoEstado + "\n");
        }
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
        catch(ImovelInexistenteException e){err.println(e.getMessage());}
        catch(EstadoInvalidoException e){err.println(e.getMessage());}
    }
    
    /** 
     * Lê um inteiro N e, se o utilizador autenticado for um vendedor, apresenta 
     * o conjunto dos seus imóveis que têm mais do que N consultas.
     */
    private static void opcao7(){
        Scanner input = new Scanner(System.in);
        int N;
        Set<String> setIds;
        
        //try{
            out.print("Limite inferior do número consultas dos imóveis a apresentar: ");
            N = input.nextInt();
            setIds = imoobiliaria.getTopImoveis(N);
            if(setIds == null || setIds.isEmpty())
                out.println("Não existem imóveis com mais do que " + N + " consultas.");
            else{
                out.println("-> IDs dos imóveis com mais do que " + N + " consultas\n");
                for(String id : setIds) // ! se este setIds puder ser null, temos que alterar este ciclo
                    out.println(id);
            }
        //}
        // NOTA: Este método devia atirar a SemAutorizacaoException!!! catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    /** Lê um tipo e um preço e apresenta a lista de todos os imóveis desse tipo, até ao preço especificado. */
    private static void opcao8(){
        Scanner input = new Scanner(System.in);
        String tipo;
        int precoMaximo;
        List<Imovel> l;
        
        try{
            out.print("Tipo de imóvel: ");
            tipo = input.nextLine();
            out.print("Preço máximo dos imóveis a consultar: ");
            precoMaximo = input.nextInt();
            l = imoobiliaria.getImovel(tipo, precoMaximo);
            
            if(l == null || l.isEmpty())
                out.println("Não existem imóveis do tipo '" + tipo + "', com preço não superior a " + precoMaximo + "€");
            else{
                for(Imovel im : l)
                    out.println(im.toString());
            }
        }
        catch(InputMismatchException e){err.println("Input inválido.");}
        // !falta fazer catch da excepção atirada quando o tipo de Imovel é invalido.
    }
    
    /** Apresenta a lista de todos os imóveis habitáveis. */
    private static void opcao9(){
        Scanner input = new Scanner(System.in);
        int precoMaximo;
        
        try{
            out.print("Preço máximo dos imóveis habitáveis: ");
            precoMaximo = input.nextInt();
            List<Habitavel> l = imoobiliaria.getHabitaveis(precoMaximo);
            
            if(l == null || l.isEmpty())
                out.println("Não existem imóveis habitáveis com preço abaixo de " + precoMaximo + "€");
            else{
                for(Habitavel hab : l)
                    out.println(hab.toString());
            }
        }
        catch(InputMismatchException e){err.println("Input inválido.");}
    }
    
    /** Apresenta um mapeamento entre imóveis e respectivos vendedores. */
    private static void opcao10(){
        Map<Imovel, Vendedor> mapeamentoImoveis = imoobiliaria.getMapeamentoImoveis();
        
        for(Map.Entry<Imovel, Vendedor> entrada : mapeamentoImoveis.entrySet()){
            Imovel im = entrada.getKey();
            Vendedor vendedor = entrada.getValue();
            out.println(im.toString());
            out.println("-> Vendedor:\nNome: " + vendedor.getNome() + "\nEmail: " + vendedor.getEmail());
            out.println("--------------------------------------------------------------------------------------------------");
        }
    }
    
    /** Marca um imóvel como favorito (opção de comprador). */
    private static void opcao11(){
        Scanner input = new Scanner(System.in);
        String id;
        
        try{
            out.print("ID do imóvel a adicionar aos favoritos: ");
            id = input.nextLine();
            imoobiliaria.setFavorito(id);
        }
        catch(ImovelInexistenteException e){err.println(e.getMessage());}
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    /** Apresentar os imóveis favoritos de um comprador, ordenados por preço. */
    private static void opcao12(){
        TreeSet<Imovel> favoritos;
        
        try{
            favoritos = imoobiliaria.getFavoritos();
            
            if(favoritos == null || favoritos.isEmpty())
                out.println("Ainda não tem nenhum imóvel favorito.");
            else{
                for(Imovel fav : favoritos)
                    out.print(fav.toString());
            }
        }
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    private static void opcao13(){}
    
    private static void opcao14(){}
    
    private static void opcao15(){}
}
