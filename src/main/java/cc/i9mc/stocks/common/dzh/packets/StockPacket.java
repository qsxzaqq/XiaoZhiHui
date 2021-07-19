package cc.i9mc.stocks.common.dzh.packets;

import cc.i9mc.stocks.utils.ByteInputStreamUtil;
import cc.i9mc.stocks.utils.DrawerUtil;
import lombok.Data;

/**
 * Created by JinVan on 2021-04-02.
 */
public class StockPacket {

    @Data
    public static class Api2930 {
        private int buyAvgPrice;
        private int buyOrder4BBig;
        private int buyOrder4Big;
        private int buyOrder4Middle;
        private int buyOrder4Small;
        private int ddx;
        private int orderNumCha;
        private int sellAvgPrice;
        private int sellOrder4BBig;
        private int sellOrder4Big;
        private int sellOrder4Middle;
        private int sellOrder4Small;
        private int totalBuy;
        private int totalSell;

        public void decode(ByteInputStreamUtil byteInputStreamUtil) {
            totalSell = byteInputStreamUtil.readInt();
            sellAvgPrice = byteInputStreamUtil.readInt();
            totalBuy = byteInputStreamUtil.readInt();
            buyAvgPrice = byteInputStreamUtil.readInt();
            ddx = byteInputStreamUtil.readDdx();
            orderNumCha = byteInputStreamUtil.readInt();
            buyOrder4BBig = byteInputStreamUtil.readInt();
            sellOrder4BBig = byteInputStreamUtil.readInt();
            buyOrder4Big = byteInputStreamUtil.readInt();
            sellOrder4Big = byteInputStreamUtil.readInt();
            buyOrder4Middle = byteInputStreamUtil.readInt();
            sellOrder4Middle = byteInputStreamUtil.readInt();
            buyOrder4Small = byteInputStreamUtil.readInt();
            sellOrder4Small = byteInputStreamUtil.readInt();
        }

        @Override
        public String toString() {
            return "共买=" +
                    (buyOrder4BBig + buyOrder4Big + buyOrder4Middle + buyOrder4Small) +
                    "," +
                    "共卖=" +
                    (sellOrder4BBig + sellOrder4Big + sellOrder4Middle + sellOrder4Small);
        }
    }

    @Data
    public static class Api2973 {
        private long bigDayCapitalInflows = 0;

        public void decode(ByteInputStreamUtil byteInputStreamUtil) {
            bigDayCapitalInflows = DrawerUtil.parseLongWithSign(byteInputStreamUtil.readInt());
        }

        @Override
        public String toString() {
            return "资金流入=" +
                    bigDayCapitalInflows;
        }
    }


}
