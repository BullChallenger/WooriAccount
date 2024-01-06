package io.woori.account.wooriaccount.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

/*
* jwt를 저장할 때 사용할 레디스를 위한 설정 클래스입니다.
* */
@Configuration
public class RedisConfig {

    /*
    * 레디스에 데이터를 저장할 때 key, value를 어떻게 직렬화해서 데이터를 넣을지 설정해주는 작업니다.
    * */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>( );
        redisTemplate.setConnectionFactory(redisConnectionFactory);// factory로 redis와 spring 연결을 설정
        redisTemplate.setKeySerializer(new StringRedisSerializer()); //key값은 String
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); //json serializer, Value는 Object를 Json 형태로 직렬화하여 저장하도록 설정

        return redisTemplate;
    }
}
