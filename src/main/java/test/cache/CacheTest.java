package test.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CacheTest {
    private int genNum(int id) {
        Random rand = new Random(id);
        return rand.nextInt(1000);
    }

    @Cacheable("hehe")
    public String getFullName(int id) {
        System.out.println("call getFullName ...");
        int num = genNum(id);
        String fullName = id + "-fullname-" + num;
        return fullName;
    }

    @CachePut(value = "hehe", key = "#id")
    public String setFullName(int id, String name) {
        return id + "-fullname-" + name;
    }

    @CacheEvict("hehe")
    public void removeName(int id) {
    }
}
