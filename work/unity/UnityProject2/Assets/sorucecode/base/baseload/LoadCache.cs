using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LoadCache : MonoBehaviour
{
    private string path = null;

    public string getCurPath(){
        return path;
    }
    string getObjectPaht() {
        Transform tempTransfome = transform;
        string path = tempTransfome.name;
        while (tempTransfome.parent != null)
        {
            tempTransfome = tempTransfome.parent;
            path = tempTransfome.name + "/" + path;
        }
        return path;
    }

    void Start () {
        path = getObjectPaht();
        BaseScript.putLoadObject(path, gameObject);
    }

	// Update is called once per frame
	void Update () {
		
	}

    void OnDestroy() {
        BaseScript.removeLoadObject(path);
    }
}
