package cc.i9mc.stocks.components;

import cc.i9mc.stocks.common.dzh.*;
import cc.i9mc.stocks.config.DzhConfig;
import cc.i9mc.stocks.service.SpringLogService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by JinVan on 2021-04-01.
 */
@Component
@Slf4j
public class DzhQuotationComponent {
    private final byte[] bypassSign = {0, 123, 125, 58};
    private byte sign = Byte.MIN_VALUE;

    @Autowired
    public DzhConfig dzhConfig;
    @Autowired
    private SpringLogService springLogService;
    //private int lastServer = 0;

    private final EventLoopGroup group = new NioEventLoopGroup();
    private SocketChannel socketChannel;

    @Getter
    private final List<ResultRunnable> results = new ArrayList<>();

    public void addListener(ResultRunnable resultRunnable) {
        results.add(resultRunnable);
    }

    public void send(DzhReqPacket dzhReqPacket) {

        socketChannel.writeAndFlush(dzhReqPacket);
    }

    public byte getSign() {
        sign = (byte) (sign + 1);
        for (byte b : bypassSign) {
            if (sign == b) {
                sign = (byte) (sign + 1);
            }
        }
        if (sign > Byte.MAX_VALUE) {
            sign = Byte.MIN_VALUE;
        }

        return sign;
    }

    public void sendMsg() {
        //盯盘
/*        DzhReqPacket dzhReqPacket = new DzhReqPacket(2976);
        ByteOutputStreamUtil byteOutputStreamUtil = dzhReqPacket.getByteOutputStreamUtil();
        byteOutputStreamUtil.writeStringByShort("SH601899");
        byteOutputStreamUtil.writeInt(-1);
        byteOutputStreamUtil.writeShort(100);
        byteOutputStreamUtil.writeInt(4);
        socketChannel.writeAndFlush(dzhReqPacket);*/

        // 资金流入
/*        DzhReqPacket dzhReqPacket = new DzhReqPacket(2973);
        ByteOutputStreamUtil byteOutputStreamUtil = dzhReqPacket.getByteOutputStreamUtil();
        byteOutputStreamUtil.writeShort(4);
        byteOutputStreamUtil.writeStringByShort("SH601899");
        socketChannel.writeAndFlush(dzhReqPacket);*/

        // 分单
/*        DzhReqPacket dzhReqPacket = new DzhReqPacket(2930);
        ByteOutputStreamUtil byteOutputStreamUtil = dzhReqPacket.getByteOutputStreamUtil();
        byteOutputStreamUtil.writeStringByShort("SH601899");
        socketChannel.writeAndFlush(dzhReqPacket);*/

        // level2z逐笔
      /*  DzhReqPacket dzhReqPacket1 = new DzhReqPacket(3018);
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
        header.put("device_id", "300000000327467");
        json.put("header", header);
        json.put("stock", "SH601899");
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

        socketChannel.writeAndFlush(dzhReqPacket1);*/

        //socketChannel.writeAndFlush(HexUtil.decodeHex("d9ca0b0000e4000364000100d7000000c8a56900d30000007b22686561646572223a7b22706c6174666f726d223a224770686f6e65222c2276657273696f6e223a22392e3236222c226465766963655f6964223a22333030303030303030333237343637227d2c2273746f636b223a225348363031383939222c226465616c5f6964223a2d312c226465616c5f69645f646972656374696f6e223a226e657874222c226274696d65223a302c226574696d65223a302c226d74696d65223a302c2266696c7465725f6275795f766f6c223a302c2266696c7465725f73656c6c5f766f6c223a32303030307d"));
    }

    @PostConstruct
    public void start()  {
        // String[] server = dzhConfig.getServers().get(lastServer).split(":");

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(dzhConfig.getIp(), dzhConfig.getPort())
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ByteArrayEncoder());
                        pipeline.addLast(new DzhEncoder());

                        pipeline.addLast(new ByteArrayDecoder());
                        pipeline.addLast(new DzhDecoder());
                        pipeline.addLast(new NettyClientHandler(DzhQuotationComponent.this));
                    }
                });

        ChannelFuture future = bootstrap.connect();
        future.addListener((ChannelFutureListener) future1 -> {
            boolean success = future1.isSuccess();

            springLogService.create(dzhConfig.getNode(), success);
            if (success) {
                //log.info("连接行情服务器成功 [" + server[0] + ":" + server[1] + "]");
                log.info("连接行情服务器成功 [" + dzhConfig.getIp() + ":" + dzhConfig.getPort() + "]");
            } else {
        /*        if (dzhConfig.getServers().size() > (lastServer + 1)) {
                    lastServer++;
                } else {
                    lastServer = 0;
                }
                log.info("连接行情服务器失败 [" + server[0] + ":" + server[1] + "] ,使用下一轮行情服务器");*/
                log.info("连接行情服务器失败 [" + dzhConfig.getIp() + ":" + dzhConfig.getPort() + "] ,正在重连");
                future1.channel().eventLoop().schedule(this::start, 20, TimeUnit.SECONDS);
            }
        });

        socketChannel = (SocketChannel) future.channel();
    }

    public void loadSign(DzhReqPacket dzhReqPacket) {
        dzhReqPacket.setSign(getSign());
    }

    public interface ResultRunnable {
        void run(DzhPacket packet) throws IOException;
    }
}
