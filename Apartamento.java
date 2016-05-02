public class Apartamento extends Imovel
{
    // variaveis de instancia
    String tipo; // tipo de apartamento: Simples, Duplex, Triplex
    int areaTotal;
    int numQuartos, numWCs;
    int numDaPorta, andar;
    boolean temGaragem;

    /**
     * Constructor for objects of class Apartamento
     */
    public Apartamento()
    {
        super();
        tipo = "n/a";
        areaTotal = numQuartos = numWCs = numDaPorta = andar = 0;
        temGaragem = false;
    }
    
    public Apartamento(String id, String rua, double precoPedido, double precoMinimo,
                       String tipo, int areaTotal, int numQuartos, int numWCs,
                       int numDaPorta, int andar, boolean temGaragem){
        super(id, rua, precoPedido, precoMinimo);
        this.tipo = tipo;
        this.areaTotal = areaTotal;
        this.numQuartos = numQuartos;
        this.numWCs = numWCs;
        this.numDaPorta = numDaPorta;
        this.andar = andar;
        this.temGaragem = temGaragem;
    }
    
    public Apartamento(Apartamento a){
        super(a);
        tipo = a.getTipo();
        areaTotal = a.getAreaTotal();
        numQuartos = a.getNumQuartos();
        numWCs = a.getNumWCs();
        numDaPorta = a.getNumDaPorta();
        andar = a.getAndar();
        temGaragem = a.getTemGaragem();
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public int getAreaTotal(){
        return areaTotal;
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
    
    public int getAndar(){
        return andar;
    }
    
    public boolean getTemGaragem(){
        return temGaragem;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    
    public void setAreaTotal(int areaTotal){
        this.areaTotal = areaTotal;
    }
    
    public void setNumQuartos(int numQuartos){
        this.numQuartos = numQuartos;
    }
    
    public void setnumWCs(int numWCs){
        this.numWCs = numWCs;
    }
    
    public void setNumDaPorta(int numDaPorta){
        this.numDaPorta = numDaPorta;
    }
    
    public void setAndar(int andar){
        this.andar = andar;
    }
    
    public void getTemGaragem(boolean temGaragem){
        this.temGaragem = temGaragem;
    }
    
    public Apartamento clone(){
        return new Apartamento(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        else if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            Apartamento a = (Apartamento) o;
            
            return super.equals(a) && tipo.equals(a.getTipo()) && areaTotal == a.getAreaTotal() &&
                   numQuartos == a.getNumQuartos() && numWCs == a.getNumWCs() && numDaPorta == a.getNumDaPorta() &&
                   andar == a.getAndar() && temGaragem == a.getTemGaragem();
        }
    }
    
    public String toString(){
        return "";
    }
}
