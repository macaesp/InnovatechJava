import json
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

def lambda_handler(event, context):
    proyecto = event.get('proyecto', 'Sin nombre')
    estado = event.get('estado', 'Sin estado')
    responsable = event.get('responsable', 'Sin responsable')

    mensaje = f"[Innovatech] Proyecto '{proyecto}' cambió a estado '{estado}'. Responsable: {responsable}"

    logger.info("=== NOTIFICACIÓN DE CAMBIO DE ESTADO ===")
    logger.info(f"Proyecto: {proyecto}")
    logger.info(f"Nuevo estado: {estado}")
    logger.info(f"Responsable: {responsable}")
    logger.info(mensaje)

    return {
        'statusCode': 200,
        'body': json.dumps({
            'message': 'Notificación registrada exitosamente',
            'detalle': mensaje
        })
    }
