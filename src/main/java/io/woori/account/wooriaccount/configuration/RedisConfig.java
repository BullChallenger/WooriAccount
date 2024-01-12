package io.woori.account.wooriaccount.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

/*
* jwt를 저장할 때 사용할 레디스를 위한 설정 클래스입니다.
* */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration configuration= new RedisStandaloneConfiguration();

        configuration.setHostName(host);
        configuration.setPort(port);

        return new LettuceConnectionFactory(configuration);
    }



    /*
    * 레디스에 데이터를 저장할 때 key, value를 어떻게 직렬화해서 데이터를 넣을지 설정해주는 작업니다.
    * */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>( );

        redisTemplate.setConnectionFactory(redisConnectionFactory());

        //key : value 값으로 시리얼라이저
        redisTemplate.setKeySerializer(new StringRedisSerializer()); //key값은 String
        redisTemplate.setValueSerializer(new StringRedisSerializer()); //json serializer, Value는 Object를 Json 형태로 직렬화하여 저장하도록 설정


        // hash 사용할 경우에 시리얼라이저
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());


        return redisTemplate;
    }
}
