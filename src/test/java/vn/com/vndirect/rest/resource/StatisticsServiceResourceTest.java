package vn.com.vndirect.rest.resource;

import static org.junit.Assert.*;

import java.util.Random;

import javax.ws.rs.NotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.ZSetOperations;

import vn.com.vndirect.order.Order;

public class StatisticsServiceResourceTest {

    private ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-test/spring-bean.xml",
            "classpath:spring-test/spring-redis.xml");
    private StatisticsServiceResource resource;
    private StatisticsServiceResource inactiveResource;
    private ZSetOperations<String, String> zSetOps;
    private String redisOrderKeyName;
    private String redisAccKeyName;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        resource = context.getBean("statisticsServiceResource", StatisticsServiceResource.class);
        inactiveResource = context.getBean("inactiveStatisticsServiceResource", StatisticsServiceResource.class);
        zSetOps = resource.getTemplate().opsForZSet();
        redisOrderKeyName = resource.getRedisOrderKeyName();
        redisAccKeyName = resource.getRedisAccKeyName();
    }

    @After
    public void tearDown() {
        resource.getTemplate().delete(redisOrderKeyName);
        resource.getTemplate().delete(redisAccKeyName);
    }

    @Test
    public void testRedisConnection() {
        assertNotNull(resource);
        assertEquals("test-order", redisOrderKeyName);
        assertEquals("test-account", redisAccKeyName);
    }

    @Test
    public void testRegisterOrderShouldStoreOrderIdToTheFreshRedisZSet() {
        Order order = new Order("12345678", "10000", "VND", 12.5F, 100, "ATC");
        resource.registerOrder(order);

        assertEquals(new Long(1), zSetOps.size(redisOrderKeyName));
        assertTrue(zSetOps.range(redisOrderKeyName, 0, -1).contains(order.getId()));

        assertEquals(new Long(1), zSetOps.size(redisAccKeyName));
        assertTrue(zSetOps.range(redisAccKeyName, 0, -1).contains(order.getAccount()));
    }

    @Test
    public void testRegisterOrderShouldNotStoreOrderIdToASaturatedZSetIfLowValue() {
        prepareDataSet(10, 2000);

        Order order = new Order("12345678", "10000", "VND", 12.5F, 10, "ATC");
        resource.registerOrder(order);

        assertEquals(new Long(10), zSetOps.size(redisOrderKeyName));
        assertFalse(zSetOps.range(redisOrderKeyName, 0, -1).contains(order.getId()));
    }

    @Test
    public void testRegisterOrderShouldStoreOrderIdToASaturatedZSetIfHigh() {
        prepareDataSet(10, 124);

        Order order = new Order("12345678", "10000", "VND", 12.5F, 10, "ATC");
        resource.registerOrder(order);

        assertEquals(new Long(10), zSetOps.size(redisOrderKeyName));
        assertTrue(zSetOps.range(redisOrderKeyName, 0, -1).contains(order.getId()));
    }

    @Test
    public void testGetOrderStatisticsShouldThrow406IfInactive() {
        thrown.expect(NotFoundException.class);
        inactiveResource.getTopOrders();
    }

    @Test
    public void testGetAccStatisticsShouldThrow406IfInactive() {
        thrown.expect(NotFoundException.class);
        inactiveResource.getTopAccounts();
    }

    private void prepareDataSet(int size, double score) {
        Random random = new Random();
        int seed = random.nextInt(Integer.MAX_VALUE - size);
        for (int i = 0; i < size; i++) {
            zSetOps.add(redisOrderKeyName, String.format("%05d", i + seed), score + i);
        }
    }
}
