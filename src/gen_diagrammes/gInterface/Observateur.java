package gen_diagrammes.gInterface;

import java.io.Serializable;

/**
 * Interface pour les observateurs
 */
public interface Observateur extends Serializable {

    static final long serialVersionUID = 1L;

    void actualiser();

}
