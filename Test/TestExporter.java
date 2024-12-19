import gen_diagrammes.Diagramme;
import gen_diagrammes.Exporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExporter {


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
    public void testConstructeur() {
        Exporter exporter = new Exporter(Diagramme.getInstance());
        assertEquals(Diagramme.getInstance(), exporter.diagramme());
    }
}