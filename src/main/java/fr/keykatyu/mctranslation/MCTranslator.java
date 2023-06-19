package fr.keykatyu.mctranslation;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Translatable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class MCTranslator {

    public static String translate(String key, Language language) {
        try {
            Reader reader = new InputStreamReader(MCTranslator.class.getClassLoader().getResourceAsStream(language.getResourcePath()), StandardCharsets.UTF_8);
            JsonObject translatorObj = new GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create().fromJson(reader, JsonObject.class);
            reader.close();
            return translatorObj.get(key).getAsString();
        } catch (NullPointerException e) {
            System.out.println("An error occurred while retrieving the translation or the translation file. It may not exist.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return key;
    }

    public static String translate(Translatable translatable, Language language) {
        return translate(translatable.getTranslationKey(), language);
    }

}
