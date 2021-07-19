package cc.i9mc.stocks.datasource.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Created by JinVan on 2021-04-02.
 */
@Data
@Document("itemizes")
public class Itemized {
    private String node;
    private String code;
    private Long timeStamp;
    private Float price;
    private Integer deal;
    private Integer sellId;
    private Integer sellVol;
    private Integer buyId;
    private Integer buyVol;
}
