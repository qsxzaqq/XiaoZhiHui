package cc.i9mc.stocks.common.dzh.packets;

import cc.i9mc.stocks.common.dzh.DzhReqPacket;
import cc.i9mc.stocks.common.dzh.DzhReqType;
import cc.i9mc.stocks.utils.ByteInputStreamUtil;
import cc.i9mc.stocks.utils.ByteOutputStreamUtil;
import cc.i9mc.stocks.utils.PriceUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * Created by JinVan on 2021-04-01.
 */
@Data
public class Stock3018_100Packet {
    private int delen;
    private boolean iswtbuyEndOrder;
    private boolean iswtbuyFirstOrder;
    private boolean iswtbuyIdEqualNext;
    private boolean iswtbuyIdEqualPre;
    private boolean iswtbuyShowDownArrow;
    private boolean iswtbuyShowUpArrow;
    private boolean iswtbuyShowVol;
    private boolean iswtsellEndOrder;
    private boolean iswtsellFirstOrder;
    private boolean iswtsellIdEqualNext;
    private boolean iswtsellIdEqualPre;
    private boolean iswtsellShowDownArrow;
    private boolean iswtsellShowUpArrow;
    private boolean iswtsellShowVol;
    private int price;
    private long timeStamp;
    private int vol;
    private int wtRealBuyId;
    private int wtRealSellId;
    private int wtbuyId;
    private int wtbuyPrice;
    private int wtbuyVol;
    private int wtsellId;
    private int wtsellPrice;
    private int wtsellVol;
    private int zbcjId;

    public static DzhReqPacket encode(String stock, String imei) {
        DzhReqPacket dzhReqPacket1 = new DzhReqPacket(3018);
        dzhReqPacket1.setType(DzhReqType.PROTOCOL_SPECIAL);
        ByteOutputStreamUtil byteOutputStreamUtil1 = dzhReqPacket1.getByteOutputStreamUtil();
        byteOutputStreamUtil1.writeByte(3);
        byteOutputStreamUtil1.writeShort(100);
        byteOutputStreamUtil1.writeShort(1);

        ByteOutputStreamUtil byteOutputStreamUtil2 = new ByteOutputStreamUtil();
        JSONObject json = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("platform", "Gphone");
        header.put("version", "9.26");
        header.put("device_id", imei);
        json.put("header", header);
        json.put("stock", stock);
        json.put("deal_id", -1);
        json.put("deal_id_direction", "next");
        json.put("btime", 0);
        json.put("etime", 0);
        json.put("mtime", 0);
        json.put("filter_buy_vol", 0);
        json.put("filter_sell_vol", 0);
        byteOutputStreamUtil2.writeStringByInt(json.toString());

        byte[] data = byteOutputStreamUtil2.toBytes();
        byteOutputStreamUtil2.close();

        byteOutputStreamUtil1.writeInt(data.length);
        byteOutputStreamUtil1.writeInt(1);
        byteOutputStreamUtil1.writeBytes(data);

        return dzhReqPacket1;
    }

    public void decode(ByteInputStreamUtil byteInputStreamUtil, int i) {
        boolean z = true;
        try {
            this.timeStamp = byteInputStreamUtil.readLong();
            this.price = byteInputStreamUtil.readInt();
            this.vol = byteInputStreamUtil.readInt();
            this.delen = byteInputStreamUtil.readByte();
            this.zbcjId = byteInputStreamUtil.readInt();
            this.wtbuyId = byteInputStreamUtil.readInt();
            this.wtsellId = byteInputStreamUtil.readInt();
            this.wtbuyPrice = byteInputStreamUtil.readInt();
            this.wtsellPrice = byteInputStreamUtil.readInt();
            this.wtbuyVol = byteInputStreamUtil.readInt();
            this.wtsellVol = byteInputStreamUtil.readInt();
            if (i - 45 > 0) {
                for (int i2 = 0; i2 < i - 45; i2++) {
                    byteInputStreamUtil.readByte();
                }
            }

            this.wtRealBuyId = this.wtbuyId & 1073741823;
            this.wtRealSellId = this.wtsellId & 1073741823;
            this.iswtbuyFirstOrder = ((this.wtbuyId >>> 30) & 1) != 0;
            this.iswtbuyEndOrder = ((this.wtbuyId >>> 31) & 1) != 0;
            this.iswtsellFirstOrder = ((this.wtsellId >>> 30) & 1) != 0;
            if (((this.wtsellId >>> 31) & 1) == 0) {
                z = false;
            }
            this.iswtsellEndOrder = z;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean iswtbuyPositive() {
        return this.wtRealBuyId > this.wtRealSellId;
    }

    public String getPriceValue() {
        return PriceUtil.a(this.price, this.delen);
    }

    public float getPriceFloatValue() {
        try {
            return Float.parseFloat(getPriceValue());
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public int getDealVol() {
        return ((this.vol + 50) / 100);
    }

    public String getShowVol() {
        return "" + ((this.vol + 50) / 100);
    }

    public String getShowwtbuyVol() {
        return "" + ((this.wtbuyVol + 50) / 100);
    }

    public String getShowwtsellVol() {
        return "" + ((this.wtsellVol + 50) / 100);
    }

    public int getBuyVol() {
        return ((this.wtbuyVol + 50) / 100);
    }

    public int getSellVol() {
        return ((this.wtsellVol + 50) / 100);
    }

    @Override
    public String toString() {
        return "时间=" +
                DateUtil.date(timeStamp * 1000).toTimeStr() +
                ", " +
                "价格=" +
                getPriceValue() +
                ", " +
                "成交=" +
                getShowVol() +
                ", " +
                "卖单=" +
                getWtRealBuyId() +
                ", " +
                "卖单数=" +
                getShowwtbuyVol() +
                ", " +
                "买单=" +
                getWtRealSellId() +
                ", " +
                "买单数=" +
                getShowwtsellVol() +
                ", " +
                zbcjId;
    }
}
