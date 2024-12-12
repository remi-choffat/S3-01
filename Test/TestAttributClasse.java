import gen_diagrammes.AttributClasse;
import gen_diagrammes.Classe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestAttributClasse {


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
    public void testAttributClasseVide() {
        AttributClasse attributClasse = new AttributClasse();

        assertEquals("Inconnu", attributClasse.getCardinalitePointee());
        assertEquals("Inconnu", attributClasse.getCardinalitePointeur());
        assertNull(attributClasse.getAttribut());
        assertEquals("Inconnu", attributClasse.getNom());
        assertEquals("Inconnu", attributClasse.getTypeAcces());
        assertEquals("Inconnu", attributClasse.getType());
    }

    @Test
    public void testAttributClasseNonVide() {
        Classe classe = new Classe();
        AttributClasse attributClasse = new AttributClasse("test1","test2","test3","test4","test5",classe);

        assertEquals(classe, attributClasse.getAttribut());
        assertEquals("test1", attributClasse.getNom());
        assertEquals("test2", attributClasse.getTypeAcces());
        assertEquals("test3", attributClasse.getType());
        assertEquals("test4", attributClasse.getCardinalitePointeur());
        assertEquals("test5", attributClasse.getCardinalitePointee());
    }

    @Test
    public void testAttributClasseSetter() {
        AttributClasse attributClasse = new AttributClasse();

        attributClasse.setNom("test1");
        attributClasse.setTypeAcces("test2");
        attributClasse.setType("test3");
        attributClasse.setCardinalitePointee("test4");
        attributClasse.setCardinalitePointeur("test5");

        assertEquals("test1", attributClasse.getNom());
        assertEquals("test2", attributClasse.getTypeAcces());
        assertEquals("test3", attributClasse.getType());
        assertEquals("test4", attributClasse.getCardinalitePointee());
        assertEquals("test5", attributClasse.getCardinalitePointeur());
    }
}