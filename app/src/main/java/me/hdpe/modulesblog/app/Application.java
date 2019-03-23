package me.hdpe.modulesblog.app;

import me.hdpe.modulesblog.diary.service.DiaryConfig;
import me.hdpe.modulesblog.spring.ExportedConfiguration;
import me.hdpe.modulesblog.web.DiaryController;
import me.hdpe.modulesblog.web.WebConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Application.class).contextClass(AnnotationConfigEmbeddedWebApplicationContext.class).web(true)
                .child(DiaryConfig.class).web(false)
                .sibling(WebConfig.class).web(false).listeners(new EventListener())
                .run(args);
    }


    // TODO - Avoid duplicate registration of /ping and /diary. They are already registered the first time round.
    static class EventListener implements ApplicationListener<ApplicationEvent> {
        @Override
        public void onApplicationEvent(ApplicationEvent applicationEvent) {
            if (applicationEvent instanceof ApplicationReadyEvent) {
                ConfigurableApplicationContext context = ((ApplicationReadyEvent) applicationEvent).getApplicationContext();
                ApplicationContext parent = context.getParent();
                if (parent != null) {
                    Map<String, RequestMappingHandlerMapping> beansOfType = parent.getBeansOfType(RequestMappingHandlerMapping.class);
                    beansOfType
                            .forEach((k, v) -> v.afterPropertiesSet());
                }
            }
        }
    }

}
