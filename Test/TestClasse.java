import gen_diagrammes.Attribut;
import gen_diagrammes.Classe;
import gen_diagrammes.Methode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestClasse {


    @BeforeAll
    public static void setdown() throws SQLException, ClassNotFoundException {
    }

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
    }

    @AfterEach
    public void set() throws SQLException, ClassNotFoundException {
    }

    @Test
    public void testConstructeurVide() {
        Classe classe = new Classe();

        assertEquals("Classe", classe.getNom());
        assertEquals("public", classe.getAcces());
        assertEquals("class", classe.getType());
    }

    @Test
    public void testConstructeurNonVide() {
        Classe classe = new Classe("test1", "test2", "test3");

        assertEquals("test1", classe.getNom());
        assertEquals("test2", classe.getAcces());
        assertEquals("test3", classe.getType());
    }

    @Test
    public void testConstructeurFichier() throws ClassNotFoundException, MalformedURLException {
        //Il faut un chemin absolut, donc le test ne peut pas marcher pour tout le monde, le chemin doit être ajouter pour chaque utilisateur
        Classe classe = new Classe("CHEMIN_DE_CLASS_ABSOLUT");

        assertEquals("Classe", classe.getNom());
        assertEquals("public", classe.getAcces());
        assertEquals("class", classe.getType());
    }

    @Test
    public void testAjout() {
        Classe classe1 = new Classe();

        ArrayList<Classe> ac = new ArrayList<>();
        ArrayList<Attribut> aa = new ArrayList<>();
        ArrayList<Methode> am = new ArrayList<>();

        assertEquals(ac, classe1.getParents());
        assertEquals(aa, classe1.getAttributs());
        assertEquals(am, classe1.getMethodes());

        Classe classe2 = new Classe();
        Attribut attribut = new Attribut();
        Methode methode = new Methode();

        ac.add(classe2);
        aa.add(attribut);
        am.add(methode);

        classe1.addParent(classe2);
        classe1.addAttribut(attribut);
        classe1.addMethode(methode);

        assertEquals(ac, classe1.getParents());
        assertEquals(aa, classe1.getAttributs());
        assertEquals(am, classe1.getMethodes());
    }


    @Test
    public void testAjoutPlusieursParentMemeType() {
        Classe classe1 = new Classe();

        ArrayList<Classe> ac = new ArrayList<>();

        Classe classe2 = new Classe();
        Classe classe3 = new Classe();

        ac.add(classe2);
        classe1.addParent(classe2);

        assertEquals(ac, classe1.getParents());

        ac.add(classe3);
        classe1.addParent(classe3);

        //Il faut faire une exeption pour ça
        assertNotEquals(ac, classe1.getParents());
    }

    @Test
    public void testAjoutPlusieursParentPasMemeType() {
        Classe classe1 = new Classe();

        ArrayList<Classe> ac = new ArrayList<>();

        Classe classe2 = new Classe();
        Classe classe3 = new Classe("test1", "test2", "test3");

        ac.add(classe2);
        classe1.addParent(classe2);

        assertEquals(ac, classe1.getParents());

        ac.add(classe3);
        classe1.addParent(classe3);

        assertEquals(ac, classe1.getParents());
    }
}
