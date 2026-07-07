package com.innovatech.msvc_proyecto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovatech.msvc_proyecto.model.EstadoProyecto;
import com.innovatech.msvc_proyecto.model.Proyecto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class NotificacionService {

    private static final Logger logger = Logger.getLogger(NotificacionService.class.getName());

    private final LambdaClient lambdaClient;
    private final ObjectMapper objectMapper; // Para serializar JSON de forma segura

    @Value("${aws.lambda.notificacion-function-name}")
    private String functionName;

    @Value("${aws.lambda.notificacion-habilitada:true}")
    private boolean notificacionHabilitada;

    // Inyectamos el LambdaClient y el ObjectMapper administrados por Spring
    public NotificacionService(LambdaClient lambdaClient, ObjectMapper objectMapper) {
        this.lambdaClient = lambdaClient;
        this.objectMapper = objectMapper;
    }

    public void notificarCambioDeEstado(Proyecto proyecto, EstadoProyecto estadoAnterior) {
        if (!notificacionHabilitada) {
            logger.info("Notificación de cambio de estado deshabilitada, se omite la invocación a Lambda");
            return;
        }

        try {
            // Construimos el payload usando un Map estructurado
            Map<String, Object> payloadMap = Map.of(
                    "proyecto", proyecto.getNombre(),
                    "estado", proyecto.getEstado(),
                    "estadoAnterior", estadoAnterior,
                    "responsable", proyecto.getResponsable()
            );

            // Jackson se encarga de escapar cualquier carácter especial automáticamente
            String payload = objectMapper.writeValueAsString(payloadMap);

            InvokeRequest request = InvokeRequest.builder()
                    .functionName(functionName)
                    .invocationType(InvocationType.EVENT)
                    .payload(SdkBytes.fromString(payload, StandardCharsets.UTF_8))
                    .build();

            InvokeResponse response = lambdaClient.invoke(request);

            logger.info("Notificación enviada a Lambda '" + functionName + "' para el proyecto '"
                    + proyecto.getNombre() + "' (statusCode=" + response.statusCode() + ")");

        } catch (Exception e) {
            logger.log(Level.WARNING, "No se pudo notificar el cambio de estado a Lambda", e);
        }
    }
}