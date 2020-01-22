package com.mokhovav.meeting_room_reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {
    @Bean
    public static Logger getLogger() {
        return LoggerFactory.getLogger(LoggingConfig.class);
    }
}
