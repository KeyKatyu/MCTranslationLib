package fr.keykatyu.mctranslation.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Translatable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * API entry point for MC translations
 */
public final class MCTranslator {

    private static final Logger LOGGER = Logger.getLogger("MCTranslator");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final TranslationsCache CACHE = new TranslationsCache();

    /**
     * Basic method to translate, using a translation key and a language
     * @param key The Minecraft translation key
     * @param language The language to translate into
     * @return The translated string, or null if not found
     */
    public static String translate(String key, Language language) {
        // Try to lookup in cache first
        if(CACHE.has(language)) {
            JsonElement translatedElement = CACHE.get(language, key);
            if(translatedElement == null) {
                LOGGER.severe("(ERROR) Key \"" + key + "\" not found in cache for language " + language.name());
                return null;
            }
            return translatedElement.getAsString();
        }

        // Only then do the I/O
        try(Reader reader = new InputStreamReader(MCTranslator.class.getClassLoader().getResourceAsStream("lang/" + language.getResourcePath()), StandardCharsets.UTF_8)) {
            JsonObject translatorObj = GSON.fromJson(reader, JsonObject.class);
            CACHE.cache(language, translatorObj);
            JsonElement translatedElement = translatorObj.get(key);
            if(translatedElement == null) {
                LOGGER.severe("(ERROR) An error occurred while retrieving the translation. It may not exist.");
                return null;
            }
            return translatedElement.getAsString();
        } catch (NullPointerException e) {
            LOGGER.severe("(ERROR) An error occurred while retrieving the translation file. It may not exist.");
        } catch (IOException e) {
            LOGGER.severe("(ERROR) An error occurred while closing the java Reader.");
        }
        return null;
    }

    /**
     * Translate a Bukkit Translatable interface
     * @param translatable The Translatable interface
     * @param language The language to translate into
     * @return The translated string, or null if not found
     */
    public static String translate(Translatable translatable, Language language) {
        return translate(translatable.getTranslationKey(), language);
    }

    /**
     * Translate a Bukkit Keyed interface
     * @param type The Keyed interface type
     * @param keyed The Keyed interface
     * @param language The language to translate into
     * @return The translated string, or null if not found
     */
    public static String translateKeyed(KeyedType type, Keyed keyed, Language language) {
        NamespacedKey namespacedKey = keyed.getKey();
        return translate(type.getPrefix() + "." + namespacedKey.getNamespace() + "." + namespacedKey.getKey(), language);
    }

    public enum KeyedType {
        ENCHANTMENT("enchantment"),
        POTION_EFFECT("effect"),
        ;

        private final String prefix;

        KeyedType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

}