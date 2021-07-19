package cc.i9mc.stocks.common.dzh;

import cc.i9mc.stocks.utils.ByteOutputStreamUtil;
import lombok.Data;

/**
 * Created by JinVan on 2021-04-02.
 */
@Data
public class DzhReqPacket {
    private DzhReqType type = DzhReqType.SCREEN;
    private int protocol = 0;
    private byte sign = -1;

    private ByteOutputStreamUtil byteOutputStreamUtil;

    public DzhReqPacket(int protocol, byte sign) {
        this.protocol = protocol;
        this.sign = sign;

        this.byteOutputStreamUtil = new ByteOutputStreamUtil();
    }

    public DzhReqPacket(int protocol) {
        this.protocol = protocol;

        this.byteOutputStreamUtil = new ByteOutputStreamUtil();
    }
}
