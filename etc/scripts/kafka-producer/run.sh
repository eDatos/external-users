#!/bin/bash

poetry run python kafka-producer.py \
--topic OPERATION_TOPIC \
--schema-file avro/test.avsc \
--record-value values/dataset_version_1.json
