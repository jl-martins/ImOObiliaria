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
 * Classe principal do projeto Imoobiliaria.
 * 
 * @author Grupo 12
 * @version 07/05/2016
 */
public class ImoobiliariaApp
{
    private static Imoobiliaria imoobiliaria;
    private static Menu menuMain, menuTipoUtilizador, menuTipoImovel;
    
    public static void main(String[] args){
        carregarMenus();
        carregarDados();
        
        do{
            menuMain.executa();
            numOpcao = menuMain.getOpcao();
            if(numOpcao > 0){ // o resto das validações do número da opção são feitas na classe Menu
                Method m = ImoobiliariaApp.class.getDeclaredMethod("opcao" + numOpcao, null);
                m.invoke(null, null); // 1º null indica que o método é static. O 2º indica que este não tem argumentos.
            }
        } while(numOpcao != 0);
        
        try{
            imoobiliaria.gravaObj("imoobiliaria.dat");
            imoobiliaria.log("log.txt");
        }
        catch(IOException e){err.println("Não foi possível gravar os dados!");}
        out.println("A sair da aplicação...");
    }
    
    private static void carregarMenus(){
        String[] opcoesMain = {
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
            "Adicionar comprador ao leilão atual (opção de Vendedor).",
            "Encerrar leilão (opção de Vendedor)."
        };
        String[] opcoesTipoUtilizador = {
            "Adicionar comprador.",
            "Adicionar vendedor."
        };
        String[] opcoesTipoImovel = {
            "Registar uma moradia.",
            "Registar um apartamento.",
            "Registar uma loja não habitável.",
            "Registar uma loja habitável.",
            "Registar um terreno."
        };
        menuMain = new Menu(opcoesMain);
        menuTipoUtilizador = new Menu(opcoesTipoUtilizador);
        menuTipoImovel = new Menu(opcoesTipoImovel);
    }  
    
    public static void carregarDados(){
        imoobiliaria = initApp();
    }
    
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
        Utilizador novoUtilizador;
        int numOpcao;
        
