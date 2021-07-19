package cc.i9mc.stocks.common.dzh;

import cc.i9mc.stocks.common.dzh.packets.ShortThreadPacket;
import cc.i9mc.stocks.common.dzh.packets.Stock3018_100Packet;
import cc.i9mc.stocks.common.dzh.packets.StockPacket;
import cc.i9mc.stocks.components.DzhQuotationComponent;
import cc.i9mc.stocks.service.ItemizedService;
import cc.i9mc.stocks.utils.ByteInputStreamUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by JinVan on 2021-04-01.
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<DzhPacket> {
    private final DzhQuotationComponent dzhQuotationComponent;

    public NettyClientHandler(DzhQuotationComponent dzhQuotationComponent) {
        this.dzhQuotationComponent = dzhQuotationComponent;
    }

/*
    private final ItemizedService itemizedService;

    private int zbcjId = -1;
*/

    @SneakyThrows
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DzhPacket dzhPacket) {
        log.debug("协议: {}  数据len {} ", dzhPacket.getProtocol(), dzhPacket.getData().length);

        for (DzhQuotationComponent.ResultRunnable result : dzhQuotationComponent.getResults()) {
            result.run(dzhPacket);
        }


        /*if (dzhPacket.getProtocol() == 3018) {
            ByteInputStreamUtil byteInputStreamUtil = new ByteInputStreamUtil(dzhPacket.getData());
            switch (byteInputStreamUtil.readByte()) {
                case 2:
                    if (byteInputStreamUtil.readShort() == 100) {
                        byteInputStreamUtil.readShort();
                        byteInputStreamUtil.readInt();
                        byteInputStreamUtil.readInt();

                        int size = byteInputStreamUtil.readInt();

                        if (byteInputStreamUtil.readInt() == 0) {
                            byte[] data = byteInputStreamUtil.readBytes(size);
                            byteInputStreamUtil.close();
                            byteInputStreamUtil = new ByteInputStreamUtil(data);

                            List<Stock3018_100Packet> dealDatumPackets = new ArrayList<>();
                            int ig = byteInputStreamUtil.readShort();
                            int ig1 = byteInputStreamUtil.readInt();

                            for (int i = 0; i < ig1; i++) {
                                Stock3018_100Packet stock3018_10 = new Stock3018_100Packet();
                                stock3018_10.decode(byteInputStreamUtil, ig);
                                dealDatumPackets.add(stock3018_10);
                            }

    *//*                        for (Stock3018_100Packet stock3018100Packet1 : dealDatumPackets) {
                                if (zbcjId != -1 && stock3018100Packet1.getZbcjId() <= zbcjId) {
                                    continue;
                                }
                                zbcjId = stock3018100Packet1.getZbcjId();

                                Itemized itemized = new Itemized();
                                itemized.setCode("SH601899");
                                itemized.setTimeStamp(stock3018100Packet1.getTimeStamp());
                                itemized.setPrice(stock3018100Packet1.getPriceFloatValue());
                                itemized.setSellId(stock3018100Packet1.getWtRealSellId());
                                itemized.setSellVol(stock3018100Packet1.getSellVol());
                                itemized.setBuyId(stock3018100Packet1.getWtRealBuyId());
                                itemized.setBuyVol(stock3018100Packet1.getBuyVol());
                                itemizedService.save(itemized);

                               // log.info(stock3018100Packet1.toString());
                            }*//*
                            log.info("PriceAnalysisManager 3018_100 list.size = {}", dealDatumPackets.size());
                        }
                    }
                    break;
                case 3:
            }
            byteInputStreamUtil.close();
        } else*/  if (dzhPacket.getProtocol() == 2977) {
            ByteInputStreamUtil byteInputStreamUtil = new ByteInputStreamUtil(dzhPacket.getData());
            try {
                byteInputStreamUtil.readInt();
                byteInputStreamUtil.readInt();

                List<ShortThreadPacket> shortThreadPackets = new ArrayList<>();
                int g2 = byteInputStreamUtil.readShort();

                for (int i = 0; i < g2; i++) {
                    ShortThreadPacket shortThreadPacket = new ShortThreadPacket();
                    shortThreadPacket.decode2977_3205(byteInputStreamUtil);
                    shortThreadPackets.add(shortThreadPacket);
                }

                for (ShortThreadPacket shortThreadPacket : shortThreadPackets) {
                    log.info(shortThreadPacket.toString());
                }
                log.info("PriceAnalysisManager 2977 list.size = {}", shortThreadPackets.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            byteInputStreamUtil.close();
        } else if (dzhPacket.getProtocol() == 3205) {
            ByteInputStreamUtil byteInputStreamUtil = new ByteInputStreamUtil(dzhPacket.getData());
            try {
                byteInputStreamUtil.readByte();
                byteInputStreamUtil.readInt();
                byteInputStreamUtil.readInt();

                List<ShortThreadPacket> shortThreadPackets = new ArrayList<>();
                int g = byteInputStreamUtil.readShort();

                for (int i = 0; i < g; i++) {
                    ShortThreadPacket shortThreadPacket = new ShortThreadPacket();
                    shortThreadPacket.decode2977_3205(byteInputStreamUtil);
                    shortThreadPackets.add(shortThreadPacket);
                }

                for (ShortThreadPacket shortThreadPacket : shortThreadPackets) {
                    log.info(shortThreadPacket.toString());
                }
                log.info("PriceAnalysisManager 3205 list.size = {}", shortThreadPackets.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            byteInputStreamUtil.close();
        } else if (dzhPacket.getProtocol() == 2976) {
            ByteInputStreamUtil byteInputStreamUtil = new ByteInputStreamUtil(dzhPacket.getData());
            try {
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readShort();
                byteInputStreamUtil.readInt();
                byteInputStreamUtil.readInt();

                List<ShortThreadPacket> shortThreadPackets = new ArrayList<>();
                int g = byteInputStreamUtil.readShort();

                for (int i = 0; i < g; i++) {
                    ShortThreadPacket shortThreadPacket = new ShortThreadPacket();
                    shortThreadPacket.decode2976(byteInputStreamUtil);
                    shortThreadPackets.add(shortThreadPacket);
                }

                for (ShortThreadPacket shortThreadPacket : shortThreadPackets) {
                    log.info(shortThreadPacket.toString());
                }
                log.info("PriceAnalysisManager 2976 list.size = {}", shortThreadPackets.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            byteInputStreamUtil.close();
        } else if (dzhPacket.getProtocol() == 2973) {
            ByteInputStreamUtil byteInputStreamUtil = new ByteInputStreamUtil(dzhPacket.getData());
            if (byteInputStreamUtil.readShort() == 4) {
                StockPacket.Api2973 api2973 = new StockPacket.Api2973();
                api2973.decode(byteInputStreamUtil);
                log.info(api2973.toString());
            }
            byteInputStreamUtil.close();
        } else if (dzhPacket.getProtocol() == 2930) {
            ByteInputStreamUtil byteInputStreamUtil = new ByteInputStreamUtil(dzhPacket.getData());
            StockPacket.Api2930 api2930 = new StockPacket.Api2930();
            api2930.decode(byteInputStreamUtil);
            log.info(api2930.toString());
            byteInputStreamUtil.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("与行情服务器 [" + dzhQuotationComponent.dzhConfig.getIp() + ":" + dzhQuotationComponent.dzhConfig.getPort() + "] 断开连接 ,正在重连");
        dzhQuotationComponent.start();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}