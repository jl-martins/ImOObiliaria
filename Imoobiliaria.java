/**
 * Classe que representa a Imoobiliaria.
 * @author Grupo12
 * @version 
 */

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.TreeSet;
import java.util.HashMap;

import java.time.LocalDate;

import java.io.Serializable;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import static java.lang.System.err;

public class Imoobiliaria implements Serializable
{
    Map<String, Utilizador> utilizadores; // Map que a cada email faz corresponder o respetivo Utilizador
    Map<String, Imovel> imoveis; // Map que a cada id (valido) de imóvel faz corresponder o respetivo objeto da classe Imovel
    Utilizador utilizadorAutenticado;
    Leilao leilao = null;

    /** Construtor por omissão. */
    public Imoobiliaria(){
        utilizadores = new HashMap<String, Utilizador>();
        imoveis = new HashMap<String, Imovel>();
    }
    
    /** Construtor de cópia. */
    public Imoobiliaria(Imoobiliaria original){
        this.utilizadores = new HashMap<String, Utilizador>();
        this.imoveis = new HashMap<String, Imovel>();
        Collection<Imovel> imoveisAdicionar = original.imoveis.values();
        Collection<Utilizador> utilizadoresAdicionar = original.utilizadores.values();
        
        for(Imovel imv : imoveisAdicionar)
            this.imoveis.put(imv.getId(), imv.clone());
        
        for(Utilizador utilizador: utilizadoresAdicionar)
            this.utilizadores.put(utilizador.getEmail(), utilizador.clone());
        
        if(original.utilizadorAutenticado != null){
            this.utilizadorAutenticado = this.utilizadores.get(original.utilizadorAutenticado.getEmail());
        }
    }
    
    /** Inicializa e devolve uma Imoobiliaria com o estado guardado no ficheiro Imoobiliaria.ser */
    public static Imoobiliaria initApp(){
        Imoobiliaria imoobiliaria;

        try{
            imoobiliaria = Imoobiliaria.leObj("Imoobiliaria.ser");
        }
        catch(IOException e){
            imoobiliaria = new Imoobiliaria();
            err.println("Não foi possível ler os dados!\nErro de leitura.");
        }
        catch(ClassNotFoundException e){
            imoobiliaria = new Imoobiliaria();
            err.println("Não foi possível ler os dados!\nFicheiro com formato desconhecido.");
        }
        catch(ClassCastException e){
            imoobiliaria = new Imoobiliaria();
            err.println("Não foi possível ler os dados!\nErro de formato.");
        }
        return imoobiliaria;
    }
 
