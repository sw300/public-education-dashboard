package com.uengine.education;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    @Autowired
    DashboardRepository dashboardRepository;

    @StreamListener(Streams.INPUT)
    @JsonDeserialize(as = TotalEnrollmentUpdated.class)
    public void handleEnrolled(@Payload TotalEnrollmentUpdated totalEnrollmentUpdated,  @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY)String key) {

        Long customerId = Long.parseLong(key);

        Dashboard dashboard = dashboardRepository.findById(customerId).orElse(new Dashboard());

        dashboard.setUserId(customerId);
        dashboard.setTotalPrice(totalEnrollmentUpdated.totalPrice);
        dashboard.setTotalTime(totalEnrollmentUpdated.totalTime);

        dashboardRepository.save(dashboard);

    }

}
