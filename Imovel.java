import java.util.ArrayList;
import java.io.Serializable;

public abstract class Imovel implements Comparable<Imovel>, Serializable
{
    // variáveis de instância
    private String id; // id do imóvel
    private String rua;
    private EstadoImovel estado;
    private int precoPedido;
    private int precoMinimo;
    private ArrayList<Consulta> consultas;
    
    /**
     * Construtor por omissão.
     */
    public Imovel(){
        this("n/a", "n/a", 0, 0);
    }
    
    /**
     * Construtor parametrizado.
     */
    public Imovel(String id, String rua, int precoPedido, int precoMinimo){
        this.id = id;
        this.rua = rua;
        this.estado = EstadoImovel.EM_VENDA; // o estado inicial de um Imovel criado é sempre EM_VENDA.
        this.precoPedido = precoPedido;
        this.precoMinimo = precoMinimo;
        this.consultas = new ArrayList<Consulta>();
    }
    
    /**
     * Construtor de cópia.
     */
    public Imovel(Imovel imv){
        this(imv.getId(), imv.getRua(), imv.getPrecoPedido(), imv.precoMinimo);
        this.estado = imv.getEstado();
        this.consultas = imv.getConsultas();
    }
    
    /** @return id deste imóvel. */
    public String getId(){
        return id;
    }
    
    /** @return Rua em que este imóvel se situa */
    public String getRua(){
        return rua;
    }
    
    /** @return Estado deste Imovel. */
    public EstadoImovel getEstado(){
        return estado;
    }
    
    /** @return O preço pedido por este imóvel. */
    public int getPrecoPedido(){
        return precoPedido;
    }
    
    /** @return O preço mínimo deste imóvel. */
    private int getPrecoMinimo(){
        return precoMinimo;
    }
    
    public int getQuantasConsultas(){
        return consultas.size();
    }
    
    public ArrayList<Consulta> getConsultas(){
        return new ArrayList<Consulta>(consultas); // as instâncias de Consulta são imutáveis, logo não quebramos o encapsulamento.
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setRua(String rua){
        this.rua = rua;
    }
    
    public void setEstado(EstadoImovel estado){
        this.estado = estado;
    }
    
    public void setPrecoPedido(int precoPedido){
        this.precoPedido = precoPedido;
    }
    
    private void setPrecoMinimo(int precoMinimo){
        this.precoMinimo = precoMinimo;
    }
    
    public void registaConsulta(Consulta c){
        this.consultas.add(c); /* Consulta é um tipo imutável. Nao e preciso fazer cópia */
    }
    
    /* Comparação baseada no número de consultas */
    public int compareTo(Imovel imv){
        int quantasConsultas1 = this.consultas.size();
        int quantasConsultas2 = imv.getQuantasConsultas();
        
        if (quantasConsultas1 == quantasConsultas2)
            return 0;
        else if(quantasConsultas1 > quantasConsultas2)
            return 1;
        else
            return -1;
    }
    
    public abstract Imovel clone();
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || (this.getClass() != o.getClass()))
            return false;
        
        Imovel imv = (Imovel) o;
        return id.equals(imv.getId()) && rua.equals(imv.getRua()) && estado == imv.getEstado() && precoPedido == imv.getPrecoPedido() &&
               precoMinimo == imv.precoMinimo && consultas.equals(imv.getConsultas());
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("id: " + id + "\n");
        sb.append("Rua: " + rua + "\n");
        sb.append("Preço pedido pelo imóvel: " + precoPedido + "\n");
        sb.append("Número de consultas: " + consultas.size() + "\n");
        // O preço mínimo não deverá ser apresentado aos compradores, logo não o incluimos na String retornada
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = 7;
        
        hash = 31*hash + id.hashCode();
        hash = 31*hash + rua.hashCode();
        hash = 31*hash + precoPedido;
        hash = 31*hash + precoMinimo;
        hash = 31*hash + (consultas == null ? 0 : consultas.hashCode());
        return hash;
    }
}
