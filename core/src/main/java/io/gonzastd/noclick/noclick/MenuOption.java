package io.gonzastd.noclick.noclick;

public enum MenuOption {
    NEW_GAME("JUEGO NUEVO"),
    CONTINUE_LAST_LEVEL("CONTINUAR"),
    OPTIONS("OPCIONES"),
    GLOSSARY("GLOSARIO"),
    CREDITS("CRÉDITOS"),
    EXIT_GAME("SALIR");

    private final String label;

    MenuOption(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
