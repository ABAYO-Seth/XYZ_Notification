package rw.gov.irembo.notification.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfiguration {

    @Bean(name = "normal")
    public RateLimiter normalLimiter(){
       return RateLimiter.create(2, 15, TimeUnit.SECONDS);
    }

    @Bean(name = "premium")
    public RateLimiter premiumLimiter(){
        return RateLimiter.create(2,15,TimeUnit.SECONDS);
    }

}
