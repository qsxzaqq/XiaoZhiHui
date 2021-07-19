package cc.i9mc.stocks.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by JinVan on 2021-04-02.
 */
public class ByteOutputStreamUtil {
    private final DataOutputStream dataOutputStream;
    private final ByteArrayOutputStream byteArrayOutputStream;

    public ByteOutputStreamUtil() {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    }

    public void writeBytes(byte[] raw) {
        try {
            this.dataOutputStream.write(raw);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void writeStream(ByteOutputStreamUtil byteOutputStreamUtil) {
        try {
            byte[] data = byteOutputStreamUtil.toBytes();
            writeShort(data.length);
            this.dataOutputStream.write(data);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void writeByte(byte raw) {
        try {
            this.dataOutputStream.write(raw);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void writeByte(int raw) {
        try {
            this.dataOutputStream.write(raw);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void writeShort(int i) {
        try {
            this.dataOutputStream.write(i & 255);
            this.dataOutputStream.write((i >>> 8) & 255);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void writeInt(int i) {
        try {
            this.dataOutputStream.write(i & 255);
            this.dataOutputStream.write((i >>> 8) & 255);
            this.dataOutputStream.write((i >>> 16) & 255);
            this.dataOutputStream.write((i >>> 24) & 255);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void writeStringByShort(String str) {
        if (str == null) {
            str = "";
        }

        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            writeShort(bytes.length);
            this.dataOutputStream.write(bytes);
        } catch (Exception ignored) {
        }
    }

    public void writeStringByInt(String str) {
        if (str == null) {
            str = "";
        }

        try {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            writeInt(bytes.length);
            this.dataOutputStream.write(bytes);
        } catch (Exception ignored) {
        }
    }

    public byte[] toBytes() {
        return this.byteArrayOutputStream.toByteArray();
    }

    public void close() {
        try {
            if (this.byteArrayOutputStream != null) {
                this.byteArrayOutputStream.close();
            }
            this.dataOutputStream.close();
        } catch (IOException ignored) {
        }
    }
}
