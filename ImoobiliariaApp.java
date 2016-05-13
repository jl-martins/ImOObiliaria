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
    private static Menu menuMain, menuComprador, menuVendedor;
    private static Menu menuTipoUtilizador, menuTipoImovel, menuSimNao;
    
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
    
    private static void carregarMenus(){
        String[] opcoesMain = {
            "Registar utilizador",
            "Iniciar sessão",
            "Listar imóveis de um certo tipo",
            "Listar imóveis habitáveis",
            "Correspondência entre imóveis e vendedores",
            "Gravar estado"
        };
        
        String[] opcoesComprador = {
            "Fechar sessão",
            "Listar imóveis de um certo tipo",
            "Listar imóveis habitáveis",
            "Correspondência entre imóveis e vendedores",
            "Marcar um imóvel como favorito",
            "Consultar imóveis favoritos",
            "Participar em leilão"
        };
        
        String[] opcoesVendedor = {
            "Fechar sessão",
            "Listar imóveis de um certo tipo",
            "Listar imóveis habitáveis",
            "Correspondência entre imóveis e vendedores",
            "Registar um imóvel",
            "Obter as 10 últimas consultas",
            "Alterar o estado de um imóvel",
            "Obter imóveis com mais de N consultas",
            "Iniciar leilão",
            "Encerrar leilão",
            "Gravar estado"
        };
        String[] opcoesSimNao = {"Sim", "Não"};
        String[] opcoesTipoUtilizador = {"Comprador", "Vendedor"};
        String[] opcoesTipoImovel = {"Moradia", "Apartamento", "Loja não habitável", "Loja habitável", "Terreno"};
        
        // o espaço inicial dos títulos é intencional (se o 3º parâmetro for true, então o menu tem opção para sair)
        menuMain = new Menu(" Menu Principal", opcoesMain, true);
        menuComprador = new Menu(" Menu Comprador", opcoesComprador, true);
        menuVendedor = new Menu(" Menu Vendedor", opcoesVendedor, true);
        menuTipoUtilizador = new Menu(" Selecionar tipo de utilizador", opcoesTipoUtilizador, false);
        menuTipoImovel = new Menu(" Selecionar tipo de imóvel", opcoesTipoImovel, false);
        menuSimNao = new Menu(" Resposta Sim/Não", opcoesSimNao, false);
    }
    
    private static void carregarDados(){imoobiliaria = Imoobiliaria.initApp();}
    
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
                switch(numOpcao){
                    case 1:
                        registarUtilizador();
                        break;
                    case 2:
                        // iniciarSessao() devolve -1 se a autenticação falhar. Se a autenticação tiver sucesso e o utilizador
                        // pretender sair é devolvido 0. Se o utilizador fechar a sessão mas não quiser sair, é devolvido 1.
                        numOpcao = iniciarSessao();
                        break;
                    case 3:
                        listarImoveisTipo();
                        break;
                    case 4:
                        listarHabitaveis();
                        break;
                    case 5:
                        mapImovelVend();
                        break;
                    case 6:
                        gravarEstado();
                        break;
                }
                if(numOpcao != 0)
                    enterParaContinuar();
            }
        } while(numOpcao != 0);
        
        gravarEstado();
        out.println("Volte sempre!");
    }
    
    private static int menuComprador(){
        int numOpcao;
        
        do{
            menuComprador.executa();
            numOpcao = menuComprador.getOpcao();
            switch(numOpcao){
                case 0: // se o comprador pretender sair, primeiro terminamos a sessão
                case 1:
                    fecharSessao();
                    break;
                case 2:
                    listarImoveisTipo();
                    break;
                case 3:
                    listarHabitaveis();
                    break;
                case 4:
                    mapImovelVend();
                    break;
                case 5:
                    registarFavorito();
                    break;
                case 6:
                    apresentarFavoritos();
                    break;
                case 7:
                    adicionarComprador();
                    break;
                case 8:
                    gravarEstado();
                    break;
            }
            if(numOpcao > 1) // se a opção não é "sair" ou "fechar a sessão"
                enterParaContinuar();
        } while(numOpcao > 1); // enquanto o comprador não pretender sair ou fechar a sessão
        
        return numOpcao;
    }
    
    private static int menuVendedor(){
        int numOpcao;
        
        do{
            menuVendedor.executa();
            numOpcao = menuVendedor.getOpcao();
            switch(numOpcao){
                case 0: // se o vendedor quiser sair, primeiro fechamos a sessão
                case 1:
                    fecharSessao();
                    break;
                case 2:
                    listarImoveisTipo();
                    break;
                case 3:
                    listarHabitaveis();
                    break;
                case 4:
                    mapImovelVend();
                    break;
                case 5:
                    registarImovel();
                    break;
                case 6:
                    ultimasConsultas();
                    break;
                case 7:
                    alterarEstadoImovel();
                    break;
                case 8:
                    topImoveis();
                    break;
                case 9:
                    iniciarLeilao();
                    break;
                case 10:
                    encerrarLeilao();
                    break;
                case 11:
                    gravarEstado();
                    break;
            }
            if(numOpcao > 1) // se a opção não é "sair" ou "fechar a sessão"
                enterParaContinuar();
        } while(numOpcao > 1); // enquanto o vendedor não pretender fechar a sessão ou sair
        
        return numOpcao;
    }
    
    public static void gravarEstado(){
        try{
            imoobiliaria.gravaObj("Imoobiliaria.ser");
            // IMPLEMENTAR ==> imoobiliaria.log("log.txt");
            out.println("-> Estado gravado com sucesso!");
        }
        catch(IOException e){err.println("Não foi possível gravar os dados!");}
    }
    
    /**
     * Pede ao utilizador para introduzir o seu email, nome, password, morada, data de nascimento
     * e tipo de conta de utilizador pretendida. Se os dados introduzidos forem válidos e não
     * existir qualquer utilizador com o email introduzido, regista o novo utilizador na Imoobiliaria.
     */
    private static void registarUtilizador(){
        int numOpcao; // número da opção do menu
        Scanner input = new Scanner(System.in);
        String email, nome, password, morada, strData; // strData - String que irá guardar a representação textual da data.
        DateTimeFormatter formatador = DateTimeFormatter.ISO_LOCAL_DATE; // data na forma aaaa-mm-dd
        LocalDate dataNascimento = null;
        Utilizador novoUtilizador = null;
        
        menuTipoUtilizador.executa();
        numOpcao = menuTipoUtilizador.getOpcao();
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
                out.println("-> Utilizador registado com sucesso!");
            }
            else // o email introduzido é inválido
                err.print("O email: '" + email + "' é inválido.\n");  
        }
        catch(NoSuchElementException e){err.println("Erro: Introduziu uma linha em branco.");}
        catch(DateTimeParseException e){err.println("Erro: A data de nascimento '" + dataNascimento + "' é inválida.\nFormato esperado: aaaa-mm-dd.");}
        catch(UtilizadorExistenteException e){err.println(e.getMessage());}
    }
    
      /** 
       * Inicia a sessão e em caso de sucesso invoca o menu de comprador ou de vendedor, consoante o tipo de utilizador autenticado.
       * @return -1 se a autenticação falhar; 0 se a autenticação for bem sucedida e o utilizador quiser sair no fim da sessão;
       *         1 se o utilizador pretender apenas fechar a sessão e voltar ao menu principal.
       */
    private static int iniciarSessao(){
        Scanner input = new Scanner(System.in);
        String email, password;
        int r = -1;
        
        try{
            out.print("Email: ");
            email = input.nextLine();
            out.print("Password: ");
            password = input.nextLine();
            imoobiliaria.iniciaSessao(email, password);
            out.println("-> Sessão iniciada iniciada com sucesso!");
            enterParaContinuar();
            // só chegamos a este switch, se o utilizador conseguiu autenticar-se
            switch(imoobiliaria.classUtilizadorAutenticado()){
                case "Comprador":
                    r = menuComprador();
                    break;
                case "Vendedor":
                    r = menuVendedor();
                    break;
            }
        }
        catch(NoSuchElementException e){err.println("Erro: Introduziu uma linha em branco.");}
        catch(SemAutorizacaoException e){err.println(e.getMessage());}

        return r;
    }
    
    /** Fecha sessão. */
    private static void fecharSessao(){
        imoobiliaria.fechaSessao();
        out.println("-> Sessão fechada com sucesso!");
    }
    
    /** Regista um imóvel. */
    private static void registarImovel(){
        String id, rua;
        int precoPedido, precoMinimo, numOpcao;
        Scanner input = new Scanner(System.in);
        Imovel im = null;
        
        menuTipoImovel.executa();
        numOpcao = menuTipoImovel.getOpcao();
        try{
            out.print("ID do imóvel: ");
            id = input.nextLine();
            out.print("Rua: ");
            rua = input.nextLine();
            out.print("Preço pedido: ");
            precoPedido = input.nextInt(); input.nextLine(); // lê um int e consome o newline que ficou no buffer do stdin
            out.print("Preço mínimo: ");
            precoMinimo = input.nextInt(); input.nextLine();
                
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
            imoobiliaria.registaImovel(im);
            out.println("-> Registo do imóvel '" + id + "' efetuado com sucesso!");
        }
        catch(ImovelExisteException e){err.println(e.getMessage());}
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
        catch(InputMismatchException e){err.println("Input inválido.");}
        // FALTA APANHAR EXCEÇÕES GERADAS QUANDO UMA STRING NAO PODE SER CONVERTIDA PRA ENUM
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
        areaImplantacao = input.nextInt(); input.nextLine();
        out.print("Área total coberta: ");
        areaTotal = input.nextInt(); input.nextLine();
        out.print("Área do terreno envolvente: ");
        areaEnv = input.nextInt(); input.nextLine();
        out.print("Número de quartos: ");
        numQuartos = input.nextInt(); input.nextLine();
        out.print("Número de WCs: ");
        numWCs = input.nextInt(); input.nextLine();
        out.print("Número da porta: ");
        numDaPorta = input.nextInt(); input.nextLine();
        
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
        areaTotal = input.nextInt(); input.nextLine();
        out.print("Número de quartos: ");
        numQuartos = input.nextInt(); input.nextLine();
        out.print("Número de WCs: ");
        numWCs = input.nextInt(); input.nextLine();
        out.print("Número da porta: ");
        numDaPorta = input.nextInt(); input.nextLine();
        out.print("Andar: ");
        andar = input.nextInt(); input.nextLine();
        
        menuSimNao.setTitulo("O apartamento tem garagem?");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
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
        area = input.nextInt(); input.nextLine();
        
        menuSimNao.setTitulo("A loja tem WC?");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
        temWC = (numOpcao == 1);
        
        out.print("Tipo de negócio: ");
        tipoNegocio = input.nextLine();
        out.print("Número da porta: ");
        numDaPorta = input.nextInt(); input.nextLine();
        return new Loja(id, rua, precoPedido, precoMinimo, area, temWC, tipoNegocio, numDaPorta);
    }
    
    /** Pede ao utilizador para introduzir os dados relativos a um terreno e, em caso de sucesso, devolve o Terreno criado. */
    private static Terreno leDadosTerreno(String id, String rua, int precoPedido, int precoMinimo){
        Scanner input = new Scanner(System.in);
        int area, numOpcao;
        double diamCanalizacoes, maxKWh;
        boolean terrenoHab, terrenoArm, temRedeEsgotos;
        
        out.print("Área do terreno: ");
        area = input.nextInt(); input.nextLine();
        
        menuSimNao.setTitulo("O terreno é apropriado para construção de habitação?");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
        terrenoHab = (numOpcao == 1);
        
        menuSimNao.setTitulo("O terreno é apropriado para construção de armazéns?");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
        terrenoArm = (numOpcao == 1);
        
        out.print("Diâmetro das canalizações (em mm): "); 
        diamCanalizacoes = input.nextDouble(); input.nextLine();
        out.print("kWh máximos: ");
        maxKWh = input.nextDouble(); input.nextLine();
        
        menuSimNao.setTitulo("O terreno tem rede de esgotos?");
        menuSimNao.executa();
        numOpcao = menuSimNao.getOpcao();
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
    private static void ultimasConsultas(){
        try{
            List<Consulta> consultas = imoobiliaria.getConsultas();
            
            if(consultas == null || consultas.isEmpty())
                out.println("Não existem consultas para apresentar.");
            else{
                for(Consulta c : consultas){
                    out.println("--------------------------------------------------------------------------------------------------");
                    out.print(c.toString());
                    out.println("--------------------------------------------------------------------------------------------------");
                }
            }
        }
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    /** Altera o estado de um imóvel, de acordo com as ações feitas sobre ele. */
    private static void alterarEstadoImovel(){
        Scanner input = new Scanner(System.in);
        String idImovel, novoEstado;
        
        try{
            out.print("Id do imóvel cujo estado pretende alterar: ");
            idImovel = input.nextLine();
            out.print("Novo estado do imóvel: ");
            novoEstado = input.nextLine();
            imoobiliaria.setEstado(idImovel, novoEstado);
            out.println("-> Estado do imóvel " + idImovel + " alterado com sucesso para: " + novoEstado);
        }
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
        catch(ImovelInexistenteException e){err.println(e.getMessage());}
        catch(EstadoInvalidoException e){err.println(e.getMessage());}
    }
    
    /** 
     * Lê um inteiro N e, se o utilizador autenticado for um vendedor, apresenta 
     * o conjunto dos seus imóveis que têm mais do que N consultas.
     */
    private static void topImoveis(){
        Scanner input = new Scanner(System.in);
        int N;
        Set<String> setIds;
        
        try{
            out.print("Limite inferior do número consultas dos imóveis a apresentar: ");
            N = input.nextInt(); input.nextLine();
            setIds = imoobiliaria.getTopImoveis(N);
            if(setIds == null || setIds.isEmpty())
                out.println("Não existem imóveis com mais do que " + N + " consultas.");
            else{
                out.println("-> IDs dos imóveis com mais do que " + N + " consultas\n");
                for(String id : setIds) // ! se este setIds puder ser null, temos que alterar este ciclo
                    out.print(id);
            }
        }
        catch(SemAutorizacaoException e){err.print(e.getMessage());}
    }
    
    /** Lê um tipo e um preço e apresenta a lista de todos os imóveis desse tipo, até ao preço especificado. */
    private static void listarImoveisTipo(){
        Scanner input = new Scanner(System.in);
        String tipo;
        int precoMaximo;
        List<Imovel> l;
        
        try{
            out.print("Tipo de imóvel: ");
            tipo = input.nextLine();
            out.print("Preço máximo dos imóveis a consultar: ");
            precoMaximo = input.nextInt(); input.nextLine();
            l = imoobiliaria.getImovel(tipo, precoMaximo);
            
            if(l == null || l.isEmpty())
                out.println("Não existem imóveis do tipo '" + tipo + "', com preço não superior a " + precoMaximo + "€");
            else{
                for(Imovel im : l){
                    out.println("--------------------------------------------------------------------------------------------------");
                    out.print(im.toString());
                    out.println("--------------------------------------------------------------------------------------------------");
                }
            }
        }
        catch(InputMismatchException e){err.println("Input inválido.");}
        // !falta fazer catch da excepção atirada quando o tipo de Imovel é invalido.
    }
    
    /** Apresenta a lista de todos os imóveis habitáveis. */
    private static void listarHabitaveis(){
        Scanner input = new Scanner(System.in);
        int precoMaximo;
        
        try{
            out.print("Preço máximo dos imóveis habitáveis: ");
            precoMaximo = input.nextInt(); input.nextLine();
            List<Habitavel> l = imoobiliaria.getHabitaveis(precoMaximo);
            
            if(l == null || l.isEmpty())
                out.println("Não existem imóveis habitáveis com preço abaixo de " + precoMaximo + "€");
            else{
                for(Habitavel hab : l){
                    out.println("--------------------------------------------------------------------------------------------------");
                    out.print(hab.toString());
                    out.println("--------------------------------------------------------------------------------------------------");
                }
            }
        }
        catch(InputMismatchException e){err.println("Input inválido.");}
    }
    
    /** Apresenta um mapeamento entre imóveis e respectivos vendedores. */
    private static void mapImovelVend(){
        Map<Imovel, Vendedor> mapeamentoImoveis = imoobiliaria.getMapeamentoImoveis();
        
        if(mapeamentoImoveis == null || mapeamentoImoveis.isEmpty())
            out.println("A imobiliária ainda não tem imóveis nem vendedores.");
        else{
            for(Map.Entry<Imovel, Vendedor> entrada : mapeamentoImoveis.entrySet()){
                out.println("--------------------------------------------------------------------------------------------------");
                Imovel im = entrada.getKey();
                Vendedor vendedor = entrada.getValue();
                out.println(im.toString());
                out.println("-> Vendedor:\nNome: " + vendedor.getNome() + "\nEmail: " + vendedor.getEmail());
                out.println("--------------------------------------------------------------------------------------------------");
            }
        }
    }
    
    /** Marca um imóvel como favorito (opção de comprador). */
    private static void registarFavorito(){
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
    private static void apresentarFavoritos(){
        TreeSet<Imovel> favoritos;
        
        try{
            favoritos = imoobiliaria.getFavoritos();
            
            if(favoritos == null || favoritos.isEmpty())
                out.println("Ainda não tem nenhum imóvel favorito.");
            else{
                for(Imovel fav : favoritos){
                    out.println("--------------------------------------------------------------------------------------------------");
                    out.print(fav.toString());
                    out.println("--------------------------------------------------------------------------------------------------");
                }
            }
        }
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    private static void iniciarLeilao(){
        
    }
    
    private static void adicionarComprador(){}
    
    private static void encerrarLeilao(){}
}
