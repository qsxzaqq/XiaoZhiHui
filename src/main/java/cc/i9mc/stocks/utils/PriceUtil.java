package cc.i9mc.stocks.utils;

/**
 * Created by JinVan on 2021-04-01.
 */
public class PriceUtil {
    public static String a(int i, int i2) {
        return i == 0 ? "--" : b(i, i2);
    }

    public static String b(int i, int i2) {
        StringBuilder valueOf = new StringBuilder(String.valueOf(Math.abs(i)));
        while (valueOf.length() <= i2) {
            valueOf.insert(0, "0");
        }
        if (i < 0) {
            valueOf.insert(0, "-");
        }
        return i2 <= 0 ? valueOf.toString() : valueOf.substring(0, valueOf.length() - i2) + "." + valueOf.substring(valueOf.length() - i2);
    }
}
