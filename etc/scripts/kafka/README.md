# Scripts de Kafka

## Tabla de contenidos

- [Requisitos](#requisitos)
- [Dependencias](#dependencias)
    - [Entorno virtual para las dependencias](#entorno-virtual-para-las-dependencias)
- [Scripts](#scripts)
    - [Productor de Kafka: `kafka_producer.py`](#productor-de-kafka-kafka_producerpy)
    - [Consumidor de Kafka: `kafka_consumer.py`](#consumidor-de-kafka-kafka_consumerpy)
- [Notas](#notas)

## Requisitos

- Python 3.8

## Dependencias

Instalables a través del archivo `requirements.txt`. Se pueden instalar las dependencias de manera global con el
siguiente comando:

```shell
pip install -r requirements.txt
```

### Entorno virtual para las dependencias

Es recomendable instalar las dependencias en un entorno virtual para evitar conflictos entre las versiones. El entorno
se crea con el siguiente comando:

```shell
python -m venv /dir/to/.venv 
```

Creado el entorno virtual, se debe activar:

```shell
/dir/to/.venv/Scripts/activate
```

Luego se pueden instalar las dependencias normalmente:

```shell
pip install -r requirements.txt
```

## Scripts

### Productor de Kafka: `kafka_producer.py`

### Consumidor de Kafka: `kafka_consumer.py`

## Notas
