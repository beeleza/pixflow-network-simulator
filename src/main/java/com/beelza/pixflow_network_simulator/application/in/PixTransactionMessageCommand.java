package com.beelza.pixflow_network_simulator.application.in;

import java.math.BigDecimal;

public record PixTransactionMessageCommand(
        String transactionId,
        String idempotencyKey,
        String sourceAccount,
        String destinationKey,
        String destinationKeyType,
        BigDecimal amount
) {
}
