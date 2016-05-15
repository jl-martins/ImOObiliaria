/**
 * Classe de geradores do estado inicial da ImoobiliariaApp.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */

import java.time.LocalDate;
import java.util.Random;
import java.util.TreeSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;

public class GeradorEstado
{
    private final int N_VENDEDORES = 15;
    private final int N_COMPRADORES = 50;
    private final int N_IMOVEIS_POR_VENDEDOR = 15;
    private final String passwordPadrao = "1234";

    private Imoobiliaria imb = new Imoobiliaria();
    private Set<String> emails = new TreeSet<String>();
    private Set<String> idsImoveis = new TreeSet<String>();

    private Random random = new Random();

    public void resetEstado(){
        imb = new Imoobiliaria();
        emails = new TreeSet<String>();
        idsImoveis = new TreeSet<String>();
    }

    private LocalDate geraDataNascimento(){
        long LIMITE_INF = LocalDate.of(1910, 1, 1).toEpochDay(); /* passar para o correspondente em long para calcular so uma vez */
        long LIMITE_SUP = LocalDate.of(2000, 1, 1).toEpochDay();
        long dataAleatoria = LIMITE_INF + (long)(random.nextDouble()*(LIMITE_SUP - LIMITE_INF));
        return LocalDate.ofEpochDay(dataAleatoria);
    }

    private int randInt(int min, int max) {
        int randomNum = random.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private String geraNome(){

        int quantosNomes = randInt(1,3);
        int quantosApelidos = randInt(1,3);
        String nome, nomesPossiveis[];

        StringBuilder sb = new StringBuilder();
        Set<String> nomesSemReps = new TreeSet<String>();

        String nomesHomem[] = {"João", "Carlos", "Luís", "José", "Esteves", "Miguel", "Rui", "Rafael", "Tiago", "André", 
                "Ricardo", "Álvaro", "Ângelo", "Fernando", "Alexandre", "Manuel", "Gil", "António", "Fábio", "Pedro", "Francisco"};
        String nomesMulher[] = {"Sara", "Cláudia", "Elsa", "Ana", "Margarida", "Raquel", "Matilde", "Cátia", "Bárbara", "Filipa", "Andreia", "Carla", 
                "Catarina", "Joaquina", "Beatriz", "Alexandra", "Fernanda", "Francisca", "Luísa", "Cátia", "Alexandrine", "Rita"};
        String apelidos[] = {"Passos", "Dias", "Aguiar", "Mota", "Mendes", "Pereira", "Martins", "Silva", "Sousa", "Cardoso", "Roriz", "Machado", 
                "Guedes", "Baião", "Cunha", "Costa", "Oliveira", "Marques"};

        nomesPossiveis = (randInt(0,1) == 0)? nomesMulher : nomesHomem;

        for(int i = 0; i < quantosNomes; i++){
            nome = nomesPossiveis[randInt(0, nomesPossiveis.length - 1)];
            if(nomesSemReps.add(nome))
                sb.append(nome + " ");
        }

        for(int i = 0; i < quantosApelidos; i++){
            nome = apelidos[randInt(0, apelidos.length - 1)];
            if(nomesSemReps.add(nome))
                sb.append(nome + " ");
        }

        return sb.toString();
    }

    /* gera uma string com n caracteres alfanumericos */
    private String randomAlfaNumericString(int n){
        String alfaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int indice = random.nextInt(alfaNum.length()); 
            res.append(alfaNum.charAt(indice));            
        }
        return res.toString();
    }

    private String geraEmail(){
        int lenEmail = randInt(4,12);
        String servidores[] = {"@yahoo.com", "@gmail.com", "@hotmail.com", "@uminho.pt", "@outlook.com", "@sapo.pt"};
        String email = randomAlfaNumericString(lenEmail).toLowerCase() + servidores[randInt(0, servidores.length-1)];
        return email;
    }

    private Imovel geraImovel(String idImovel) throws TipoInvalidoException{
        int tipo = randInt(0, 4); 
        Imovel imv = null;
        switch(tipo){
            case 0:
                imv = (Imovel) new Moradia(idImovel, geraMorada(), randInt(100000, 400000), 100000, geraTipoMoradia(), randInt(400, 1000),
                                            randInt(600, 1200), randInt(400, 1000), randInt(2, 6), randInt(2, 6), randInt(1, 500));
                break;
            case 1:
                imv = (Imovel) new Apartamento(idImovel, geraMorada(), randInt(65000, 400000), 65000 , geraTipoApartamento(), randInt(100, 500),
                                                randInt(0, 5), randInt(0, 4), randInt(1, 500), randInt(1, 20), true);
                break;
            case 2:
                imv = (Imovel) new Loja(idImovel, geraMorada(), randInt(65000, 400000), 65000, randInt(70, 500), true, "N.A.", randInt(1, 500));
                break;
            case 3:
                imv = (Imovel) new Terreno(idImovel, geraMorada(), randInt(65000, 400000), 65000, 
                                            randInt(1000, 10000), true, true, 50, 35000, true);
                break;
            case 4:
                int preco = randInt(125000, 400000);
                String morada = geraMorada();
                Apartamento apartamento = new Apartamento(idImovel, morada, preco, 65000 , geraTipoApartamento(), randInt(100, 500),
                                                          randInt(0, 5), randInt(0, 4), randInt(1, 500), randInt(1, 20), true); 
                
                imv = (Imovel) new LojaHabitavel(idImovel, morada, preco, 100000, randInt(500, 1000), true, "N.A.", randInt(1, 500), apartamento);                             
                break;
        }
        return imv;
    }

