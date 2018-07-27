using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.Net;
using System.IO;
using global::Google.Protobuf;
using UnityEngine;

namespace CommonData
{
    interface TcpRecv
    {
        void onRecv(BaseData bsd);
    }
    enum SocketState
    {
        Connecting,
        Connected,
        Idel
    }
    public class TcpConfig {
        public static int headSize = 4;
        public static int recvBuffSize = 1024 * 64;
        public static int maxPackSize = 64 * 1024;
    }
    class BaseNetTcp
    {
        private Socket cTcp = null;//socket套接字
        private byte[] bytes = new byte[TcpConfig.recvBuffSize];//本对象中接收数据缓存
        private SocketState sState = SocketState.Idel;// 当前套接字的状态
        private int headSize = 4;//包头长度 固定4
        private byte[] surplusBuffer = null;//不完整的数据包，即用户自定义缓冲区
        private TcpRecv recvEvent = null;
        public int initTcp(string ip, int port, TcpRecv tr)
        {// 初始化BaseNetTcp
            cTcp = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            cTcp.BeginConnect(ip, port, new AsyncCallback(onConnect), cTcp);
            Array.Clear(bytes, 0, bytes.Length);
            sState = SocketState.Connecting;
            recvEvent = tr;
            return 1;
        }
        public void onTimer()
        {// tcp 套接字的心跳 这里将来会处理断线重连
            Console.WriteLine("basenettcp on timer");
        }
        public void onConnect(IAsyncResult ar)
        {
            Debug.Log("basenettcp onConnect");
            Socket ts = (Socket)ar.AsyncState;
            sState = SocketState.Connected;
            ts.BeginReceive(bytes, 0, bytes.Length, SocketFlags.None, new AsyncCallback(OnReceive), ts);
        }
        public void send(CommonData.BaseData sendData)
        {
            Debug.Log("basenettcp send");
            if (sState != SocketState.Connected)
            {
                Console.WriteLine("套接字没有准备好！！！");
                return;
            }
            byte[] protoBuff = sendData.toProtoBuf().ToByteArray();
            if (protoBuff.Length > TcpConfig.maxPackSize - 4)
            {
                Debug.Log("发送数据超出64k");
                return;
            }
            byte[] sendBuff = new byte[protoBuff.Length + 4];
            byte[] lenBuff = BitConverter.GetBytes(protoBuff.Length);
            Array.Reverse(lenBuff);
            Array.Copy(lenBuff, sendBuff, 4);
            Array.Copy(protoBuff, 0, sendBuff, 4, protoBuff.Length);
            cTcp.BeginSend(sendBuff, 0, sendBuff.Length, SocketFlags.None, new AsyncCallback(onSend), sendData);
        }
        public void onSend(IAsyncResult ar)
        {
            BaseData bd = (BaseData)ar.AsyncState;
            Debug.Log("basenettcp onSend sunncess " + bd.ToString());
        }

        public void OnReceive(IAsyncResult ar)
        {
            Socket ts = (Socket)ar.AsyncState;
            int bytesRead = bytes.Length;
            bytesRead = ts.EndReceive(ar);
            Debug.Log("onreceive" + bytesRead);
          
            if (bytesRead > 0)
            {
                if (surplusBuffer == null)
                {
                    surplusBuffer = new byte[bytesRead];
                    Buffer.BlockCopy(bytes, 0, surplusBuffer, 0, bytesRead);
                }
                else
                {
                    byte[] tempByte = new byte[bytesRead];
                    Buffer.BlockCopy(bytes, 0, tempByte, 0, bytesRead);
                    surplusBuffer = surplusBuffer.Concat(tempByte).ToArray();
                }
                int haveRead = 0;
                int totalLen = surplusBuffer.Length;
                while (haveRead <= totalLen)
                {
                    if (totalLen - haveRead < headSize)
                    {
                        byte[] byteSub = new byte[totalLen - haveRead];
                        Buffer.BlockCopy(surplusBuffer, haveRead, byteSub, 0, totalLen - haveRead);
                        surplusBuffer = byteSub;
                        totalLen = 0;
                        break;
                    }
                    byte[] headByte = new byte[headSize];
                    Buffer.BlockCopy(surplusBuffer, haveRead, headByte, 0, headSize);
                    Array.Reverse(headByte);
                    int bodySize = BitConverter.ToInt32(headByte, 0);
                    if (haveRead + headSize + bodySize > totalLen)
                    {
                        byte[] byteSub = new byte[totalLen - haveRead];
                        Buffer.BlockCopy(surplusBuffer, haveRead, byteSub, 0, totalLen - haveRead);
                        surplusBuffer = byteSub;
                        break;
                    }
                    else
                    {
                        BaseData bsdata = null;
                        if (bodySize >= 0)
                        {
                            byte[] bodyArray = new byte[bodySize];
                            Array.Copy(surplusBuffer, haveRead + headSize, bodyArray, 0, bodySize);
                            Proto.BaseData bd = Proto.BaseData.Parser.ParseFrom(bodyArray);
                            bsdata = BaseData.bytetoBaseData(bd);
                        } haveRead = haveRead + headSize + bodySize;
                        if (headSize + bodySize == bytesRead)
                        {
                            surplusBuffer = null;
                            totalLen = 0;
                        }
                        recvEvent.onRecv(bsdata);
                    }
                }
                Array.Clear(bytes, 0, bytes.Length);
                ts.BeginReceive(bytes, 0, bytes.Length, SocketFlags.None, new AsyncCallback(OnReceive), ts);
            }
        }
    }
}
