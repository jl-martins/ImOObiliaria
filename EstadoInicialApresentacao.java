import java.time.LocalDate;
import java.util.Random;
import java.util.TreeSet;
import java.util.Set;
import java.util.ArrayList;

/**
 * Write a description of class EstadoInicialApresentação here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EstadoInicialApresentacao
{
    private static LocalDate geraDataNascimento(){
        final long LIMITE_INF = LocalDate.of(1910, 1, 1).toEpochDay(); /* passar para o correspondente em long para calcular so uma vez */
        final long LIMITE_SUP = LocalDate.of(2000, 1, 1).toEpochDay();
        Random random = new Random();
        long dataAleatoria = LIMITE_INF + (long)(random.nextDouble()*(LIMITE_SUP - LIMITE_INF));
        return LocalDate.ofEpochDay(dataAleatoria);
    }

    private static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private static String geraNome(){
        StringBuilder sb = new StringBuilder();
        int quantosNomes = randInt(1,3);
        int quantosApelidos = randInt(1,3);
        Set<String> nomesSemReps = new TreeSet<String>();
        String nome;

        String nomesHomem[] = {"João", "Carlos", "Luís", "José", "Esteves", "Miguel", "Rui", "Rafael", "Tiago", "André", "Ricardo", "Álvaro", "Ângelo",
                "Fernando", "Alexandre", "Manuel", "Gil", "António", "Fábio"};
        String nomesMulher[] = {"Cláudia", "Elsa", "Ana", "Margarida", "Raquel", "Sara", "Matilde", "Cátia", "Bárbara", "Filipa", "Andreia", "Carla", 
                "Catarina", "Joaquina", "Beatriz", "Alexandra", "Fernanda"};
        String apelidos[] = {"Passos", "Dias", "Aguiar", "Mota", "Mendes", "Pereira", "Martins", "Silva", "Sousa", "Cardoso", "Roriz", "Machado", 
                "Guedes", "Baião", "Cunha"};

        if(randInt(0,1) == 0){
            for(int i = 0; i < quantosNomes; i++){
                nome = nomesMulher[randInt(0, nomesMulher.length - 1)];
                if(nomesSemReps.add(nome))
                    sb.append(nome + " ");
            }
        } else {
            for(int i = 0; i < quantosNomes; i++){
                nome = nomesHomem[randInt(0, nomesMulher.length - 1)];
                if(nomesSemReps.add(nome))
                    sb.append(nome + " ");
            }        
        }
        for(int i = 0; i < quantosApelidos; i++){
            nome = apelidos[randInt(0, apelidos.length - 1)];
            if(nomesSemReps.add(nome))
                sb.append(nome + " ");
        }

        return sb.toString();
    }

    /* gera uma string com n caracteres alfanumericos */
    private static String randomAlfaNumericString(int n){
        String alfaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random rand = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int indice = rand.nextInt(alfaNum.length()); 
            res.append(alfaNum.charAt(indice));            
        }
        return res.toString();
    }

    private static String geraEmail(){
        int lenEmail = randInt(4,16);
        String servidores[] = {"@yahoo.com", "@gmail.com", "@hotmail.com", "@uminho.pt", "@outlook.com"};
        String email = randomAlfaNumericString(lenEmail).toLowerCase() + servidores[randInt(0, servidores.length -1)];
        return email;
    }

    private static Imovel geraImovel(String idImovel){
        int tipo = randInt(0, 4); 
        Imovel imv = null;
        switch(tipo){
            case 0:
            imv = (Imovel) new Moradia(idImovel, geraMorada(), randInt(100000, 400000), 100000, TipoMoradia.ISOLADA, randInt(400, 1000),
                randInt(600, 1200), randInt(400, 1000), randInt(2, 6), randInt(2, 6), randInt(1, 500));
            break;
            case 1:
            imv = (Imovel) new Apartamento(idImovel, geraMorada(), randInt(65000, 400000), 65000 , TipoApartamento.DUPLEX, randInt(100, 500),
                randInt(0, 5), randInt(0, 4), randInt(1, 500), randInt(1, 20), true);
            break;
            case 2:
            imv = (Imovel) new Loja(idImovel, geraMorada(), randInt(65000, 400000), 65000, randInt(70, 500), true, "Pastelaria", randInt(1, 500));
            break;
            case 3:
            imv = (Imovel) new Terreno(idImovel, geraMorada(), randInt(65000, 400000), 65000, randInt(1000, 10000), true,
                true, 50, 35000, true);
            break;
            case 4:
            int preco = randInt(125000, 400000);
            String morada = geraMorada();
            imv = (Imovel) new LojaHabitavel(idImovel, morada, preco, 100000, randInt(500, 1000),
                true, "Everything you want", randInt(1, 500), 
                new Apartamento(idImovel, morada, preco, 65000 , TipoApartamento.DUPLEX, randInt(100, 500),
                    randInt(0, 5), randInt(0, 4), randInt(1, 500), randInt(1, 20), true)); 
            break;
        }
        return imv;
    }

    private static String geraMorada(){
        String moradas[] = {"Jardim Zoológico da Maia", "Somewhere Over the rainbow, Way up high", "Rua dos Mancos", "Rua da Imoobiliaria", "Rua de Barros", "Rua 25 de Abril"};
        return moradas[randInt(0, moradas.length - 1)];
    }

    public static Imoobiliaria geraEstadoAleatorio(){
        Imoobiliaria imb = new Imoobiliaria();
        String passwordPadrao = "1234";
        Set<String> emails = new TreeSet<String>();
        Set<String> idsImoveis = new TreeSet<String>();

        int N_VENDEDORES = 10;
        int N_IMOVEIS_POR_VENDEDOR = 30;
        int N_COMPRADORES = 50;

        // Utilizadores comuns para ser facil testar estados 
        Vendedor vendedorPadrao = new Vendedor("a75273@uminho.pt", "João Pereira", passwordPadrao, "Rua da Imoobiliaria", LocalDate.of(1996, 12, 19));
        Comprador compradorPadrao = new Comprador("a68646@uminho.pt", "João Martins", passwordPadrao, "Rua da Imoobiliaria", LocalDate.of(1994, 8, 8)); 

        /*falta gerar imoveis aletorios para estes*/
        imb.utilizadores.put("a75273@uminho.pt", vendedorPadrao);
        imb.utilizadores.put("a68646@uminho.pt", compradorPadrao);

        try{
            imb.iniciaSessao("a75273@uminho.pt", passwordPadrao);
        }catch(SemAutorizacaoException e){
            /* ver codigo erros */
        }
        /* registo dos imoveis */
        for(int j = 0; j < N_IMOVEIS_POR_VENDEDOR; j++){
            String idImovel = randomAlfaNumericString(6);
            if(idsImoveis.add(idImovel)){
                try{
                    imb.registaImovel(geraImovel(idImovel));
                } catch(Exception e) {
                    /* ver codigo erros */
                }                        
            }
        }
        imb.fechaSessao();

        /* gera utilizadores aleatorios */        
        for(int i=1; i < N_VENDEDORES; i++){
            String randomEmail = geraEmail();
            if(emails.add(randomEmail)){
                Vendedor vendedor = new Vendedor(randomEmail, geraNome(), passwordPadrao, geraMorada(), geraDataNascimento());
                imb.utilizadores.put(randomEmail, vendedor);

                /* Evitar replicacao do codigo, fazer uma funcao propria */
                try{
                    imb.iniciaSessao(randomEmail, passwordPadrao);
                }catch(SemAutorizacaoException e){
                    /* ver codigo erros */
                }
                /* registo dos imoveis */
                for(int j = 0; j < N_IMOVEIS_POR_VENDEDOR; j++){
                    String idImovel = randomAlfaNumericString(6);
                    if(idsImoveis.add(idImovel)){
                        try{
                            imb.registaImovel(geraImovel(idImovel));
                        } catch(Exception e) {
                            /* ver codigo erros */
                        }                        
                    }
                }
                imb.fechaSessao();
            }       
        }

        for(int i=1; i < N_COMPRADORES; i++){
            String randomEmail = geraEmail();
            if(emails.add(randomEmail)){
                Comprador comprador = new Comprador(randomEmail, geraNome(), passwordPadrao, geraMorada(), geraDataNascimento()); // usamos valores sem sentido para o nome, a morada e o ano?
                imb.utilizadores.put(randomEmail, comprador);
                /* adicionar favoritos??*/
            }       
        }

        // adiciona-se um imovel aleatriamente a à lista de venda de um vendedor 

        return imb;
    }
}
