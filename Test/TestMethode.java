import gen_diagrammes.Attribut;
import gen_diagrammes.Methode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
        Methode methode = new Methode("test1","test2","test3");

        assertEquals("test1", methode.getNom());
        assertEquals("test2", methode.getAcces());
        assertEquals("test3", methode.getTypeRetour());
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