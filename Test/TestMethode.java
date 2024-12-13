import gen_diagrammes.Methode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMethode {


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
    public void testAttributVide() {
        Methode methode = new Methode();

        assertEquals("Inconnu", methode.getNom());
        assertEquals("Inconnu", methode.getTypeRetour());
        assertEquals("Inconnu", methode.getAcces());
    }

    @Test
    public void testAttributNonVide() {
        ArrayList<String> liste = new ArrayList<String>();
        liste.add("test4");
        liste.add("test5");
        Methode methode = new Methode("test1","test2","test3",liste);

        assertEquals("test1", methode.getNom());
        assertEquals("test2", methode.getAcces());
        assertEquals("test3", methode.getTypeRetour());
        assertEquals("test4", methode.getParametres().get(0));
        assertEquals("test5", methode.getParametres().get(1));
        assertEquals(2, methode.getParametres().size());
    }

    @Test
    public void testAttributSetter() {
        Methode methode = new Methode();

        methode.setNom("test1");
        methode.setAcces("test2");
        methode.setTypeRetour("test3");

        assertEquals("test1", methode.getNom());
        assertEquals("test2", methode.getAcces());
        assertEquals("test3", methode.getTypeRetour());
    }
}