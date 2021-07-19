package cc.i9mc.stocks.common.dzh;

import cc.i9mc.stocks.utils.ByteInputStreamUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * Created by JinVan on 2021-04-01.
 */
@Slf4j
public class DzhDecoder extends MessageToMessageDecoder<byte[]> {
    private static byte[] receiveData = null;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, byte[] bytes, List<Object> list) {
        log.debug("response length: {}   ", bytes.length);
        if (receiveData == null || receiveData.length == 0) {
            receiveData = bytes;
        } else {
            byte[] data = new byte[(receiveData.length + bytes.length)];
            System.arraycopy(receiveData, 0, data, 0, receiveData.length);
            System.arraycopy(bytes, 0, data, receiveData.length, bytes.length);
            receiveData = data;
        }

        ByteInputStreamUtil byteInputStreamUtil = new ByteInputStreamUtil(receiveData);
        while (true) {
            if (byteInputStreamUtil.len() <= 0) {
                break;
            }

            try {
                byte sign = (byte) byteInputStreamUtil.readByte();

                int protocol = byteInputStreamUtil.readShort();
                log.debug("response protocol: {}   ", protocol);

                if (protocol != 0) {
                    int g3 = byteInputStreamUtil.readShort();
                    int i3 = g3 & 2;
                    int i4 = g3 & 4;

                    int i2;
                    if ((g3 & 8) == 8 || protocol == 2956) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }

                    boolean z = sign == 0 || protocol == 2907;


                    DzhPacket dzhPacket = new DzhPacket(protocol, sign);
                    dzhPacket.setZ(z);

                    int c2 = byteInputStreamUtil.readInt(i2);
                    if (c2 < 0 || byteInputStreamUtil.len() < c2) {
                        break;
                    }

                    try {
                        byte[] a2 = byteInputStreamUtil.readBytes(c2);
                        if (i3 == 2) {
                            dzhPacket.setLongType(true);
                            switch (protocol) {
                 /*               case 3320:
                                    try {
                                        dzhPacket.setData(CBitStream.Expend3320Data(a2));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 2942:
                                    dzhPacket.setData(CSimpleBitStream.ExpandMinData(a2));
                                    break;
                                case 2944:
                                    dzhPacket.setData(CSimpleBitStream.ExpandKLineData(a2));
                                    break;
                                case 2933:
                                    dzhPacket.setData(CSimpleBitStream.ExpandBSData(a2));
                                    break;
                                case 2985:
                                    dzhPacket.setData(CSimpleBitStream.ExpandHisMin(a2));
                                    break;*/
                                default:
                                    dzhPacket.setData(a2);
                                    break;
                            }
                        } else {
                            dzhPacket.setLongType(false);
                            if (i4 == 4) {
                                dzhPacket.setUnZlib(true);
                            }
                            dzhPacket.setData(a2);
                        }
                        list.add(dzhPacket);

                        if (byteInputStreamUtil.len() > 0) {
                            byte[] data = new byte[byteInputStreamUtil.len()];
                            System.arraycopy(receiveData, receiveData.length - byteInputStreamUtil.len(), data, 0, byteInputStreamUtil.len());
                            receiveData = data;
                        } else {
                            receiveData = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        receiveData = null;
                        byteInputStreamUtil.close();
                    }
                }
            } catch (RuntimeException e) {
                receiveData = null;
                byteInputStreamUtil.close();
            }
        }

        log.debug("receive data length：{}", receiveData == null ? "null" : receiveData.length);
    }
}
