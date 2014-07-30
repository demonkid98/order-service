package vn.com.vndirect.rest.resource;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;

import vn.com.vndirect.order.Order;

public class StatisticsServiceResource {

    private RedisTemplate<String, String> template;
    private String redisOrderKeyName;
    private String redisAccKeyName;
    private int maxKeep;

    public RedisTemplate<String, String> getTemplate() {
        return template;
    }

    public void setTemplate(RedisTemplate<String, String> template) {
        this.template = template;
    }

    public String getRedisOrderKeyName() {
        return redisOrderKeyName;
    }

    public void setRedisOrderKeyName(String redisOrderKeyName) {
        this.redisOrderKeyName = redisOrderKeyName;
    }

    public String getRedisAccKeyName() {
        return redisAccKeyName;
    }

    public void setRedisAccKeyName(String redisAccKeyName) {
        this.redisAccKeyName = redisAccKeyName;
    }

    public void setMaxKeep(int maxKeep) {
        this.maxKeep = maxKeep;
    }

    public void registerOrder(Order order) {
        addToRedisZSet(redisOrderKeyName, order.getId(), order.getValue());
        addToRedisZSet(redisAccKeyName, order.getAccount(), order.getValue());
    }

    public Set<String> getTopOrders() {
        return template.opsForZSet().reverseRange(redisOrderKeyName, 0, -1);
    }

    private void addToRedisZSet(String key, String name, double score) {
        template.opsForZSet().add(key, name, score);
        template.opsForZSet().removeRange(key, 0, - maxKeep - 1); // keep {maxKeep} items with top score
    }

}
