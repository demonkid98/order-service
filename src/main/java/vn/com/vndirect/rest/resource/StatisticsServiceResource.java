package vn.com.vndirect.rest.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import vn.com.vndirect.order.Order;

public class StatisticsServiceResource extends ServiceResource {

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

    public List<Map<String, String>> getTopOrders() {
        return convertToListMap(template.opsForZSet().reverseRangeWithScores(redisOrderKeyName, 0, -1));
    }

    public List<Map<String, String>> getTopAccounts() {
        return convertToListMap(template.opsForZSet().reverseRangeWithScores(redisAccKeyName, 0, -1));
    }

    private void addToRedisZSet(String key, String name, double score) {
        template.opsForZSet().add(key, name, score);
        template.opsForZSet().removeRange(key, 0, -maxKeep - 1); // keep {maxKeep} items with top score
    }

    private List<Map<String, String>> convertToListMap(Set<TypedTuple<String>> set) {
        List<Map<String, String>> rs = new ArrayList<>();
        for (TypedTuple<String> tuple : set) {
            Map<String, String> entry = new HashMap<>();
            entry.put("id", tuple.getValue());
            entry.put("value", String.format("%s", tuple.getScore()));
            rs.add(entry);
        }
        return rs;
    }

}
