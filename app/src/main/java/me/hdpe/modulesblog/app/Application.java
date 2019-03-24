package me.hdpe.modulesblog.app;

import me.hdpe.modulesblog.diary.service.DiaryConfig;
import me.hdpe.modulesblog.web.WebConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .contextClass(AnnotationConfigServletWebServerApplicationContext.class)
                .sources(Application.class).web(WebApplicationType.SERVLET)
                .child(DiaryConfig.class).web(WebApplicationType.NONE)
                .sibling(WebConfig.class).web(WebApplicationType.NONE)
                .listeners(new EventListener())
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
