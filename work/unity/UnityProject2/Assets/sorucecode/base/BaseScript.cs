using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BaseScript {
    private static Dictionary<string, GameObject> loadCache = new Dictionary<string, GameObject>();

    public static GameObject getLoadObject(string path){
        return loadCache[path];
    }

    public static void putLoadObject(string path, GameObject obj)
    {
        loadCache.Add(path, obj);
    }
    public static void removeLoadObject(string path)
    {
        loadCache.Remove(path);
    }
}
