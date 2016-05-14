import java.io.Serializable;

public class Apartamento extends Imovel
        implements Habitavel, Serializable
{
    // variaveis de instancia
    private TipoApartamento tipo; // tipo de apartamento: Simples, Duplex, Triplex
    private int areaTotal;
    private int numQuartos, numWCs;
    private int numDaPorta, andar;
    private boolean temGaragem;

    /**
     * Constructor for objects of class Apartamento
     */
    public Apartamento(){
        super();
        tipo = null;
        temGaragem = false;
        areaTotal = numQuartos = numWCs = numDaPorta = andar = 0;
    }
    
    public Apartamento(String id, String rua, int precoPedido, int precoMinimo, TipoApartamento tipo, int areaTotal,
                       int numQuartos, int numWCs, int numDaPorta, int andar, boolean temGaragem){
        
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
    
    public TipoApartamento getTipo(){
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
    
    private void setTipo(String tipo) throws TipoInvalidoException{
        this.tipo = TipoApartamento.fromString(tipo);
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
            
            return super.equals(a) && tipo == a.getTipo() && areaTotal == a.getAreaTotal() &&
                   numQuartos == a.getNumQuartos() && numWCs == a.getNumWCs() && numDaPorta == a.getNumDaPorta() &&
                   andar == a.getAndar() && temGaragem == a.getTemGaragem();
        }
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder("-> Apartamento\n");
        
        sb.append(super.toString());
        sb.append("Tipo: " + ((tipo != null) ? tipo.name() : "n/a") + "\n");
        sb.append("Área total: " + areaTotal + "m^2\n");
        sb.append("Número de quartos: " + numQuartos + "\n");
        sb.append("Número de WCs: " + numWCs + "\n");
        sb.append("Número da porta: " + numDaPorta + "\n");
        sb.append("Andar: " + andar + "\n");
        sb.append("Tem garagem: " + (temGaragem ? "sim\n" : "não\n"));
        return sb.toString();
    }
    
        public String toStringParcial(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("Tipo: " + ((tipo != null) ? tipo.name() : "n/a") + "\n");
        sb.append("Área total: " + areaTotal + "m^2\n");
        sb.append("Número de quartos: " + numQuartos + "\n");
        sb.append("Número de WCs: " + numWCs + "\n");
        sb.append("Número da porta: " + numDaPorta + "\n");
        sb.append("Andar: " + andar + "\n");
        sb.append("Tem garagem: " + (temGaragem ? "sim\n" : "não\n"));
        return sb.toString();
    }
    
    public int hashCode(){
        int hash = super.hashCode();
        
        hash = 31*hash + ((tipo == null) ? 0 : tipo.hashCode());
        hash = 31*hash + areaTotal;
        hash = 31*hash + numQuartos;
        hash = 31*hash + numWCs;
        hash = 31*hash + numDaPorta;
        hash = 31*hash + andar;
        hash = 31*hash + (temGaragem ? 1 : 0);
        return hash;
    }
}
