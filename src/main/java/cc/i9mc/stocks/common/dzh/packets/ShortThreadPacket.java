package cc.i9mc.stocks.common.dzh.packets;

import cc.i9mc.stocks.utils.ByteInputStreamUtil;
import lombok.Data;

@Data
public class ShortThreadPacket {
    private String code;
    private String name;
    private String time;
    private int type;
    private int vol;

    public void decode2977_3205(ByteInputStreamUtil byteInputStreamUtil) {
        try {
            this.code = byteInputStreamUtil.readString();
            this.name = byteInputStreamUtil.readString();
            this.type = byteInputStreamUtil.readByte();

            String dateStr = String.valueOf(byteInputStreamUtil.readShort());
            if (dateStr.length() == 4) {
                this.time = dateStr.substring(0, 2) + ":" + dateStr.substring(2, 4);
            } else if (dateStr.length() == 3) {
                this.time = dateStr.substring(0, 1) + ":" + dateStr.substring(1, 3);
            }
            this.vol = byteInputStreamUtil.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decode2976(ByteInputStreamUtil byteInputStreamUtil) {
        try {
            this.type = byteInputStreamUtil.readByte();

            String dateStr = String.valueOf(byteInputStreamUtil.readShort());
            if (dateStr.length() == 4) {
                this.time = dateStr.substring(0, 2) + ":" + dateStr.substring(2, 4);
            } else if (dateStr.length() == 3) {
                this.time = dateStr.substring(0, 1) + ":" + dateStr.substring(1, 3);
            }
            this.vol = byteInputStreamUtil.readInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "时间=" +
                time +
                ", " +
                "名称=" +
                name +
                ", " +
                "事件=" +
                typeStr(type) +
                ", " +
                "value=" +
                vol;
    }

    public String typeStr(int type) {
        switch (type) {
            case 0:
                return "大笔买入";
            case 1:
                return "大笔卖出";
            case 2:
                return "机构吃货";
            case 3:
                return "机构吐货";
            case 4:
                return "机构委买";
            case 5:
                return "机构委卖";
            case 6:
                return "火箭发射";
            case 7:
                return "快速反弹";
            case 8:
                return "高台跳水";
            case 9:
                return "加速下跌";
            case 10:
                return "封涨停板";
            case 11:
                return "封跌停板";
            case 12:
                return "打开涨停";
            case 13:
                return "打开跌停";
            case 14:
                return "有大卖盘";
            case 15:
                return "有大买盘";
            case 16:
                return "拉升指数";
            case 17:
                return "打压指数";
            case 18:
                return "买单分单";
            case 19:
                return "卖单分单";
            case 20:
                return "买入撤单";
            case 21:
                return "卖出撤单";
            case 22:
                return "买入新单";
            case 23:
                return "卖出新单";
            default:
                return "";
        }
    }
}
