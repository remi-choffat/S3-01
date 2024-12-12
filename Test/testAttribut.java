import gen_diagrammes.Attribut;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testAttribut {


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
        Attribut attribut = new Attribut();

        assertEquals("Inconnu", attribut.getNom());
        assertEquals("Inconnu", attribut.getTypeAcces());
        assertEquals("Inconnu", attribut.getType());
    }

    @Test
    public void testAttributNonVide() {
        Attribut attribut = new Attribut("test1","test2","test3");

        assertEquals("test1", attribut.getNom());
        assertEquals("test2", attribut.getTypeAcces());
        assertEquals("test3", attribut.getType());
    }

    @Test
    public void testAttributSetter() {
        Attribut attribut = new Attribut();

        attribut.setNom("test1");
        attribut.setTypeAcces("test2");
        attribut.setType("test3");

        assertEquals("test1", attribut.getNom());
        assertEquals("test2", attribut.getTypeAcces());
        assertEquals("test3", attribut.getType());
    }
}