package vn.com.vndirect.aop;

import javax.ws.rs.NotFoundException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import vn.com.vndirect.rest.resource.ServiceResource;

@Aspect
public class ServiceResourceAspect {
    
    @Before("execution(* vn.com.vndirect.rest.resource.*.*(..) )")
    public void abortIfInactive(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        if (target instanceof ServiceResource) {
            ServiceResource resource = (ServiceResource) target;
            if (! resource.getAppState().isActive()) {
                throw new NotFoundException();
            }
        }
    }

}
