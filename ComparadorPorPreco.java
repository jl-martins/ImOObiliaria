import java.util.Comparator;

/**
 * Write a description of class ComparadorPorPreço here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ComparadorPorPreco implements Comparator<Imovel> {
    public int compare(Imovel imv1, Imovel imv2) {
        if (imv1.getPrecoPedido() == imv2.getPrecoPedido())
            return 0;
        else if(imv1.getPrecoPedido() > imv2.getPrecoPedido())
            return 1;
        else return -1;
    }
}
