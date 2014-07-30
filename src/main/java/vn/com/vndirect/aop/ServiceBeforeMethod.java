package vn.com.vndirect.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

public class ServiceBeforeMethod implements MethodBeforeAdvice {

    private static final Logger logger = Logger.getLogger(ServiceBeforeMethod.class);

    @Override
    public void before(Method method, Object[] args, Object object) throws Throwable {
        logger.info("Hijack");
//        if (object instanceof OrderService) {
//            OrderService service = (OrderService) object;
//            if (! service.isActive()) {
//                throw(new NotFoundException());
//            }
//        }
    }

}
