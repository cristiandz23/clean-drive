package com.cleandriver.model.enums;

public enum AppointmentStatus {

    PENDING,       // Reservado pero aún no confirmado
    CONFIRMED,     // Confirmado por el lavadero
    IN_PROGRESS,   // El lavado está en curso
    COMPLETED,     // El lavado fue realizado
    CANCELED,      // Cancelado por el cliente o el lavadero
    NO_SHOW        // El cliente no se presentó
}
