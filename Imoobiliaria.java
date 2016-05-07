import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.TreeSet;

public class Imoobiliaria
{   
    Map<String, Utilizador> utilizadores; // Map que a cada email faz corresponder o respetivo Utilizador
    Map<String, Imovel> imoveis; // Map que a cada id (valido) de imóvel faz corresponder o respetivo objeto da classe Imovel
    Utilizador utilizadorAutenticado = null;

    public static void initApp(){
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

    public void confirmaVendedorAutenticado() throws SemAutorizacaoException{
        if(!(utilizadorAutenticado instanceof Vendedor))
            throw new SemAutorizacaoException("O utilizador atual não tem permissões para alterar o Imóvel.");
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
            Collections.sort(ultimasConsultas);
            fim = ultimasConsultas.size();
            inicio = (fim >= N_CONSULTAS)? fim - N_CONSULTAS : 0;
            ultimasConsultas = ultimasConsultas.subList(inicio, fim);             
        }

        return ultimasConsultas;
    }

    public void setEstado(String idImovel, String estado) 
    throws ImovelInexistenteException, SemAutorizacaoException, EstadoInvalidoException {
        Imovel imv = imoveis.get(idImovel);
        if(imv == null){
            throw new ImovelInexistenteException("O imóvel " + idImovel + " não está registado.");
        }
        confirmaVendedorAutenticado();
        Vendedor vendedor = (Vendedor) utilizadorAutenticado;
        if(!vendedor.registouImovel(idImovel))
            throw new SemAutorizacaoException("O utilizador atual não tem permissões para alterar o Imovel.");
        EstadoImovel estadoImovel = EstadoImovel.fromString(estado);
        vendedor.alteraEstadoImovel(idImovel, estadoImovel);
        imv.setEstado(estadoImovel);     
    } 

    public Set<String> getTopImoveis(int n){
        int inicio, fim;
        Set<String> idsImoveis = new TreeSet<String>();
        List<Imovel> imoveis = new ArrayList<Imovel>();

        if(!(utilizadorAutenticado instanceof Vendedor))
            return idsImoveis;
        Vendedor vendedor = (Vendedor) utilizadorAutenticado;
        idsImoveis.addAll(vendedor.getEmVenda());
        idsImoveis.addAll(vendedor.getVendidos());
        Collections.sort(imoveis);

        fim = imoveis.size();
        inicio = (fim >= n)? fim - n : 0;
        imoveis = imoveis.subList(inicio, fim); 

        idsImoveis.clear();
        for(Imovel imv : imoveis)
            idsImoveis.add(imv.getId());
        return idsImoveis;        
    }

    public List<Imovel> getImovel(String classe, int preco){
        List<Imovel> resultados = new ArrayList<Imovel>();
        Class tipoImovel;
        try{
            tipoImovel = Class.forName(classe);
        } catch(ClassNotFoundException e){
            return resultados;
        }
        
        Collection<Imovel> todosImoveis = imoveis.values();
        for(Imovel imv : todosImoveis){
            if(tipoImovel.isInstance(imv) && imv.getPrecoPedido() < preco){
                resultados.add(imv.clone());
            }
        }
        return resultados;
    }
}
