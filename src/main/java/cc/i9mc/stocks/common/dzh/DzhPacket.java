package cc.i9mc.stocks.common.dzh;

import lombok.Data;

/**
 * Created by JinVan on 2021-04-01.
 */
@Data
public class DzhPacket {
    private int protocol = 0;
    private byte sign = -1;
    private byte[] data;

    private boolean z = false;
    private boolean unZlib = false;
    private boolean longType = false;

    public DzhPacket(int protocol, byte sign) {
        this.protocol = protocol;
        this.sign = sign;
    }
}
