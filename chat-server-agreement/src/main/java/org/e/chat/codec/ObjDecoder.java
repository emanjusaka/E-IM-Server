package org.e.chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.e.chat.protocol.Packet;
import org.e.chat.utils.SerializationUtil;

import java.util.List;

public class ObjDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte command = in.readByte(); //读取指令
        byte[] data = new byte[dataLength - 1];// 指令占了一位，剔除掉
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, Packet.get(command)));

    }
}
