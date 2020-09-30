import com.kataer.redis.common.CommonApplication;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@SpringBootTest(classes = CommonApplication.class)
public class CommonApplicationTests {

    @Resource(name = "intRedisTemplate")
    private RedisTemplate<String, Integer> intRedisTemplate;

    @Resource(name = "strRedisTemplate")
    private RedisTemplate<String, String> strRedisTemplate;


    @Test
    public void testString() {
        intRedisTemplate.opsForValue().set("name", 25);
        Integer integer = intRedisTemplate.opsForValue().get("name");
        System.out.println(integer);
    }

    @Test
    public void testHash() {
        System.err.println("------------>" + strRedisTemplate);
        Map<String, Object> map = new HashMap<>();
        map.put("mame", "test");
        map.put("age", 30);
        map.put("tel", 55.5);
        strRedisTemplate.opsForHash().putAll("user", map);
        HashOperations<String, String, Object> hashOps = strRedisTemplate.opsForHash();
        Map<String, Object> res = hashOps.entries("user");
        User user = new User();
        user.setAge((Integer) res.get("age"));
        System.out.println(res);
    }


    @Test
    public void testList() {
        Long res = strRedisTemplate.opsForList().leftPush("List", "1");
        System.out.println(res);
        String str = strRedisTemplate.opsForList().index("List", 0);
        System.out.println(str);
    }


    @Test
    public void testSet() {
        strRedisTemplate.opsForSet().add("set", "1", "2", "3", "4", "5");
        Boolean b = strRedisTemplate.opsForSet().isMember("set", "9");
        System.out.println("9 " + b);
        String str = strRedisTemplate.opsForSet().pop("set");
        System.out.println("pop " + str);
        Set<String> members = strRedisTemplate.opsForSet().members("set");
        System.out.println(members);
    }


    @Test
    public void testZSet() {
        strRedisTemplate.opsForZSet().add("zset", "1", 0);
        strRedisTemplate.opsForZSet().add("zset", "2", 1);
        strRedisTemplate.opsForZSet().add("zset", "3", 2);
        Set<String> members = strRedisTemplate.opsForZSet().range("zset", 0, 3);
        System.out.println(members);
    }


    @Data
    private static class User {
        private String name;
        private Integer age;
        private Double tel;
    }
}
