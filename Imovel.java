public abstract class Imovel
{
    // variáveis de instância
    private String id; // id do imóvel
    private String rua;
    private String estado;
    private double precoPedido;
    private double precoMinimo;
    private int quantasConsultas;    
    
    /**
     * Construtor por omissão.
     */
    public Imovel(){
        this("n/a", "n/a", "n/a", 0, 0, 0);
    }
    
    /**
     * Construtor parametrizado.
     */
    public Imovel(String id, String rua, String estado, double precoPedido, double precoMinimo, int quantasConsultas){
        this.id = id;
        this.rua = rua;
        this.estado = estado;
        this.precoPedido = precoPedido;
        this.precoMinimo = precoMinimo;
        this.quantasConsultas = 0;
    }
    
    /**
     * Construtor de cópia.
     */
    public Imovel(Imovel imv){
        this(imv.getId(), imv.getRua(), imv.getEstado(), imv.getPrecoPedido(), imv.precoMinimo, imv.quantasConsultas);
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
    public String getEstado(){
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
    
    public void setId(String id){
        this.id = id;
    }
    
    public void setRua(String rua){
        this.rua = rua;
    }
    
    public void setPrecoPedido(double precoPedido){
        this.precoPedido = precoPedido;
    }
    
    private void setPrecoMinimo(double precoMinimo){
        this.precoMinimo = precoMinimo;
    }
    
    public void consultaImovel(){
        this.quantasConsultas++;
    }
    
    public abstract Imovel clone();
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || (this.getClass() != o.getClass()))
            return false;
        
        Imovel imv = (Imovel) o;
        return id.equals(imv.getId()) && rua.equals(imv.getRua()) && estado.equals(imv.getEstado()) &&
               precoPedido == imv.getPrecoPedido() && precoMinimo == imv.precoMinimo && quantasConsultas == imv.quantasConsultas;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("id: " + id + "\n");
        sb.append("Rua: " + rua + "\n");
        sb.append("Preço pedido pelo imóvel: " + precoPedido + "\n");
        //sb.append("Preço mínimo exigido: " + precoMinimo + "\n");
        
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