    private String geraMorada(){
        String moradas[] = {"Jardim Zoológico da Maia", "Somewhere Over the rainbow, Way up high", "Rua dos Mancos", "Rua da Imoobiliaria", "Rua de Barros", "Rua 25 de Abril"};
        return moradas[randInt(0, moradas.length - 1)];
    }

    /* Neste caso, é impossivel lançar a excepçao */
    private TipoMoradia geraTipoMoradia() throws TipoInvalidoException{
        String[] tipoMoradia = {"Isolada", "Geminada", "Banda", "Gaveto"};
        return TipoMoradia.fromString(tipoMoradia[random.nextInt(4)]);
    }

    /* Neste caso, é impossivel lançar a excepçao */
    private TipoApartamento geraTipoApartamento() throws TipoInvalidoException{
        String[] tipoApartamento = {"SIMPLES", "DUPLEX", "TRIPLEX"};
        return TipoApartamento.fromString(tipoApartamento[random.nextInt(4)]);
    }

    private void registaImoveisVendedor(String email, String password){
        imb.fechaSessao(); // certifica-se que nao está ninguém logado
        try{
            imb.iniciaSessao(email, password);
            for(int j = 0; j < N_IMOVEIS_POR_VENDEDOR; j++){
                String idImovel = randomAlfaNumericString(6);
                if(idsImoveis.add(idImovel)){ //se o id do Imovel nao foi registado
                    try{
                        Imovel imv = geraImovel(idImovel);
                        imb.registaImovel(imv);
                    } catch(Exception e) {
                        idsImoveis.remove(idImovel);
                    }                        
                }
            }
            imb.fechaSessao();
        }catch(SemAutorizacaoException e){}
    }

    private Set<String> subsetIdsImoveis(){
        ArrayList<String> ids = new ArrayList<String>(idsImoveis);
        Collections.shuffle(ids);
        return new TreeSet<String>(ids.subList(0,random.nextInt(ids.size() + 1)));        
    }

    private void registaFavoritosComprador(String email, String password){
        imb.fechaSessao(); 
        try{
            imb.iniciaSessao(email, password);
            Set<String> favoritos = subsetIdsImoveis();
            for(String fav : favoritos){
                try{ 
                    imb.setFavorito(fav);
                } catch(Exception e){}
            }
            imb.fechaSessao();
        }catch(SemAutorizacaoException e){}
    }

    public Imoobiliaria geraEstadoAleatorio(){

        // Utilizadores comuns para ser facil testar estados 
        Vendedor vendedorPadrao = new Vendedor("a75273@alunos.uminho.pt", "João Pereira", passwordPadrao, "Rua da Imoobiliaria", LocalDate.of(1996, 12, 19));
        Comprador compradorPadrao = new Comprador("a68646@alunos.uminho.pt", "João Martins", passwordPadrao, "Rua da Imoobiliaria", LocalDate.of(1994, 8, 8)); 

        /* Utilizadores comuns a todas as sessoes aleatorias */
        imb.utilizadores.put("a75273@alunos.uminho.pt", vendedorPadrao);
        imb.utilizadores.put("a68646@alunos.uminho.pt", compradorPadrao);
      
        /* gera vendedores aleatorios e os seus imoveis */   
        registaImoveisVendedor("a75273@alunos.uminho.pt", passwordPadrao);
        for(int i=1; i < N_VENDEDORES; i++){
            String randomEmail = geraEmail();
            if(emails.add(randomEmail)){
                Vendedor vendedor = new Vendedor(randomEmail, geraNome(), passwordPadrao, geraMorada(), geraDataNascimento());
                imb.utilizadores.put(randomEmail, vendedor);
                registaImoveisVendedor(randomEmail, passwordPadrao);
            }       
        }

        /* gera compradores e os seus favoritos */
        registaFavoritosComprador("a68646@alunos.uminho.pt", passwordPadrao);   
        for(int i=1; i < N_COMPRADORES; i++){
            String randomEmail = geraEmail();
            if(emails.add(randomEmail)){
                Comprador comprador = new Comprador(randomEmail, geraNome(), passwordPadrao, geraMorada(), geraDataNascimento()); // usamos valores sem sentido para o nome, a morada e o ano?
                imb.utilizadores.put(randomEmail, comprador);
                registaFavoritosComprador(randomEmail, passwordPadrao);
            }       
        }
        return imb;
    }
}
