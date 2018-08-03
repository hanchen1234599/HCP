using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Net;
using System.Net.Sockets;
using System.Threading;
using CommonData;
public class BaseTest : MonoBehaviour
{

    private int step = 0;
    private static BaseNetTcp client = null;
    // Use this for initialization

    public static void send(BaseData data) {
        client.send(data);
    }
    
    void Start()
    {
        MonoBehaviour.print("***********BaseTest start begin*************");
        client = new BaseNetTcp();
        client.initTcp("127.0.0.1", 9900, new RecvClass());
        MonoBehaviour.print("***********BaseTest start success*************");
    }

    // Update is called once per frame
    void Update()
    {
        step++;
        if (step > 100)
        {
            BaseData hcD = new BaseData(null);
            hcD.createObject();
            hcD.putObject("aaaa", new BaseData("1111"));
            hcD.putObject(1, new BaseData(2222));
            hcD.putObject("hanchen", new BaseData(null).createObject().putObject("1", new BaseData(null).createObject()).putObject(2, new BaseData(5)));
            hcD.putObject("hc1", new BaseData(null));
           // client.send(hcD);
            step = 0;
        }
    }
}
class RecvClass : TcpRecv
{
    public void onRecv(BaseData bsd)
    {
        //Debug.Log("onRecv begin \n");
        Debug.Log(bsd.ToString());
       // Debug.Log("onRecv end \n");
    }
}
