using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using CommonData;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using global::Google.Protobuf;
using Proto;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace CommonData
{
    public class MainClass
    {
       static void Main(string[] args)
        {
            Console.WriteLine("***********BaseTest main begin*************");
            BaseNetTcp tcp = new BaseNetTcp();
            tcp.initTcp("127.0.0.1", 9900, new RecvClass());
            Console.WriteLine("***********BaseTest main end*************");
            while (true) {
                BaseData hcD = new BaseData(null);
                hcD.createObject();
                hcD.putObject("aaaa", new BaseData("1111"));
                hcD.putObject(1, new BaseData(2222));
                hcD.putObject("hanchen", new BaseData(null).createObject().putObject("1", new BaseData(null).createObject()).putObject(2, new BaseData(5)));
                hcD.putObject("hc1", new BaseData(null));
                tcp.send(hcD);
                
                Thread.Sleep(2000);
            } 
           Thread.Sleep(1000000);
        }
    }
  
    class RecvClass:TcpRecv {
        public void onRecv(BaseData bsd)
        {
            Console.WriteLine("onRecv:");
            Console.WriteLine(bsd.ToString());
            Console.WriteLine("onRecv:");
        }
    }
    
}


//BaseData hcD = new BaseData(null);
//hcD.createObject();
//hcD.putObject("aaaa", new BaseData("1111"));
//hcD.putObject(1, new BaseData(2222));
//hcD.putObject("hanchen", new BaseData(null).createObject().putObject("1", new BaseData(null).createObject()).putObject(2, new BaseData(5)));
//hcD.putObject("hc1", new BaseData(null));
//Console.WriteLine(hcD);
//byte[] data = hcD.toProtoBuf().ToByteArray();
//for (int i = 0; i < data.Length; i++)
//    Console.Write(data[i]);
//Console.WriteLine();
//BaseData hcD1 = BaseData.bytetoBaseData(Proto.BaseData.Parser.ParseFrom(data));
//Console.WriteLine(hcD1);
// 这里处理网络 传输  1处理发包
