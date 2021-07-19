package cc.i9mc.stocks.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by JinVan on 2021-04-01.
 */
public class ByteInputStreamUtil {
    private final DataInputStream dataInputStream;
    private final ByteArrayInputStream byteArrayInputStream;

    public ByteInputStreamUtil(byte[] data) {
        this.byteArrayInputStream = new ByteArrayInputStream(data);
        this.dataInputStream = new DataInputStream(byteArrayInputStream);
    }

    public int len() {
        try {
            return this.dataInputStream.available();
        } catch (IOException e) {
            return -1;
        }
    }

    public byte[] readBytes(int i) throws IOException {
        byte[] data = new byte[i];
        this.dataInputStream.read(data);

        return data;
    }

    public int readByte() {
        try {
            return this.dataInputStream.readByte();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public int readShort() {
        try {
            int read = this.dataInputStream.read();
            int read2 = this.dataInputStream.read();
            if ((read2 | read) < 0) {
                throw new EOFException();
            }

            return read + (read2 << 8);
        } catch (IOException e2) {
            return -1;
        }
    }

    public int readInt() {
        try {
            int read = this.dataInputStream.read();
            int read2 = this.dataInputStream.read();
            int read3 = this.dataInputStream.read();
            int read4 = this.dataInputStream.read();
            if ((read4 | read3 | read2 | read) < 0) {
                throw new EOFException();
            }

            return read + (read2 << 8) + (read3 << 16) + (read4 << 24);
        } catch (IOException e2) {
            return -1;
        }
    }

    public int readInt(int i) {
        if (i == 0) {
            return readShort();
        }
        return readInt();
    }

    public long readLong() {
        try {
            long read = this.dataInputStream.read();
            long read2 = this.dataInputStream.read();
            long read3 = this.dataInputStream.read();
            long read4 = this.dataInputStream.read();
            long read5 = this.dataInputStream.read();
            long read6 = this.dataInputStream.read();
            long read7 = this.dataInputStream.read();
            long read8 = this.dataInputStream.read();
            if ((read8 | read7 | read6 | read5 | read4 | read3 | read2 | read) < 0) {
                throw new EOFException();
            }
            return read + (read2 << 8) + (read3 << 16) + (read4 << 24) + (read5 << 32) + (read6 << 40) + (read7 << 48) + (read8 << 56);
        } catch (IOException e2) {
            return 0;
        }
    }

    public String readString() {
        try {
            byte[] bArr = new byte[readShort()];
            this.dataInputStream.read(bArr);

            return new String(bArr, StandardCharsets.UTF_8);
        } catch (IOException e2) {
            return null;
        }
    }


    public void close() {
        try {
            this.dataInputStream.close();
            if (this.byteArrayInputStream != null) {
                this.byteArrayInputStream.close();
            }
        } catch (IOException ignored) {
        }
    }

    public int readDdx() {
        int g = readShort();
        return (g >> 15) == 0 ? g : ((~g + 1) & 65535) * -1;
    }
}
