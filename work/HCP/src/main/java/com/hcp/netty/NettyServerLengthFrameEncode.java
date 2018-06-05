package com.hcp.netty;

import com.hcp.data.BaseData;
import com.hcp.proto.BaseDataProtos;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyServerLengthFrameEncode extends MessageToByteEncoder<BaseData> {

	protected void encode(ChannelHandlerContext ctx, BaseData msg, ByteBuf out) throws Exception {
		BaseDataProtos.BaseData bsPro = msg.toProtoBuf();
		byte[] bytes = bsPro.toByteArray();
		int len = bytes.length;
		out.writeInt(len);
		out.writeBytes(bytes);
	}
}
