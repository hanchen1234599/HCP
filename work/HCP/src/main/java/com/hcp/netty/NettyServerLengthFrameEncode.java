package com.hcp.netty;

import com.hcp.proto.BaseDataProtos;
import com.hcp.util.BaseData;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyServerLengthFrameEncode extends MessageToByteEncoder<BaseData> {

	protected void encode(ChannelHandlerContext ctx, BaseData msg, ByteBuf out) throws Exception {
		System.out.println(msg);
		BaseDataProtos.BaseData bsPro = msg.toProtoBuf();
		byte[] bytes = bsPro.toByteArray();
		int len = bytes.length;
//		if( len > 64 * 1024 - 4)
//			return;
		out.writeInt(len);
		out.writeBytes(bytes);
	}
}
