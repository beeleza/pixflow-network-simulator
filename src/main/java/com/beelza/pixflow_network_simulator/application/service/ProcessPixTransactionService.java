package com.beelza.pixflow_network_simulator.application.service;

import com.beelza.pixflow_network_simulator.application.in.PixTransactionMessageCommand;
import com.beelza.pixflow_network_simulator.application.in.ProcessPixTransactionPort;
import com.beelza.pixflow_network_simulator.application.out.PixTransactionMessageResponse;
import com.beelza.pixflow_network_simulator.application.out.PublishPixTransactionPort;
import org.springframework.stereotype.Service;

@Service
public class ProcessPixTransactionService implements ProcessPixTransactionPort {

    private final PublishPixTransactionPort publishPixTransactionPort;

    public ProcessPixTransactionService(PublishPixTransactionPort publishPixTransactionPort) {
        this.publishPixTransactionPort = publishPixTransactionPort;
    }

    @Override
    public void process(PixTransactionMessageCommand command) {

        String failureReason = null;

        if (command.amount() == null || command.amount().signum() <= 0) {
            failureReason = "INVALID_AMOUNT";
        } else if (command.destinationKey() == null
                || command.destinationKey().isBlank()) {
            failureReason = "INVALID_DESTINATION_KEY";
        } else if (command.sourceAccount() == null
                || command.sourceAccount().isBlank()) {
            failureReason = "INVALID_SOURCE_ACCOUNT";
        }

        boolean accepted = failureReason == null;

        var response = new PixTransactionMessageResponse(
                command.transactionId(),
                command.idempotencyKey(),
                accepted,
                failureReason
        );

        publishPixTransactionPort.publish(response);
    }
}
