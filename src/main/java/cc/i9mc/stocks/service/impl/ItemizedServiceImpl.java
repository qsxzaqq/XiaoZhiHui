package cc.i9mc.stocks.service.impl;

import cc.i9mc.stocks.common.dzh.packets.Stock3018_100Packet;
import cc.i9mc.stocks.datasource.entities.Itemized;
import cc.i9mc.stocks.service.ItemizedService;
import cc.i9mc.stocks.service.RedisService;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by JinVan on 2021-04-02.
 */
@Service
public class ItemizedServiceImpl implements ItemizedService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Itemized itemized) {
        mongoTemplate.save(itemized);
    }

    @Override
    @Async("逐笔写入数据队列")
    public void writeData(String node, String code, Stock3018_100Packet stock3018_100Packet) {
        Integer id = (Integer) redisService.get("Dzh_3018_100_lastId:" + code);
        if (id != null && stock3018_100Packet.getZbcjId() <= id) {
            return;
        }

        Itemized itemized = new Itemized();
        itemized.setNode(node);
        itemized.setCode(code);
        itemized.setTimeStamp(stock3018_100Packet.getTimeStamp());
        itemized.setPrice(stock3018_100Packet.getPriceFloatValue());
        itemized.setDeal(stock3018_100Packet.getDealVol());
        itemized.setSellId(stock3018_100Packet.getWtRealSellId());
        itemized.setSellVol(stock3018_100Packet.getSellVol());
        itemized.setBuyId(stock3018_100Packet.getWtRealBuyId());
        itemized.setBuyVol(stock3018_100Packet.getBuyVol());
        save(itemized);

        redisService.set("Dzh_3018_100_lastId:" + code, stock3018_100Packet.getZbcjId());
    }
}
