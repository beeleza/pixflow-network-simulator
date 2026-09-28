package com.beelza.pixflow_network_simulator.adapter.in;

import com.beelza.pixflow_network_simulator.application.in.PixTransactionMessageCommand;
import com.beelza.pixflow_network_simulator.application.in.ProcessPixTransactionPort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PixTransferListenerAdapter {

    private static final String TOPIC_REQUEST = "pix-transaction";

    private final ProcessPixTransactionPort processPixTransactionPort;

    public PixTransferListenerAdapter(ProcessPixTransactionPort processPixTransactionPort) {
        this.processPixTransactionPort = processPixTransactionPort;
    }

    @KafkaListener(
            topics = TOPIC_REQUEST,
            groupId = "pixflow-network-simulator"
    )
    public void consumeAndPublish(PixTransactionMessageRequest request) {
        PixTransactionMessageCommand command = new PixTransactionMessageCommand(
                request.transactionId(),
                request.idempotencyKey(),
                request.sourceAccount(),
                request.destinationKey(),
                request.destinationKeyType(),
                request.amount()
        );

        processPixTransactionPort.process(command);
    }
}
