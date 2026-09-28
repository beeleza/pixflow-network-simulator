package com.beelza.pixflow_network_simulator.adapter.in;

import java.math.BigDecimal;

public record PixTransactionMessageRequest(
        String transactionId,
        String idempotencyKey,
        String sourceAccount,
        String destinationKey,
        String destinationKeyType,
        BigDecimal amount
) {
}
