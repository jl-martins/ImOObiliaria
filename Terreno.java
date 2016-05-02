public class Terreno extends Imovel
{
    // variaveis de instancia
    private int area;
    private boolean terrenoHab; // indica se o terreno serve para construir habitacoes
    private boolean terrenoArm; // indica se o terreno serve para construir um armazem
    private double diamCanalizacoes;
    private double maxKWh;
    private boolean temRedeEsgotos;
    
    public Terreno(){
        super();
        area = 0;
        terrenoHab = terrenoArm = false;
        diamCanalizacoes = 0;
        maxKWh = 0;
        temRedeEsgotos = false;
    }
    
    public Terreno(String id, String rua, double precoPedido, double precoMinimo, int area, boolean terrenoHab,
                   boolean terrenoArm, double diamCanalizacoes, double maxKWh, boolean temRedeEsgotos)
    {
        super(id, rua, precoPedido, precoMinimo);
        this.area = area;
        this.terrenoHab = terrenoHab;
        this.terrenoArm = terrenoArm;
        this.diamCanalizacoes = diamCanalizacoes;
        this.maxKWh = maxKWh;
        this.temRedeEsgotos = temRedeEsgotos;
    }
    
    public Terreno(Terreno terr){
        super(terr.getId(), terr.getRua(), terr.getPrecoPedido(), terr.getPrecoMinimo());
        area = terr.getArea();
        terrenoHab = terr.getTerrenoHab();
        terrenoArm = terr.getTerrenoArm();
        diamCanalizacoes = terr.getDiamCanalizacoes();
        maxKWh = terr.getMaxKWh();
        temRedeEsgotos = terr.getTemRedeEsgotos();
    }
    
    public int getArea(){
        return area;
    }
    
    public boolean getTerrenoHab(){
        return terrenoHab;
    }
    
    public boolean getTerrenoArm(){
        return terrenoArm;
    }
    
    public double getDiamCanalizacoes(){
        return diamCanalizacoes;
    }
    
    public double getMaxKWh(){
        return maxKWh;
    }
    
    public boolean getTemRedeEsgotos(){
        return temRedeEsgotos;
    }
    
    public void setArea(int area){
        this.area = area;
    }
    
    public void setTerrenoHab(boolean terrenoHab){
        this.terrenoHab = terrenoHab;
    }
    
    public void setTerrenoArm(boolean terrenoArm){
        this.terrenoArm = terrenoArm;
    }
    
    public void setDiamCanalizacoes(double diamCanalizacoes){
        this.diamCanalizacoes = diamCanalizacoes;
    }
    
    public void setMaxKWh(double maxKWh){
        this.maxKWh = maxKWh;
    }
    
    public void setTemRedeEsgotos(boolean temRedeEsgotos){
        this.temRedeEsgotos = temRedeEsgotos;
    }
    
    public Terreno clone(){
        return new Terreno(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        else if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            Terreno t = (Terreno) o;
            
            return super.equals(t) && area == t.getArea() && terrenoHab == t.getTerrenoHab() &&
                   terrenoArm == t.getTerrenoArm() && diamCanalizacoes == t.getDiamCanalizacoes() &&
                   maxKWh == t.getMaxKWh() && temRedeEsgotos == t.getTemRedeEsgotos();
        }
    }
    
    public String toString(){
        return "";
    }
}
