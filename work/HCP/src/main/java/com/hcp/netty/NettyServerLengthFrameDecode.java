package com.hcp.netty;

import com.hcp.data.BaseData;
import com.hcp.proto.BaseDataProtos;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyServerLengthFrameDecode extends LengthFieldBasedFrameDecoder {
	public NettyServerLengthFrameDecode() {
		super(1024 * 64, 0, 4);
	}
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception{
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		int len = frame.readInt();
		byte[] bytes = new byte[len];
		frame.readBytes(bytes, 0, len);
		System.out.println(BaseData.bytetoBaseData(BaseDataProtos.BaseData.parseFrom(bytes)).toString());
		return BaseData.bytetoBaseData(BaseDataProtos.BaseData.parseFrom(bytes));
	}
}
