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
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import static java.lang.System.err;
import java.util.Random;

public class Imoobiliaria implements Serializable
{
    Map<String, Utilizador> utilizadores; // Map que a cada email faz corresponder o respetivo Utilizador
    Map<String, Imovel> imoveis; // Map que a cada id (valido) de imóvel faz corresponder o respetivo objeto da classe Imovel
    Utilizador utilizadorAutenticado;
    Leilao leilao;

    /** Construtor por omissão. */
    public Imoobiliaria(){
        utilizadores = new HashMap<String, Utilizador>();
        imoveis = new HashMap<String, Imovel>();
    }

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

    public static Imoobiliaria leObj(String fich) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich));

        Imoobiliaria imoobiliaria = (Imoobiliaria) ois.readObject();
        ois.close();
        return imoobiliaria;
    }

    /**
     * Regista um utilizador, quer vendedor, quer comprador.
     * @param utilizador Utilizador a registar
     * @throws UtilizadorExistenteException se a Imoobiliaria já tiver um utilizador que tem o mesmo e-mail que aquele que foi passado para o método.
     */
    public void registarUtilizador(Utilizador utilizador) throws UtilizadorExistenteException{
        String email = utilizador.getEmail();

        if(utilizadores.containsKey(email))
            throw new UtilizadorExistenteException("Já existe um utilizador com o e-mail: " + email);
        else
            utilizadores.put(email, utilizador);
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
    
    /** @return String com o nome da classe do utilizador autenticado, se existir um; null c.c. */
    public String classUtilizadorAutenticado(){
        return (utilizadorAutenticado != null) ? utilizadorAutenticado.getClass().getSimpleName() : null;
    }
    
    public void confirmaVendedorAutenticado() throws SemAutorizacaoException{
        if(!(utilizadorAutenticado instanceof Vendedor))
            throw new SemAutorizacaoException("O utilizador atual não é um Vendedor Autenticado.");
    }

    public void confirmaCompradorAutenticado() throws SemAutorizacaoException{
        if(!(utilizadorAutenticado instanceof Comprador))
            throw new SemAutorizacaoException("O utilizador atual não é um Comprador Autenticado");
    }

    public void registaImovel(Imovel im) throws SemAutorizacaoException, ImovelExisteException{
        confirmaVendedorAutenticado();
        String idImovel = im.getId();
        Vendedor vendedor = (Vendedor) utilizadorAutenticado;

        if(imoveis.containsKey(idImovel))
            throw new ImovelExisteException("Já existe um imóvel com o id: " + idImovel);
        else{
            imoveis.put(idImovel, im);
            vendedor.poeAVenda(idImovel);                
        }

    }

    public List<Consulta> getConsultas() throws SemAutorizacaoException{
        // Esta verificação também é feita em registaImovel(). Pensar em fazer um método privado que realize esta verificação!!!
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

    public void setEstado(String idImovel, String estado) 
    throws ImovelInexistenteException, SemAutorizacaoException, EstadoInvalidoException {
        confirmaVendedorAutenticado();
        Imovel imv = imoveis.get(idImovel);
        
        if(imv == null){
            throw new ImovelInexistenteException("O imóvel " + idImovel + " não está registado.");
        }
        Vendedor vendedor = (Vendedor) utilizadorAutenticado;

        if(!vendedor.registouImovel(idImovel))
            throw new SemAutorizacaoException("O utilizador atual não tem permissões para alterar o estado do imóvel " + idImovel);
        EstadoImovel estadoImovel = EstadoImovel.fromString(estado);
        vendedor.alteraEstadoImovel(idImovel, estadoImovel);
        imv.setEstado(estadoImovel);     
    } 

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
                imv.registaConsulta(new Consulta(utilizadorAutenticado));
                resultados.add(imv.clone());
            }
        }
        return resultados;
    }

    public List <Habitavel> getHabitaveis (int preco){
        Collection<Imovel> todosImoveis = imoveis.values();
        List<Habitavel> resultados = new ArrayList<Habitavel>();
        
        for(Imovel imv : todosImoveis){
            if(imv instanceof Habitavel && imv.getPrecoPedido() <= preco){
                imv.registaConsulta(new Consulta(utilizadorAutenticado));
                resultados.add((Habitavel) imv.clone());
            }
        }
        return resultados;
    }

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

    public void setFavorito(String idImovel) throws ImovelInexistenteException, SemAutorizacaoException{
        confirmaCompradorAutenticado();
        Comprador comprador = (Comprador) utilizadorAutenticado;
        
        if(!imoveis.containsKey(idImovel))
            throw new ImovelInexistenteException("O imóvel em questão não existe.");
        comprador.setFavorito(idImovel);        
    }

    public TreeSet<Imovel> getFavoritos() throws SemAutorizacaoException{
        confirmaCompradorAutenticado();
        Comprador comprador = (Comprador) utilizadorAutenticado;
        TreeSet<String> idsFavoritos = comprador.getFavoritos();
        TreeSet<Imovel> favoritos = new TreeSet<Imovel>(new ComparadorPorPreco());
        Imovel imv;

        for(String id : idsFavoritos){
            imv = imoveis.get(id);
            imv.registaConsulta(new Consulta(utilizadorAutenticado));
            favoritos.add(imv.clone());
        }
        return favoritos;
    }    

    /* API de Leiloes */
    public void iniciaLeilao(Imovel im, int horas) throws SemAutorizacaoException{
        if(!(utilizadorAutenticado instanceof Vendedor) || !((Vendedor) utilizadorAutenticado).vendeImovel(im.getId()))
            throw new SemAutorizacaoException("O Utilizador atual não tem autorização para iniciar o Leilão deste imóvel.");
        leilao = new Leilao(im.getId(), utilizadorAutenticado.getEmail(), horas);
    }

    public void adicionaComprador(String idComprador, int limite, int incrementos, int minutos) throws LeilaoTerminadoException {
        if(leilao.terminouLeilao())
            throw new LeilaoTerminadoException("O leilão terminou, não pode inserir mais compradores");
        leilao.registaCompradorLeilao(idComprador, limite, incrementos, minutos);
    }

    public Comprador encerraLeilao() throws SemAutorizacaoException{
        if(!(utilizadorAutenticado instanceof Vendedor) || !utilizadorAutenticado.getEmail().equals(leilao.getResponsavel()))
            throw new SemAutorizacaoException("Este utilizador não tem permissões para encerrar o leilao");
        String idVencedor = leilao.simulaLeilao();
        return (Comprador) utilizadores.get(idVencedor);
    }

    public int hashCode(){
        int hash = 7;
        hash = 31*hash + ((utilizadores == null) ? 0 : utilizadores.hashCode());
        hash = 31*hash + ((imoveis == null) ? 0 : imoveis.hashCode());
        hash = 31*hash + ((utilizadorAutenticado == null) ? 0 : utilizadorAutenticado.hashCode());
        return hash;
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
        sb.append("Utilizadores: ");
        sb.append(utilizadores.values().toString());
        sb.append("\n Imoveis: ");
        sb.append(imoveis.values().toString());
        sb.append("\n Utilizador Atual ");
        sb.append(((utilizadorAutenticado == null)? "n.a." : utilizadorAutenticado.toString()) + "\n");
        return sb.toString();
    }
}
