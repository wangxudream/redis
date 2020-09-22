import com.kataer.redis.demo.DemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


//
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testConect() {
        redisTemplate.opsForValue().set("name", "test");
        System.out.println("test");
    }

}
