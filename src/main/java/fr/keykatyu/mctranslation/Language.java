package fr.keykatyu.mctranslation;

public enum Language {
    FR_FR("fr_fr.json");

    private final String resourcePath;

    Language(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getResourcePath() {
        return resourcePath;
    }

}
