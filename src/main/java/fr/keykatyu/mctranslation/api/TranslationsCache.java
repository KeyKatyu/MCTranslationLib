package fr.keykatyu.mctranslation.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple cache to store loaded translation JSONs
 */
public class TranslationsCache {

  private final Map<Language, JsonObject> cachedJsons = new HashMap<>();

  /**
   * Test if a language has been cached.
   * @param language The language to test.
   * @return The JSON object.
   */
  public boolean has(Language language) {
      return cachedJsons.containsKey(language);
  }

  /**
   * Get a cached JSON object for a language.
   * @param language The language to get.
   * @param key The key to get.
   * @return The JSON element. Or {@code null} if not found.
   */
  public JsonElement get(Language language, String key) {
    return cachedJsons.get(language).get(key);
  }

  /**
   * Cache a JSON object for a language.
   * @param language The language to cache.
   * @param translatorObj The JSON object to cache.
   */
  public void cache(Language language, JsonObject translatorObj) {
    cachedJsons.put(language, translatorObj);
  }

}
