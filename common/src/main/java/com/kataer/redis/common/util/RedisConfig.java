package com.kataer.redis.common.util;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.database}")
    private Integer database;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * JedisPoolConfig 连接池
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //最大空闲数
        jedisPoolConfig.setMaxIdle(300);
        //连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(1000);
        //最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(1000);
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(300000);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(10);
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        //是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(true);
        //在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }

    /**
     * 配置工厂
     *
     * @param jedisPoolConfig
     * @return
     */
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        //连接池
//        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
//        //IP地址
//        jedisConnectionFactory.setHostName(host);
//        //端口号
//        jedisConnectionFactory.setPort(port);
//        //数据库
//        jedisConnectionFactory.setDatabase(database);
//        //如果Redis设置有密码
//        if (StringUtil.isNotBlank(password)) {
//            jedisConnectionFactory.setPassword(password);
//        }
//        //客户端超时时间单位是毫秒
//        jedisConnectionFactory.setTimeout(5000);
//        return jedisConnectionFactory;
//    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        //2.X版本可以使用RedisStandaloneConfiguration、RedisSentinelConfiguration、RedisClusterConfiguration三种方式配置连接信息。
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        //修改我们的连接池配置
        jpcf.poolConfig(jedisPoolConfig);
        jpcf.poolConfig(jedisPoolConfig);
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcf.build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }


    @Bean
//    @Bean(name = "objRedisTemplate")
    public RedisTemplate objRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用 GenericFastJsonRedisSerializer 替换默认序列化
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
        redisTemplate.setHashValueSerializer(genericFastJsonRedisSerializer);
        System.err.println("objRedisTemplate------------>" + redisTemplate.toString());
        return redisTemplate;
    }

    @Bean
//    @Bean(name = "strRedisTemplate")
    public RedisTemplate strRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用 GenericFastJsonRedisSerializer 替换默认序列化
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(String.class));
        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(String.class));
        redisTemplate.setHashValueSerializer(genericFastJsonRedisSerializer);
        System.err.println("strRedisTemplate------------>" + redisTemplate.toString());
        return redisTemplate;
    }

    @Bean
//    @Bean(name = "intRedisTemplate")
    public RedisTemplate<String, Integer> intRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用 GenericFastJsonRedisSerializer 替换默认序列化
        GenericFastJsonRedisSerializer genericFastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(Integer.class));
        redisTemplate.setValueSerializer(genericFastJsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Integer.class));
        redisTemplate.setHashValueSerializer(genericFastJsonRedisSerializer);
        System.err.println("intRedisTemplate------------>" + redisTemplate.toString());
        return redisTemplate;
    }
}
