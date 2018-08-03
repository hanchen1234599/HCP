using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using CommonData;

public class BaseClick : MonoBehaviour
{

    // Use this for initialization
    void Start()
    {
        //Debug.Log("Start" + gameObject.GetComponent<LoadCache>().getCurPath());		
    }

    // Update is called once per frame
    void Update()
    {

    }
    int i = 0;
    public void OnClick()
    {
        //Debug.Log("click" + GetComponent<LoadCache>().getCurPath());
        string name = BaseScript.getLoadObject("Canvas/username").GetComponent<InputField>().text;
        string passw = BaseScript.getLoadObject("Canvas/passw").GetComponent<InputField>().text;
        string passwsure = BaseScript.getLoadObject("Canvas/passwsure").GetComponent<InputField>().text;
        if (passw == passwsure)
        {
            Debug.Log("on passw success");
        }
        if(i == 0)
            BaseTest.send(new BaseData(null).createObject().putObject("mK", new BaseData("login")).putObject("mV",new BaseData(null).createObject().putObject("username", name).putObject("userpassword", passw)));
        else
            BaseTest.send(new BaseData(null).createObject().putObject("mE", new BaseData("da")).putObject("mV", new BaseData(null).createObject().putObject("username", name).putObject("userpassword", passw)));
        i++;
        //BaseTest.send(new BaseData(null).createObject().putObject("regist", new BaseData(null).createObject().putObject("username", name).putObject("userpassword", passw)));
    }
}
