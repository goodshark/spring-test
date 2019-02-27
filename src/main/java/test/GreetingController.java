package test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import test.dao.jdbc.DbHelper;
import test.dao.jdbc.UserData;
import test.exception.FooError;
import test.exception.OpException;
import test.exception.StubExc;
import test.objects.CustomPara;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/greeting2")
    public Greeting greeting2(String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, "good day "+name));
    }

    @RequestMapping("/test")
    public String jtest(@RequestBody Data data) {
        System.out.println("data: " + data);
        System.out.println("id: " + data.getId());
        return "foobar";
    }

    @RequestMapping(value = "/t2", method = RequestMethod.POST)
    public String paraTest2() {
        return "para test 2";
    }

    @RequestMapping(value = "/t3", method = RequestMethod.POST)
    public String paraTest3(Integer num) {
        return "para test 3, num: " + num;
    }

    @RequestMapping(value = "/t4", method = RequestMethod.POST)
    public String paraTest4(CustomPara para) {
        return "para test 4, para: " + para;
    }

    @RequestMapping(value = "/t5", method = RequestMethod.POST)
    public String paraTest5(@RequestParam Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (String k: map.keySet()) {
            sb.append(k).append(" ");
        }
        return "para test 5, para: " + sb.toString();
    }


    // exception
    @RequestMapping("/numerror")
    public String testException(int num) {
        if (num % 2 == 0)
            throw new FooError();
        else
            return "just stub";
    }

    @RequestMapping("/justexc")
    public String throwExc() {
        throw new StubExc();
    }

    @ExceptionHandler(StubExc.class)
    public String handleTestExc() {
        return "meet handle error";
    }

    // jdbc template
    @Autowired
    private DbHelper dbHelper;

    @RequestMapping(value = "/db/{op}", method = RequestMethod.POST)
    public void jdbcTest(@PathVariable String op, @RequestBody UserData user) {
        System.out.println("op type: " + op);
        System.out.println("id: " + user.getId() + ", name: " + user.getName() + ", email: " + user.getEmail());
        switch (op) {
            case "add":
                dbHelper.addData(user.getId(), user.getName(), user.getEmail());
                break;
            case "del":
                dbHelper.delData(user.getId());
                break;
            case "update":
                dbHelper.updateData(user.getId(), user.getEmail());
                break;
            case "query":
                dbHelper.queryData(user.getId());
                break;
            case "queryall":
                dbHelper.queryAll();
                break;
            default:
                throw new OpException();
        }
        System.out.println("jdbc op ok");
    }

    @ExceptionHandler(OpException.class)
    public String handleOpError() {
        return "op not exist";
    }



    // jpa
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @RequestMapping("/add") // Map ONLY GET Requests
    public String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @RequestMapping("/all")
    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    // redis
    /*@Bean
    public RedisTemplate<Object, Object> genRedisTemplate() {
        RedisTemplate redis = new RedisTemplate<>();
        redis.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redis.setValueSerializer(jackson2JsonRedisSerializer);
        return redis;
    }*/

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/redis/set")
    public String hehe(@RequestParam String k, @RequestParam String v) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        /*Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);*/
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.opsForValue().set("hehe", "good");
        return "good";
    }
}
