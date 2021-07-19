package cc.i9mc.stocks.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by JinVan on 2021-04-01.
 */
@Configuration
@ConfigurationProperties(value = "dzh", ignoreInvalidFields = true)
@Data
public class DzhConfig {
    /**
     * dzh:
     *   phone:
     *     device: "300000000327467"
     *   server:
     *     ip: "122.112.221.118"
     *     port: 12345
     *
     **/

    private String node;

    @Value("${dzh.phone.device}")
    private String device;

    @Value("${dzh.server.ip}")
    private String ip;

    @Value("${dzh.server.port}")
    private Integer port;

    private List<String> stocks_3018_100;
}
