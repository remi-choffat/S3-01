import gen_diagrammes.Classe;
import gen_diagrammes.Diagramme;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestDiagramme {


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
    public void testDiagramme() {
        Diagramme diagramme1 = Diagramme.getInstance();
        Diagramme diagramme2 = Diagramme.getInstance();
        assertSame(diagramme1, diagramme2, "Les deux instances sont normalement les mêmes");
    }

    @Test
    public void testDiagrammeInitialize() {
        ArrayList<Classe> test2 = new ArrayList<>();

        Diagramme.initialize("test1",test2);

        assertEquals("test1", Diagramme.getInstance().getNomPackage());
        assertEquals(test2, Diagramme.getInstance().getListeClasses());
    }

    @Test
    public void testDiagrammeAjouterClasse() {
        Classe classe = new Classe();

        ArrayList<Classe> clas = new ArrayList<>();
        clas.add(classe);

        Diagramme.getInstance().ajouterClasse(classe);

        assertEquals(clas, Diagramme.getInstance().getListeClasses());
    }

    @Test
    public void testDiagrammeEnleverClasse() {
        Classe classe = new Classe();

        ArrayList<Classe> clas = new ArrayList<>();

        Diagramme.getInstance().ajouterClasse(classe);
        Diagramme.getInstance().supprimerClasse(classe);

        assertEquals(clas, Diagramme.getInstance().getListeClasses());
    }
}