import java.util.ArrayList;

public abstract class Imovel implements Comparable<Imovel>
{
    // variáveis de instância
    private String id; // id do imóvel
    private String rua;
    private EstadoImovel estado;
    private double precoPedido;
    private double precoMinimo;
    private int quantasConsultas;
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
    public Imovel(String id, String rua, double precoPedido, double precoMinimo){
        this.id = id;
        this.rua = rua;
        this.estado = EstadoImovel.EM_VENDA; // o estado inicial de um Imovel criado é sempre EM_VENDA.
        this.precoPedido = precoPedido;
        this.precoMinimo = precoMinimo;
        this.quantasConsultas = 0; // o Imovel começa sempre por ter 0 consultas.
        this.consultas = new ArrayList<Consulta>();
    }
    
    /**
     * Construtor de cópia.
     */
    public Imovel(Imovel imv){
        this(imv.getId(), imv.getRua(), imv.getPrecoPedido(), imv.precoMinimo);
        this.estado = imv.getEstado();
        this.quantasConsultas = 0;
        this.consultas = new ArrayList<Consulta>();
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
    public double getPrecoPedido(){
        return precoPedido;
    }
    
    /** @return O preço mínimo deste imóvel. */
    private double getPrecoMinimo(){
        return precoMinimo;
    }
    
    public int getQuantasConsultas(){
        return quantasConsultas;
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
    
    public void setPrecoPedido(double precoPedido){
        this.precoPedido = precoPedido;
    }
    
    private void setPrecoMinimo(double precoMinimo){
        this.precoMinimo = precoMinimo;
    }
    
    public void registaConsulta(Consulta c){
        this.consultas.add(c); /* Consulta e um tipo imutavel. Nao e preciso fazer copia*/
        this.quantasConsultas++;
    }
    
    /* Comparação baseada no número de consultas */
    public int compareTo(Imovel imv){
        if (this.quantasConsultas == imv.quantasConsultas)
            return 0;
        else if(this.quantasConsultas > imv.quantasConsultas)
            return 1;
        else return -1;
    }
    
    public abstract Imovel clone();
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || (this.getClass() != o.getClass()))
            return false;
        
        Imovel imv = (Imovel) o;
        return id.equals(imv.getId()) && rua.equals(imv.getRua()) && estado == imv.getEstado() && precoPedido == imv.getPrecoPedido() &&
               precoMinimo == imv.precoMinimo && quantasConsultas == imv.quantasConsultas && consultas.equals(imv.getConsultas());
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("id: " + id + "\n");
        sb.append("Rua: " + rua + "\n");
        sb.append("Preço pedido pelo imóvel: " + precoPedido + "\n");
        // O preço mínimo não deverá ser apresentado aos compradores, logo não o incluimos na String retornada
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = 7;
        long aux;
        
        hash = 31*hash + id.hashCode();
        hash = 31*hash + rua.hashCode();
        aux = Double.doubleToLongBits(precoPedido);
        hash = 31*hash + (int) (aux^(aux >>> 32));
        aux = Double.doubleToLongBits(precoMinimo);
        hash = 31*hash + (int) (aux^(aux >>> 32));
        return hash;
    }
}
