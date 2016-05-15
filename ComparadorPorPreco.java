/**
 * Comparador de 2 imóveis com base no preço pedido pelos mesmos.
 * 
 * @author Grupo12
 * @version 15/05/2016
 */

import java.util.Comparator;

public class ComparadorPorPreco implements Comparator<Imovel> {
    
    /** Compara 2 imóveis com base no preço pedido por cada um deles. */
    public int compare(Imovel imv1, Imovel imv2) {
        if (imv1.getPrecoPedido() == imv2.getPrecoPedido())
            return 0;
        else if(imv1.getPrecoPedido() > imv2.getPrecoPedido())
            return 1;
        else
            return -1;
    }
}
