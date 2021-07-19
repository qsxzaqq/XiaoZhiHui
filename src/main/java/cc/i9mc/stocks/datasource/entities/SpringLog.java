package cc.i9mc.stocks.datasource.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by JinVan on 2021-04-06.
 */
@Data
@Document("spring_logs")
public class SpringLog {
    private Long timeStamp;
    private String node;
    private boolean success;
}
