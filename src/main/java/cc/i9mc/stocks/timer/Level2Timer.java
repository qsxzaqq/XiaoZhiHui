package cc.i9mc.stocks.timer;

import cc.i9mc.stocks.common.dzh.DzhReqPacket;
import cc.i9mc.stocks.common.dzh.DzhReqType;
import cc.i9mc.stocks.common.dzh.packets.Stock3018_100Packet;
import cc.i9mc.stocks.components.DzhQuotationComponent;
import cc.i9mc.stocks.config.DzhConfig;
import cc.i9mc.stocks.datasource.entities.Itemized;
import cc.i9mc.stocks.service.ItemizedService;
import cc.i9mc.stocks.service.RedisService;
import cc.i9mc.stocks.utils.ByteInputStreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by JinVan on 2021-04-04.
 */
@Slf4j
@Component
public class Level2Timer implements ApplicationRunner {
    private final ConcurrentHashMap<Byte, String> codeBySign = new ConcurrentHashMap<>();

    @Autowired
    private RedisService redisService;
    @Autowired
    private DzhConfig dzhConfig;
    @Autowired
    private DzhQuotationComponent dzhQuotationComponent;
    @Autowired
    private ItemizedService itemizedService;

    @Override
    public void run(ApplicationArguments args) {
        dzhQuotationComponent.addListener((packet) -> {
            if (packet.getProtocol() == 3018) {
                ByteInputStreamUtil byteInputStreamUtil = new ByteInputStreamUtil(packet.getData());
                if (byteInputStreamUtil.readByte() == 2) {
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

                            codeBySign.forEach((k, v) -> {
                                if (k.equals(packet.getSign())) {
                                    if (dealDatumPackets.isEmpty()) {
                                        redisService.del("Dzh_3018_100_lastId:" + v);
                                        return;
                                    }

                                    for (Stock3018_100Packet stock3018100Packet1 : dealDatumPackets) {
                                        itemizedService.writeData(dzhConfig.getNode(), v, stock3018100Packet1);
                                    }

                                    log.info("PriceAnalysisManager 3018_100 list.size = {} stock = {}", dealDatumPackets.size(), v);
                                }
                            });
                        }
                    }
                }
                byteInputStreamUtil.close();
            } else if (packet.getProtocol() == 2915) {
                ByteInputStreamUtil byteInputStreamUtil = new ByteInputStreamUtil(packet.getData());
                byteInputStreamUtil.readInt();
                byteInputStreamUtil.readInt();
                byteInputStreamUtil.readInt();
                byteInputStreamUtil.readInt();
                System.out.println(byteInputStreamUtil.readShort());
            }
            System.out.println(packet.getProtocol());
        });
    }

    @Scheduled(initialDelay = 2000, fixedRate = 1000)
    public void stock3018_100_scheduled() {
     //   for (String stock : dzhConfig.getStocks_3018_100()) {
            DzhReqPacket dzhReqPacket = new DzhReqPacket(2915);
            dzhReqPacket.setType(DzhReqType.SCREEN);
            dzhReqPacket.getByteOutputStreamUtil().writeStringByShort("SZ002341");
            dzhReqPacket.getByteOutputStreamUtil().writeByte(1);

            //DzhReqPacket dzhReqPacket = Stock3018_100Packet.encode(stock, dzhConfig.getDevice());
            dzhQuotationComponent.loadSign(dzhReqPacket);
         //   codeBySign.put(dzhReqPacket.getSign(), stock);
            dzhQuotationComponent.send(dzhReqPacket);
       // }

        log.info("=====>>>>>使用cron  {}", System.currentTimeMillis());
    }
}
