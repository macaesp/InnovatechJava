import json
import boto3
import logging

logger = logging.getLogger()
logger.setLevel(logging.INFO)

sqs = boto3.client('sqs', region_name='us-east-1')
QUEUE_URL = 'https://sqs.us-east-1.amazonaws.com/482544634513/innovatech-proyectos-queue'

def lambda_handler(event, context):
    proyecto = event.get('proyecto', 'Sin nombre')
    estado = event.get('estado', 'Sin estado')
    responsable = event.get('responsable', 'Sin responsable')

    mensaje = {
        'proyecto': proyecto,
        'estado': estado,
        'responsable': responsable,
        'notificacion': f"El proyecto '{proyecto}' cambió a estado '{estado}'"
    }

    logger.info(f"Enviando mensaje a SQS: {mensaje}")

    response = sqs.send_message(
        QueueUrl=QUEUE_URL,
        MessageBody=json.dumps(mensaje)
    )

    logger.info(f"Mensaje enviado. MessageId: {response['MessageId']}")

    return {
        'statusCode': 200,
        'body': json.dumps({
            'message': 'Notificación enviada a la cola',
            'messageId': response['MessageId']
        })
    }