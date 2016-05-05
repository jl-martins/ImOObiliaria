public class Moradia extends Imovel
        implements Habitavel
{
    // Variáveis de instância
    private String tipo; // tipo da moradia (isolada, geminada, banda, gaveto)
    private int areaImplantacao; // area de implantacao
    private int areaTotal; // area total coberta
    private int areaEnv; // area do terreno envolvente
    private int numQuartos, numWCs;
    private int numDaPorta;
    
    /**
     * Construtor por omissão
     */
    public Moradia(){
        super();
        tipo = "n/a";
        areaImplantacao = areaTotal = areaEnv = 0;
        numQuartos = numWCs = numDaPorta = 0;
    }
    
    public Moradia(String id, String rua, double precoPedido, double precoMinimo,
                   String tipo, int areaImplantacao, int areaTotal, int areaEnv,
                   int numQuartos, int numWCs, int numDaPorta){
        super(id, rua, precoPedido, precoMinimo);
        this.tipo = tipo;
        this.areaImplantacao = areaImplantacao;
        this.areaTotal = areaTotal;
        this.areaEnv = areaEnv;
        this.numQuartos = numQuartos;
        this.numWCs = numWCs;
        this.numDaPorta = numDaPorta;
    }
    
    public Moradia(Moradia m){
        super(m);
        tipo = m.getTipo();
        areaImplantacao = m.getAreaImplantacao();
        areaTotal = m.getAreaTotal();
        areaEnv = m.getAreaEnv();
        numQuartos = m.getNumQuartos();
        numWCs = m.getNumWCs();
        numDaPorta = m.getNumDaPorta();
    }
    
    
    // Getters
    public String getTipo(){
        return tipo;
    }
    
    public int getAreaImplantacao(){
        return areaImplantacao;
    }
    
    public int getAreaTotal(){
        return areaTotal;
    }
    
    public int getAreaEnv(){
        return areaEnv;
    }
    
    public int getNumQuartos(){
        return numQuartos;
    }
    
    public int getNumWCs(){
        return numWCs;
    }
    
    public int getNumDaPorta(){
        return numDaPorta;
    }
    
    // Setters
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    public void setAreaImplantacao(int areaImplantacao){
        this.areaImplantacao = areaImplantacao;
    }
    
    public void setAreaTotal(int areaTotal){
        this.areaTotal = areaTotal;
    }
    
    public void setAreaEnv(int areaEnv){
        this.areaEnv = areaEnv;
    }
    
    public void setNumQuartos(int numQuartos){
        this.numQuartos = numQuartos;
    }
    
    public void setNumWCs(int numWCs){
        this.numWCs = numWCs;
    }
    
    public void setNumDaPorta(int numDaPorta){
        this.numDaPorta = numDaPorta;
    }
    
    public Moradia clone(){
        return new Moradia(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        else if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            Moradia m = (Moradia) o;
            
            return super.equals(m) && tipo.equals(m.getTipo()) && areaImplantacao == m.getAreaImplantacao() &&
                   areaTotal == m.getAreaTotal() && areaEnv == m.getAreaEnv() && numQuartos == m.getNumQuartos() &&
                   numWCs == m.getNumWCs() && numDaPorta == m.getNumDaPorta();
        }
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder("-> Moradia\n");
        
        sb.append(super.toString());
        sb.append("Tipo: " + tipo + "\n");
        sb.append("Área de implantação: " + areaImplantacao + "m^2\n");
        sb.append("Área total: " + areaTotal + "m^2\n");
        sb.append("Área envolvente: " + areaEnv + "m^2\n");
        sb.append("Número de quartos: " + numQuartos + "\n");
        sb.append("Número de WCs: " + numWCs + "\n");
        sb.append("Número da porta: " + numDaPorta + "\n");
        return sb.toString();
    }
}
