package com.beelza.pixflow_network_simulator.application.out;

public record PixTransactionMessageResponse(
        String transactionId,
        String idempotencyKey,
        boolean accepted,
        String failureReason
) {}
