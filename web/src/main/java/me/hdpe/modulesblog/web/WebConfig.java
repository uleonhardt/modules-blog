package me.hdpe.modulesblog.web;

import me.hdpe.modulesblog.spring.ExportedConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import({
		ExportedConfiguration.class
})
public class WebConfig {
	
}
