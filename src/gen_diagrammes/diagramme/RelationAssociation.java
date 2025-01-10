package gen_diagrammes.diagramme;

import gen_diagrammes.vues.VueRelation;

import java.util.Objects;

public class RelationAssociation extends Relation{

    private String nomVariable;

    public RelationAssociation(Classe source, Classe destination, String nomVar){
        super(source, destination, "association");
        this.nomVariable = nomVar;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationAssociation that = (RelationAssociation) o;
        return Objects.equals(nomVariable, that.nomVariable) && that.getDestination().equals(this.getDestination()) && that.getSource().equals(this.getSource());
    }
}
