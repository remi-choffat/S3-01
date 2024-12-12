import gen_diagrammes.Classe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
        Classe classe = new Classe("test1","test2","test3");

        assertEquals("test1", classe.getNom());
        assertEquals("test2", classe.getAcces());
        assertEquals("test3", classe.getType());
    }

    @Test
    public void testConstructeurFichier() throws ClassNotFoundException {
        Classe classe = new Classe("gen_diagrammes.Classe");

        assertEquals("Classe", classe.getNom());
        assertEquals("public", classe.getAcces());
        assertEquals("class", classe.getType());
    }

}