        menuTipoUtilizador.executa();
        numOpcao = menuTipoUtilizador.getOpcao();
        if(opcao != 0){
            try{
                out.print("Email: ");
                email = input.nextLine();
                if(validadorEmail.validar(email) == true){ // ver se vale a pena criar uma EmailInvalidoException
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
                    imoobiliaria.registaUtilizador(novoUtilizador); // só chegamos aqui se todos os dados foram lidos com sucesso.
                }
                else // o email introduzido é inválido
                    err.print("O email: '" + email + "' é inválido.\n");  
            }
            catch(NoSuchElementException e){err.println("Erro: Introduziu uma linha em branco.");}
            catch(DateTimeParseException e){err.println("Erro: A data de nascimento '" + dataNascimento + "' é inválida.\nFormato esperado: aaaa-mm-dd.");}
            catch(UtilizadorExistenteException e){err.println(e.getMessage());}
        }
        else // o utilizador optou por sair
            out.println("Registo cancelado.\nA voltar ao menu principal.");
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
        }
        catch(NoSuchElementException e){err.println("Erro: Introduziu uma linha em branco.");}
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    /** Fecha sessão. */
    private static void opcao3(){
        imoobiliaria.fechaSessao();
    }
    
    /** Regista um imóvel. */
    private static void opcao4(){
        String id, rua;
        int precoPedido, precoMinimo, numOpcao;
        Scanner input = new Scanner(System.in);
        Imovel im;
        
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
                imoobiliaria.registaImovel
            }
            catch(ImovelExisteException e){err.println(e.getMessage());}
            catch(SemAutorizacaoException e){err.println(e.getMessage());}
            catch(InputMismatchException e){err.println("Input inválido.");}
            // FALTA APANHAR EXCEÇÕES GERADAS QUANDO UMA STRING NAO PODE SER CONVERTIDA PRA ENUM
        }
        else // o utilizador optou por sair
            out.println("Registo cancelado.\nA voltar ao menu principal.");
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
        int numDaPorta, andar;
        boolean temGaragem;
        
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
        out.print("O apartamento tem garagem [S/N]: ");
        // COMPLETAR!
        return new Apartamento(id, rua, precoPedido, precoMinimo, tipo, areaTotal,
                               numQuartos, numWCs, numDaPorta, andar, temGaragem);
    }
    
    /** Pede ao utilizador para introduzir os dados relativos a uma loja e, em caso de sucesso, devolve a Loja criada. */
    private static Loja leDadosLoja(String id, String rua, int precoPedido, int precoMinimo){
        Scanner input = new Scanner(System.in);
        int area, numDaPorta;
        String tipoNegocio;
        boolean temWC;
        
        out.print("Área da loja: ");
        area = input.nextInt();
        out.print("A loja tem WC [S/N]: ");
        // COMPLETAR!
        out.print("Tipo de negócio: ");
        tipoNegocio = input.nextLine();
        out.print("Número da porta: ");
        numDaPorta = input.nextInt();
        return new Loja(id, rua, precoPedido, precoMinimo, area, temWC, tipoNegocio, numDaPorta);
    }
    
    /** Pede ao utilizador para introduzir os dados relativos a um terreno e, em caso de sucesso, devolve o Terreno criado. */
    private static Terreno leDadosTerreno(String id, String rua, int precoPedido, int precoMinimo){
        Scanner input = new Scanner(System.in);
        int area;
        boolean terrenoHab, terrenoArm;
        double diamCanalizacoes, maxKWh;
        boolean temRedeEsgotos;
        
        out.print("Área do terreno: ");
        area = input.nextInt();
        out.print("O terreno é apropriado para construção de habitação? [S/N]: ");
        // COMPLETAR!
        out.print("O terreno é apropriado para construção de armazéns? [S/N]: ");
        // COMPLETAR!
        out.print("Diâmetro das canalizações (em mm): ");
        diamCanalizacoes = input.nextDouble();
        out.print("kWh máximos: ");
        numDaPorta = input.nextDouble();
        out.print("O terreno tem rede de esgotos? [S/N]: ");
        // COMPLETAR!
        return new Terreno(id, rua, precoPedido, precoMinimo, area, terrenoHab, 
                           terrenoArm, diamCanalizacoes, maxKWh, temRedeEsgotos);
    }
    
    /** Pede ao utilizador para introduzir os dados relativos a uma loja habitável e, em caso de sucesso, devolve o LojaHabitavel criada. */
    private static LojaHabitavel leDadosLojaHabitavel(String id, String rua, int precoPedido, int precoMinimo){
        Loja loja = leDadosLoja();
        Apartamento apartamento = leDadosApartamento();
        
        return new LojaHabitavel(loja, apartamento); // ADICIONAR CONSTRUTOR A LOJA HABITAVEL!
    }
    
    /** Apresenta as 10 últimas consultas (opção de vendedor). */
    private static void opcao5(){
        try{
            List<Consulta> consultas = imoobiliaria.getConsultas();
            for(Consulta c : consultas) // se consultas puder ser null, temos que mudar este ciclo.
                out.println(c.toString());
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
        
        try{
            out.print("Limite inferior do número consultas dos imóveis a consultar: ");
            N = input.nextInt();
            setIds = getTopImoveis(N);
            out.println("IDs dos imóveis com mais do que N consultas\n");
            for(String id : setIds) // ! se este setIds puder ser null, temos que alterar este ciclo
                out.println(id);
        }
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
    /** Lê um tipo e um preço e apresenta a lista de todos os imóveis desse tipo, até ao preço especificado. */
    private static void opcao8(){
        Scanner input = new Scanner(System.in);
        String tipo;
        int precoMinimo;
        List<Imovel> l;
        
        try{
            out.print("Tipo de imóvel: ");
            tipo = input.nextLine();
            out.print("Preço mínimo: ");
            precoMinimo = input.nextInt();
            l = imoobiliaria.getImovel(tipo, precoMinimo);
            
            for(Imovel im : l)
                out.println(im.toString());
        }
        catch(InputMismatchException | NoSuchElementException e){err.println("Input inválido.");}
        // !falta fazer catch da excepção atirada quando o tipo de Imovel é invalido.
    }
    
    /** Apresenta a lista de todos os imóveis habitáveis. */
    private static void opcao9(){
        Scanner input = new Scanner(System.in);
        int precoMinimo;
        
        try{
            out.print("Preço acima do qual pretende consultar imóveis habitáveis: ");
            precoMinimo = input.nextInt();
            List<Habitavel> l = imoobiliaria.getHabitaveis(precoMinimo);
            for(Habitavel hab : l)
                out.println(hab.toString()); // podemos usar o toString() logo ou temos que fazer cast para um tipo de Imovel???
        }
        catch(InputMismatchException | NoSuchElementException){err.println("Input inválido.");}
    }
    
    /** Apresenta um mapeamento entre imóveis e respectivos vendedores. */
    private static void opcao10(){
        Map<Imovel, Vendedor> mapeamentoImoveis = imoobiliaria.getMapeamentoImoveis();
        
        for(Map.Entry<Imovel, Vendedor> entrada : mapeamentoImoveis){
            // COMPLETAR!
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
            imoobiliaria.getFavoritos();
            for(Imovel fav : favoritos)
                out.print(fav.toString());
        }
        catch(SemAutorizacaoException e){err.println(e.getMessage());}
    }
    
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
