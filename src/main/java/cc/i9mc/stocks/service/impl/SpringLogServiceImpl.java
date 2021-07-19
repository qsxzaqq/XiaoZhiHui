package cc.i9mc.stocks.service.impl;

import cc.i9mc.stocks.config.DzhConfig;
import cc.i9mc.stocks.datasource.entities.SpringLog;
import cc.i9mc.stocks.service.SpringLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by JinVan on 2021-04-06.
 */
@Service
public class SpringLogServiceImpl implements SpringLogService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DzhConfig dzhConfig;

    @Override
    public void create(String node, boolean success) {
        SpringLog springLog = new SpringLog();
        springLog.setTimeStamp(System.currentTimeMillis());
        springLog.setNode(dzhConfig.getNode());
        springLog.setSuccess(success);

        mongoTemplate.save(springLog);
    }
}
