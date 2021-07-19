package cc.i9mc.stocks.utils.zip;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.zip.*;

@Slf4j
public class ZipUtil {
    private static final String CHARSET_UTF8 = "UTF-8";
/*

    public static String compress(String str) {
        byte[] bytes = str.getBytes();
        if (bytes.length <= 0) {
            return null;
        }

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bytes);
            gZIPOutputStream.close();
            return a.b(byteArrayOutputStream.toByteArray());
        } catch (Exception e2) {
            log.error("Error in compress:" + e2.getMessage());
            return null;
        }
    }

    public static String uncompress(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] uncompressGzip = uncompressGzip(base64StrToGZipData(str));
            if (uncompressGzip != null) {
                str2 = new String(uncompressGzip, "UTF-8");
            } else {
                str2 = null;
            }
            return str2;
        } catch (Exception e2) {
            Functions.Log("Error in uncompress:" + e2.getMessage());
            return null;
        }
    }

    private static byte[] base64StrToGZipData(String str) {
        return a.a(str);
    }

    public static byte[] compressGzip(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e2) {
            Functions.Log("Error in compress:" + e2.getMessage());
            return null;
        }
    }

    */
/* JADX WARNING: Removed duplicated region for block: B:17:0x0041  *//*

    */
/* JADX WARNING: Removed duplicated region for block: B:27:0x0060  *//*

    */
/* Code decompiled incorrectly, please refer to instructions dump. *//*

    public static byte[] uncompressGzip(byte[] r7) {
        */
/*
        // Method dump skipped, instructions count: 111
        *//*

        throw new UnsupportedOperationException("Method not decompiled: com.android.dazhihui.util.zip.ZipUtil.uncompressGzip(byte[]):byte[]");
    }

    public static byte[] compressZip(byte[] bArr) {
        byte[] bArr2 = null;
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            ZipEntry zipEntry = new ZipEntry("zip");
            zipEntry.setSize((long) bArr.length);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(bArr);
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            bArr2 = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bArr2;
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
            return bArr2;
        }
    }

    public static byte[] unCompressZip(byte[] bArr) {
        Exception e2;
        byte[] bArr2 = null;
        if (bArr != null && bArr.length > 0) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
                while (zipInputStream.getNextEntry() != null) {
                    byte[] bArr3 = new byte[1024];
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        int read = zipInputStream.read(bArr3, 0, bArr3.length);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr3, 0, read);
                    }
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    try {
                        byteArrayOutputStream.flush();
                        byteArrayOutputStream.close();
                        bArr2 = byteArray;
                    } catch (Exception e3) {
                        bArr2 = byteArray;
                        e2 = e3;
                        ThrowableExtension.printStackTrace(e2);
                        return bArr2;
                    }
                }
                zipInputStream.close();
                byteArrayInputStream.close();
            } catch (Exception e4) {
                e2 = e4;
                ThrowableExtension.printStackTrace(e2);
                return bArr2;
            }
        }
        return bArr2;
    }

    public static String byte2hex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b2 : bArr) {
            String hexString = Integer.toHexString(b2 & 255);
            if (hexString.length() == 1) {
                sb.append("0");
            }
            sb.append(hexString.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] hex2byte(byte[] bArr) {
        byte[] bArr2 = new byte[(bArr.length / 2)];
        for (int i = 0; i < bArr.length; i += 2) {
            bArr2[i / 2] = (byte) Integer.parseInt(new String(bArr, i, 2), 16);
        }
        return bArr2;
    }

    public static byte[] compressZlib(byte[] bArr) {
        byte[] bArr2 = new byte[0];
        Deflater deflater = new Deflater();
        deflater.reset();
        deflater.setInput(bArr);
        deflater.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
        try {
            byte[] bArr3 = new byte[1024];
            while (!deflater.finished()) {
                byteArrayOutputStream.write(bArr3, 0, deflater.deflate(bArr3));
            }
            bArr = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        } catch (Exception e3) {
            ThrowableExtension.printStackTrace(e3);
            try {
                byteArrayOutputStream.close();
            } catch (IOException e4) {
                ThrowableExtension.printStackTrace(e4);
            }
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e5) {
                ThrowableExtension.printStackTrace(e5);
            }
            throw th;
        }
        deflater.end();
        return bArr;
    }

    public static void compressZlib(byte[] bArr, OutputStream outputStream) {
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream);
        try {
            deflaterOutputStream.write(bArr, 0, bArr.length);
            deflaterOutputStream.finish();
            deflaterOutputStream.flush();
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }

    public static byte[] decompressZlib(byte[] bArr) {
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(bArr);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
        try {
            byte[] bArr3 = new byte[1024];
            while (!inflater.finished()) {
                byteArrayOutputStream.write(bArr3, 0, inflater.inflate(bArr3));
            }
            bArr = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        } catch (Exception e3) {
            ThrowableExtension.printStackTrace(e3);
            try {
                byteArrayOutputStream.close();
            } catch (IOException e4) {
                ThrowableExtension.printStackTrace(e4);
            }
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e5) {
                ThrowableExtension.printStackTrace(e5);
            }
            throw th;
        }
        inflater.end();
        return bArr;
    }

    public static byte[] decompressZlib(InputStream inputStream) {
        int i = 1024;
        InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                i = inflaterInputStream.read(bArr, 0, i);
                if (i <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
*/

}
