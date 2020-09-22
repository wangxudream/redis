import com.kataer.redis.common.util.CommonApplication;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


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
        System.err.println("------------>"+strRedisTemplate);
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

    @Data
    private static class User {
        private String name;
        private Integer age;
        private Double tel;
    }
}
