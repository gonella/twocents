package com.twocents.security.license;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LicenseAspect {
	
	private static final Logger log = Logger.getLogger(LicenseAspect.class);

	@Around("execution(* com.twocents.service..*.*(..))")
    public Object validateLicense(ProceedingJoinPoint pjp) throws Throwable {
		//log.info("Validating license for method: " + pjp.getTarget().getClass().getSimpleName()	+ "."  + pjp.getSignature().getName());
		//LicenseValidate.validateLicense();
        return pjp.proceed();
    }

	
}
