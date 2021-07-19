package cc.i9mc.stocks.service;

import cc.i9mc.stocks.common.dzh.packets.Stock3018_100Packet;
import cc.i9mc.stocks.datasource.entities.Itemized;

/**
 * Created by JinVan on 2021-04-02.
 */
public interface ItemizedService {
    void save(Itemized itemized);
    void writeData(String node, String code, Stock3018_100Packet stock3018_100Packet);
}
