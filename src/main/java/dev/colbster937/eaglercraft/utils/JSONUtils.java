package dev.colbster937.eaglercraft.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

@SuppressWarnings("unchecked")
public class JSONUtils {
  public static <T> List<T> arrayToList(JSONArray arr) {
    List<T> lst = new ArrayList<>();
    for (int i = 0; i < arr.length(); i++) {
      Object o = arr.get(i);
      lst.add((T) o);
    }
    return lst;
  }

  public static JSONArray stringToArray(String str) {
    try {
      return new JSONArray(str);
    } catch (Throwable t) {
      return new JSONArray();
    }
  }
}
