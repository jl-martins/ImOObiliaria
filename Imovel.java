public abstract class Imovel
{
    // variáveis de instância
    private String id; // id do imóvel
    private String rua;
    private double precoPedido;
    private double precoMinimo;
    
    /**
     * Construtor por omissão
     * (declarado como privado para não ser possível construir um Imovel
     *  sem especificar a rua, o preço mínimo e o preço pedido).
     */
    public Imovel(){
        this("n/a", "n/a", 0, 0);
    }
    
    /**
     * Construtor parametrizado
     */
    public Imovel(String id, String rua, double precoPedido, double precoMinimo){
        this.id = id;
        this.rua = rua;
        this.precoPedido = precoPedido;
        this.precoMinimo = precoMinimo;
    }
    
    /**
     * Construtor de cópia
     */
    public Imovel(Imovel imv){
        this(imv.getId(), imv.getRua(), imv.getPrecoPedido(), imv.getPrecoMinimo());
    }
    
    /** @return id deste imóvel. */
    public String getId(){
        return id;
    }
    
    /** @return Rua em que este imóvel se situa */
    public String getRua(){
        return rua;
    }
    
    /** @return O preço pedido por este imóvel. */
    public double getPrecoPedido(){
        return precoPedido;
    }
    
    /** @return O preço mínimo deste imóvel. */
    public double getPrecoMinimo(){
        return precoMinimo;
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
    
    public void setPrecoMinimo(double precoMinimo){
        this.precoMinimo = precoMinimo;
    }
    
    public abstract Imovel clone();
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || (this.getClass() != o.getClass()))
            return false;
        
        Imovel imv = (Imovel) o;
        return this.id.equals(imv.getId()) &&
               this.rua.equals(imv.getRua()) &&
               this.precoPedido == imv.getPrecoPedido() && 
               this.precoMinimo == imv.getPrecoMinimo();
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Imovel\n\n");
        sb.append("id: " + id);
        sb.append("Rua: " + rua);
        sb.append("Preço pedido pelo imóvel: " + precoPedido);
        sb.append("Preço mínimo exigido: " + precoMinimo);
        
        return sb.toString();
    }
}
