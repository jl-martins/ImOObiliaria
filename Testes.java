
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import java.time.LocalDate;

/**
 * The test class Testes.
 *
 * É necessário completar os teste, colocando os parâmetros nos construtores.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Testes
{
    private Imoobiliaria imo;
    private Vendedor v;
    private Terreno t;

    /**
     * Teste principal
     */
    @Test
    public void mainTest() {
        imo = new Imoobiliaria();
        try {
            imo.iniciaSessao("",null);
            fail();
        } catch(SemAutorizacaoException e) {

        } catch(Exception e) {
            fail();
        }

        try {
            v = new Vendedor("a75273@uminho.pt", "João Pereira", "1234", "Somewhere, Over the Rainbow, Way Up High", LocalDate.of(1996, 12, 19));
            imo.registarUtilizador(v);
        } catch(Exception e) {
            fail();
        }

        String email = v.getEmail();
        String password = v.getPassword();

        try {
            imo.iniciaSessao(email, password);
        } catch(Exception e) {
            fail();
        }

        t = new Terreno("Terreno para construçao de Habitacoes #1", "Rua Sobe e Desce", 40000, 30000, 500, true,
            false, 20.0, 140.0, true);  // Preencher parâmetros do construtor
        try {
            imo.registaImovel(t);
        } catch (Exception e) {
            fail();
        }

        int s = imo.getImovel("Terreno", Integer.MAX_VALUE).size();
        assertTrue(s>0);
        try{
            Set<String> ids = imo.getTopImoveis(0);
            assertTrue(ids.contains(t.getId()));
        } catch(Exception e) {  
            fail();
        }

        assertTrue(imo.getMapeamentoImoveis().keySet().contains(t));
        try {
            assertTrue(imo.getConsultas().size()>0);
        } catch(Exception e) {
            fail();
        }

        imo.fechaSessao();
        Comprador c = new Comprador("a68646@uminho.pt", "João Martins", "1234", "Jardim Zoológico da Maia", LocalDate.of(1994, 8, 8));  // Preencher parâmetros do construtor
        try {
            imo.registarUtilizador(c);
        } catch(Exception e) {
            fail();
        }
        email = c.getEmail();
        password = c.getPassword();
        try {
            imo.iniciaSessao(email, password);
            imo.setFavorito(t.getId());
            assertTrue(imo.getFavoritos().contains(t));
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
