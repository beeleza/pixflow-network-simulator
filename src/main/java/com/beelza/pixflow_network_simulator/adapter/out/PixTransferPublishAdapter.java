package com.beelza.pixflow_network_simulator.adapter.out;

import com.beelza.pixflow_network_simulator.application.out.PixTransactionMessageResponse;
import com.beelza.pixflow_network_simulator.application.out.PublishPixTransactionPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PixTransferPublishAdapter implements PublishPixTransactionPort {

    private static final String TOPIC_RESPONSE = "pix-transaction-response";

    private final KafkaTemplate<String, PixTransactionMessageResponse> kafkaTemplate;

    public PixTransferPublishAdapter(KafkaTemplate<String, PixTransactionMessageResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(PixTransactionMessageResponse pixTransactionMessageResponse) {
        kafkaTemplate.send(TOPIC_RESPONSE, pixTransactionMessageResponse.transactionId(), pixTransactionMessageResponse).join();
    }
}
