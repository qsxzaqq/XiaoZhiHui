package cc.i9mc.stocks.common.dzh;

import cc.i9mc.stocks.utils.ByteOutputStreamUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JinVan on 2021-04-02.
 */
public class DzhEncoder extends MessageToMessageEncoder<Object> {
    @SneakyThrows
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object objs, List<Object> returnValues) {
        List<DzhReqPacket> packetList = new ArrayList<>();
        if (objs instanceof DzhReqPacket) {
            packetList.add((DzhReqPacket) objs);
        } else if (objs instanceof List) {
            List list = (List) objs;
            if (!list.isEmpty() && list.get(0) instanceof DzhReqPacket) {
                packetList.addAll(list);
            } else {
                return;
            }
        } else {
            return;
        }

        if (packetList.size() == 1 && packetList.get(0).getType() == DzhReqType.SCREEN) {
            DzhReqPacket dzhReqPacket_2946 = new DzhReqPacket(2946, packetList.get(0).getSign());
            dzhReqPacket_2946.getByteOutputStreamUtil().writeInt(4);
            packetList.add(dzhReqPacket_2946);

            DzhReqPacket dzhReqPacket_2963 = new DzhReqPacket(2963, packetList.get(0).getSign());
            packetList.add(dzhReqPacket_2963);
        }

        ByteOutputStreamUtil byteOutputStreamUtil = new ByteOutputStreamUtil();
        packetList.forEach(packet -> {
            byteOutputStreamUtil.writeByte(packet.getSign());

            byteOutputStreamUtil.writeShort(packet.getProtocol());

            byteOutputStreamUtil.writeByte(64);
            byteOutputStreamUtil.writeByte(0);

            byteOutputStreamUtil.writeStream(packet.getByteOutputStreamUtil());
        });
        returnValues.add(byteOutputStreamUtil.toBytes());

        byteOutputStreamUtil.close();
    }
}
