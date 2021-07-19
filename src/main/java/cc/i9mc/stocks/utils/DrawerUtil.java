package cc.i9mc.stocks.utils;

/**
 * Created by JinVan on 2021-04-02.
 */
public class DrawerUtil {

    public static long parseLongWithSign(int i) {
        int i2 = (i >>> 30) & 3;
        if (i2 != 0) {
            long j = 1073741823 & i;
            if (((i >>> 29) & 1) != 0) {
                j = -(~(j | ((long) (((i >>> 30) | 12) << 28)))) - 1;
            }
            return j << (i2 * 4);
        } else if (((i >>> 29) & 1) == 0) {
            return i;
        } else {
            return (((~((((i >>> 30) | 12) << 28) | i)) + 1)) * -1;
        }
    }
}