    /**
     * Grava a Imoobiliaria em ficheiro.
     * @param fich String com o caminho do ficheiro onde a Imoobiliaria será gravada.
     */
    public void gravaObj(String fich) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fich));
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
    
    /**
     * Lê uma Imoobiliaria a partir de um ficheiro .ser
     * @param fich Nome do ficheiro de estado.
     * @return A Imoobiliaria lida.
     */
    public static Imoobiliaria leObj(String fich) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich));

        Imoobiliaria imoobiliaria = (Imoobiliaria) ois.readObject();
        ois.close();
        return imoobiliaria;
    }
    
    /** 
     * Escreve um log com a representação textual desta Imoobiliaria.
     * @param fich Nome do ficheiro onde o log será escrito.
     * @param ap Se for true, o log será escrito a partir do final do ficheiro.
     */
    public void log(String fich, boolean ap) throws IOException{
        FileWriter fw = new FileWriter(fich, ap);
        fw.write("\n----------- LOG - LOG - LOG - LOG - LOG ----------------\n");
        fw.write(this.toString());
        fw.write("\n----------- LOG - LOG - LOG - LOG - LOG ----------------\n");
        fw.flush();
        fw.close();
    }

    /**
     * Regista um utilizador, quer vendedor, quer comprador.
     * @param utilizador Utilizador a registar
     * @throws UtilizadorExistenteException se a Imoobiliaria já tiver um utilizador que tem o mesmo e-mail que aquele que foi passado para o método.
     */
    public void registarUtilizador(Utilizador utilizador) throws UtilizadorExistenteException{
        String email = utilizador.getEmail();

        if(utilizadores.containsKey(email))
            throw new UtilizadorExistenteException("Já existe um utilizador com o e-mail '" + email + "'");
        else
            utilizadores.put(email, utilizador);
    }
    
    /**
     * Remove um utilzador, quer vendedor quer comprador, se o email e password fornecidos forem válidos.
     * @param email Email do utilizador a remover.
     * @param password Password do utilizador a remover.
     * @throws SemAutorizacaoException se o email e/ou password fornecidos forem inválidos.
     */
    public void removerUtilizador(String email, String password) throws SemAutorizacaoException{
        Utilizador utilizador = utilizadores.get(email);

        if(utilizador == null || !utilizador.validaPassword(password)) // e-mail e/ou a password inválidos
            throw new SemAutorizacaoException("E-mail e/ou palavra-passe inválido(s)");
        else // autenticação bem sucedida
            utilizadores.remove(email);
    }
            
    /**
     * Valida o acesso à aplicação, utilizando as credenciais (email e password).
     * @param email Email do utilizador.
     * @param password Password do utilizador.
     * @throws SemAutorizacaoException Se o e-mail e/ou a password for(em) inválido(s).
     */
    public void iniciaSessao(String email, String password) throws SemAutorizacaoException{
        Utilizador utilizador = utilizadores.get(email);

        if(utilizador == null || !utilizador.validaPassword(password)) // e-mail e/ou a password inválidos
            throw new SemAutorizacaoException("E-mail e/ou palavra-passe inválido(s)");
        else // autenticação bem sucedida
            utilizadorAutenticado = utilizador;
    }
    
    /** Se existir um utilizador autenticado, termina a sessão do mesmo, se não, não faz nada. */
    public void fechaSessao(){
        utilizadorAutenticado = null;
    }
    
    /** @return String com o email do utilizador autenticado, se existir um; null c.c. */
    public String emailUtilizadorAutenticado(){
        return (utilizadorAutenticado != null) ? utilizadorAutenticado.getEmail() : null;
    }
    
    /** @return String com o nome da classe do utilizador autenticado, se existir um; null c.c. */
    public String classUtilizadorAutenticado(){
        return (utilizadorAutenticado != null) ? utilizadorAutenticado.getClass().getSimpleName() : null;
    }
    
    /**
     * Confirma se o utilziador autenticado é um Vendedor.
     * @throws SemAutorizacaoException se não existir um utilizador autenticado ou se este não for um Vendedor.
     */
    public void confirmaVendedorAutenticado() throws SemAutorizacaoException{
        if(!(utilizadorAutenticado instanceof Vendedor))
            throw new SemAutorizacaoException("O utilizador atual não é um vendedor autenticado.");
    }
    
    /**
     * Confirma se o utilziador autenticado é um Comprador.
     * @throws SemAutorizacaoException se não existir um utilizador autenticado ou se este não for um Comprador.
     */
    public void confirmaCompradorAutenticado() throws SemAutorizacaoException{
        if(!(utilizadorAutenticado instanceof Comprador))
            throw new SemAutorizacaoException("O utilizador atual não é um comprador autenticado");
    }
    
    /**
     * Regista um Imovel na Imoobiliaria.
     * @param im Imovel a registar.
     * @throws SemAutorizacaoException se o utilizador autenticado não for um vendedor.
     * @throws ImovelExisteException se já existir um imóvel com o mesmo id que @code im.
     */
    public void registaImovel(Imovel im) throws SemAutorizacaoException, ImovelExisteException{
        confirmaVendedorAutenticado();
        String idImovel = im.getId();
        Vendedor vendedor = (Vendedor) utilizadorAutenticado;

        if(imoveis.containsKey(idImovel))
            throw new ImovelExisteException("Já existe um imóvel com o id '" + idImovel + "'");
        else{
            imoveis.put(idImovel, im);
            vendedor.poeAVenda(idImovel);                
        }
    }
    
    /** 
     * Remove um Imovel da Imoobiliaria, se este existir e o utilizador autenticado for o vendedor desse Imovel. 
     * @id ID do Imovel a remover.
     * @throws SemAutorizacaoException se o utilizador atual não tiver autorização para remover o Imovel.
     * @throws ImovelInexistenteException se o Imovel não existir.
     */
    public void removeImovel(String id) throws SemAutorizacaoException, ImovelInexistenteException{
        confirmaVendedorAutenticado();
        Vendedor vendedor = (Vendedor) utilizadorAutenticado;
        
        if(!vendedor.registouImovel(id))
            throw new SemAutorizacaoException("Não tem autorização para remover o imóvel que escolheu!");
        else if(imoveis.get(id) == null)
            throw new ImovelInexistenteException("Não existe nenhum imóvel com o id '" + id + "'");
        
        imoveis.remove(id);
        vendedor.removeImovel(id); // vai ao Vendedor e remove a referência ao Imovel removido da Imoobiliaria
    }
    
    /**
     * @param id ID do Imovel a devolver.
     * @return Se @code id corresponder a um Imovel, é devolvido um clone desse Imovel.
     * @throws ImovelInexistenteException se não existir nenhum Imovel correspondente a @code id.
     */
    public Imovel getImovel(String id) throws ImovelInexistenteException{
        Imovel imv = imoveis.get(id);
        if(imv == null)
            throw new ImovelInexistenteException("Não existe nenhum imóvel com o id '" + id + "'");
        
        imv.registaConsulta(new Consulta(utilizadorAutenticado, imv.getId()));
        return imv.clone();
    }
    
    /**
     * @return Lista com as 10 últimas consultas aos imóveis do vendedor autenticado.
     * @throws SemAutorizacaoException se o utilizador autenticado não for um vendedor.
     */
    public List<Consulta> getConsultas() throws SemAutorizacaoException{
        List<Consulta> ultimasConsultas = new ArrayList<Consulta>();
        Set<String> emVenda;
        final int N_CONSULTAS = 10;
        int inicio, fim;

        confirmaVendedorAutenticado();
        Vendedor vendedor = (Vendedor) utilizadorAutenticado;
        emVenda = vendedor.getEmVenda();
        for(String idImovel : emVenda){
            ultimasConsultas.addAll(imoveis.get(idImovel).getConsultas());       
        }
        Collections.sort(ultimasConsultas);
        fim = ultimasConsultas.size();
        inicio = (fim >= N_CONSULTAS)? fim - N_CONSULTAS : 0;
        ultimasConsultas = ultimasConsultas.subList(inicio, fim); 

        return ultimasConsultas;
    }
    
    /**
     * Altera o estado de um Imovel
     * @param idImovel ID do Imovel cujo estado se pretende alterar.
     * @param estado Novo estado do imóvel.
     * @throws ImovelInexistenteException se não houver nenhum Imovel correspondente a @code idImovel
     * @throws SemAutorizacaoException se o utilizador autenticado não for um vendedor.
     * @throws EstadoInvalidoException se @code estado for inválido.
     */
    public void setEstado(String idImovel, String estado) 
        throws ImovelInexistenteException, SemAutorizacaoException, EstadoInvalidoException
    {
        confirmaVendedorAutenticado();
        Imovel imv = imoveis.get(idImovel);
        
        if(imv == null){
            throw new ImovelInexistenteException("O imóvel '" + idImovel + "' não está registado.");
        }
        
        Vendedor vendedor = (Vendedor) utilizadorAutenticado;
        if(!vendedor.registouImovel(idImovel))
            throw new SemAutorizacaoException("O utilizador atual não tem permissões para alterar o estado do imóvel '" + idImovel + "'");
        
        EstadoImovel estadoImovel = EstadoImovel.fromString(estado);
        vendedor.alteraEstadoImovel(idImovel, estadoImovel);
        imv.setEstado(estadoImovel);     
    } 
    
    /**
     * Se o utilizador autenticado for um vendedor, é devolvido o conjunto dos seus
     * imóveis que têm um número de consultas superior ao valor passado como parâmetro.
     * @param n Limite inferior do número de consultas.
     * @return Conjunto dos códigos dos imóveis que têm mais do que @code N consultas.
     * @throws SemAutorizacaoException se o utilizador não for um Vendedor.
     */
    public Set<String> getTopImoveis(int n) throws SemAutorizacaoException{
        confirmaVendedorAutenticado();
        Set<String> resultados = new TreeSet<String>();
        Vendedor vendedor = (Vendedor) utilizadorAutenticado;

        Set<String> idsImoveis = vendedor.todosImoveisVendedor();
        for(String id : idsImoveis){
            Imovel imv = imoveis.get(id);
            if(imv.getQuantasConsultas() > n)
                resultados.add(id);
        }
        return resultados;
    }
    
    /** 
     * @param classe Classe dos imóveis a consultar.
     * @param preco Preço máximo dos imóveis a consultar.
     * @return List dos imóveis da classe especificada que de preço não superior a @code preco.
     */
    public List<Imovel> getImovel(String classe, int preco){
        List<Imovel> resultados = new ArrayList<Imovel>();
        Class tipoImovel;
        try{
            tipoImovel = Class.forName(classe);
        }
        catch(ClassNotFoundException e){return resultados;}

        Collection<Imovel> todosImoveis = imoveis.values();
        for(Imovel imv : todosImoveis){
            if(tipoImovel.isInstance(imv) && imv.getPrecoPedido() <= preco){
                imv.registaConsulta(new Consulta(utilizadorAutenticado, imv.getId()));
                resultados.add(imv.clone());
            }
        }
        return resultados;
    }
    
    /** @return Lista dos imóveis habitáveis de preço não superior ao valor passado como parâmetro. */
    public List <Habitavel> getHabitaveis (int preco){
        Collection<Imovel> todosImoveis = imoveis.values();
        List<Habitavel> resultados = new ArrayList<Habitavel>();
        
        for(Imovel imv : todosImoveis){
            if(imv instanceof Habitavel && imv.getPrecoPedido() <= preco){
                imv.registaConsulta(new Consulta(utilizadorAutenticado, imv.getId()));
                resultados.add((Habitavel) imv.clone());
            }
        }
        return resultados;
    }
    
    /** @return Mapeamento entre todos os imóveis e todos os vendedores. */
    public Map<Imovel, Vendedor> getMapeamentoImoveis(){
        Map<Imovel, Vendedor> mapeamento = new HashMap<Imovel,Vendedor>((int) Math.ceil(imoveis.size() / 0.75));
        Collection<Utilizador> todosUtilizadores = utilizadores.values();

        for(Utilizador usr : todosUtilizadores){
            if(usr instanceof Vendedor){
                Vendedor vendedor = (Vendedor) usr;
                Set<String> imoveisDoVendedor = vendedor.todosImoveisVendedor();
                for(String idImv : imoveisDoVendedor){
                    mapeamento.put(imoveis.get(idImv),vendedor);
                }
            }
        }
        return mapeamento;
    }
    
    /** 
     * Definir um imóvel como favorito (opção de Comprador).
     * @param idImovel id do imóvel a acrescentar aos favoritos.
     * @throws ImovelInexistenteException se @code idImovel não corresponder a nenhum Imovel.
     * @throws SemAutorizacaoException se o utilizador não for um Comprador autenticado.
     */
    public void setFavorito(String idImovel) throws ImovelInexistenteException, SemAutorizacaoException{
        confirmaCompradorAutenticado();
        Comprador comprador = (Comprador) utilizadorAutenticado;
        
        if(!imoveis.containsKey(idImovel))
            throw new ImovelInexistenteException("O imóvel em questão não existe.");
        comprador.setFavorito(idImovel);        
    }
    
    /**
     * Se o utilizador atual for um comprador autenticado, é devolvido um TreeSet com os seus imóveis favoritos.
     * @return TreeSet dos imóveis favoritos do comprador autenticado.
     * @throws SemAutorizacaoException se o utilizador atual não for um comprador autenticado.
     */
    public TreeSet<Imovel> getFavoritos() throws SemAutorizacaoException{
        confirmaCompradorAutenticado();
        Comprador comprador = (Comprador) utilizadorAutenticado;
        TreeSet<String> idsFavoritos = comprador.getFavoritos();
        TreeSet<Imovel> favoritos = new TreeSet<Imovel>(new ComparadorPorPreco());
        Imovel imv;

        for(String id : idsFavoritos){
            imv = imoveis.get(id);
            if(imv != null){ // se o Imovel não foi removido
                imv.registaConsulta(new Consulta(utilizadorAutenticado, imv.getId()));
                favoritos.add(imv.clone());
            }
        }
        return favoritos;
    }    

    /**
     * Inicia um leilão, se o utilzador atual for um vendedor e este selecionar um imóvel que lhe pertence.
     * @param idImovel ID do Imovel a leiloar.
     * @param horas Duração do leilão.
     * @throws SemAutorizacaoException se o utilizador autenticado não for um vendedor ou se o imóvel a leiloar não lhe pertencer.
     */
    public void iniciaLeilao(String idImovel, int horas) throws SemAutorizacaoException{
        if(!(utilizadorAutenticado instanceof Vendedor) || !((Vendedor) utilizadorAutenticado).vendeImovel(idImovel))
            throw new SemAutorizacaoException("O Utilizador atual não tem autorização para iniciar o Leilão deste imóvel.");
        
        leilao = new Leilao(idImovel, utilizadorAutenticado.getEmail(), horas);
    }
    
    /**
     * Adiciona um comprador ao leilão atual.
     * @param idComprador email do comprador a adicionar ao leilão atual.
     * @param limite Valor máximo que o comprador está disposto a dar.
     * @param incrementos Incrementos a efetuar.
     * @param minutos Intervalo de tempo (em minutos) entre licitações.
     * @throws SemAutorizacaoException se o utilizador atual não for um comprador autenticado.
     * @throws LeilaoTerminadoException se não existir um leilão a decorrer.
     */
    public void adicionaComprador(String idComprador, int limite, int incrementos, int minutos)
        throws SemAutorizacaoException, LeilaoTerminadoException
    {
        confirmaCompradorAutenticado();
        
        if(leilao == null) 
            throw new LeilaoTerminadoException("Não há nenhum leilão ativo neste momento.");
        if(leilao.terminouLeilao())
            throw new LeilaoTerminadoException("O leilão terminou, não pode inserir mais compradores");
        leilao.registaCompradorLeilao(idComprador, limite, incrementos, minutos);
    }
    
    /**
     * Encerra um leilão.
     * @throws LeilaoTerminadoException se não estiver a decorrer um leilão.
     * @throws SemAutorizacaoException se o utilizador atual não for o responsável pelo leilão atual.
     */
    public Comprador encerraLeilao() throws LeilaoTerminadoException, SemAutorizacaoException{
        if(leilao == null)
            throw new LeilaoTerminadoException("De momento não está a decorrer nenhum leilão.");
        if(!(utilizadorAutenticado instanceof Vendedor) || !utilizadorAutenticado.getEmail().equals(leilao.getResponsavel()))
            throw new SemAutorizacaoException("Este utilizador não tem permissões para encerrar o leilão.");
        
        String idVencedor = leilao.simulaLeilao();
        return (Comprador) utilizadores.get(idVencedor);
    }

    public Imoobiliaria clone(){
        Imoobiliaria copia = new Imoobiliaria(this);
        return copia;
    }

    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        Imoobiliaria imb = (Imoobiliaria) o;
        return this.imoveis.equals(imb.imoveis) &&
        this.utilizadores.equals(imb.utilizadores) &&
        this.utilizadorAutenticado.equals(imb.utilizadorAutenticado);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n----------Utilizadores----------\n");
        for(Utilizador u : utilizadores.values())
            sb.append(u.toString()).append("\n");
        sb.append("\n------------Imoveis------------\n");
        for(Imovel im : imoveis.values())
            sb.append(im.toString()).append("\n");
            
        sb.append("\n----------Utilizador Atual----------\n");
        sb.append(((utilizadorAutenticado == null)? "n.a." : utilizadorAutenticado.toString()) + "\n");
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = 7;
        hash = 31*hash + ((utilizadores == null) ? 0 : utilizadores.hashCode());
        hash = 31*hash + ((imoveis == null) ? 0 : imoveis.hashCode());
        hash = 31*hash + ((utilizadorAutenticado == null) ? 0 : utilizadorAutenticado.hashCode());
        return hash;
    }
}
