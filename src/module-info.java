module gen_diagrammes {
    requires javafx.controls;
    requires javafx.base;
    requires static lombok;
    requires java.desktop;
    requires javafx.swing;
    exports gen_diagrammes;
    exports gen_diagrammes.controleurs;
    exports gen_diagrammes.vues;
    exports gen_diagrammes.diagramme;
    exports gen_diagrammes.gInterface;
}