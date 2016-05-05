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
     * Construtor por omissão
     * (declarado como privado para não ser possível construir um Imovel
     *  sem especificar a rua, o preço mínimo e o preço pedido).
     */
    private Imovel(){
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
        this.quantasConsultas = 0;
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
    private double getPrecoMinimo(){
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
    
    private void setPrecoMinimo(double precoMinimo){
        this.precoMinimo = precoMinimo;
    }
    
    public void consultaImovel(){
        this.quantasConsultas++;
    }
    
    public int getNumeroConsultas(){
        return quantasConsultas;
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
        
        sb.append("id: " + id + "\n");
        sb.append("Rua: " + rua + "\n");
        sb.append("Preço pedido pelo imóvel: " + precoPedido + "\n");
        //sb.append("Preço mínimo exigido: " + precoMinimo + "\n");
        
        return sb.toString();
    }
    
    public int hashCode(){
        //MUDAR!!
        return 0;
    }
}
