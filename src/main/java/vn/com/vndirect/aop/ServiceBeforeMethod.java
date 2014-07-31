package vn.com.vndirect.aop;

import java.lang.reflect.Method;

import javax.ws.rs.NotFoundException;

import org.springframework.aop.MethodBeforeAdvice;

import vn.com.vndirect.rest.resource.ServiceResource;

public class ServiceBeforeMethod implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object object) throws Throwable {
        if (object instanceof ServiceResource) {
            ServiceResource serviceResource = (ServiceResource) object;
            if (! serviceResource.getAppState().isActive()) {
                throw(new NotFoundException());
            }
        }
    }

}
