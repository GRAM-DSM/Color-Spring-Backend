package jhhong.gramo.color.global.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@Profile("!real")
public class EmbeddedRedisConfig {

    private static RedisServer redisServer;

    @Value("${spring.redis.port}")
    private int redisPort;

    @PostConstruct
    public void redisServer() {
        if (redisServer == null) {
            redisServer = new RedisServer(redisPort);
            redisServer.start();
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

}
