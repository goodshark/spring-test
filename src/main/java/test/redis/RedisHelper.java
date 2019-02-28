package test.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public class RedisHelper {

    @Autowired
    private RedisTemplate<String, RedisData> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public RedisTemplate<String, RedisData> genRedisTemplate(@Value("${spring.redis.host}") String host,
                                                             @Value("${spring.redis.port}") int port) {

        RedisTemplate<String, RedisData> redisTemplate = new RedisTemplate<String, RedisData>();

        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(host, port);
        RedisConnectionFactory cf = new JedisConnectionFactory(conf);
        redisTemplate.setConnectionFactory(cf);

        redisTemplate.setKeySerializer(new StringRedisSerializer());

        /*Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);*/

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(RedisData.class));

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate genStrRedisTemp(@Value("${spring.redis.host}") String host,
                                               @Value("${spring.redis.port}") int port) {

        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(host, port);
        RedisConnectionFactory cf = new JedisConnectionFactory(conf);
        StringRedisTemplate redisTemplate = new StringRedisTemplate(cf);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    public void setObjectData() {
        RedisData data = new RedisData(123, "kerj-100", "story", "tom");
        redisTemplate.opsForValue().set("test", data);
    }

    public RedisData getObjectData() {
        RedisData res = redisTemplate.opsForValue().get("test");
        return res;
    }

    public void setJson() throws JsonProcessingException {
        RedisData data = new RedisData(123, "kerj-100", "story", "tom");
        ObjectMapper om = new ObjectMapper();
        String res = om.writeValueAsString(data);
        stringRedisTemplate.opsForValue().set("test2", res);
    }

    public RedisData getJson() throws IOException {
        String res = stringRedisTemplate.opsForValue().get("test2");
        ObjectMapper om = new ObjectMapper();
        RedisData redisData = om.readValue(res, RedisData.class);
        return redisData;
    }
}
