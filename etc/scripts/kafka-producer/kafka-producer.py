import json
import os
import uuid
from argparse import ArgumentParser

from confluent_kafka import avro
from confluent_kafka.avro import AvroProducer
from rich.console import Console

script_path = os.path.dirname(__file__)
console = Console(log_time=False)
error_console = Console(log_time=False, stderr=True, style="bold red")


def load_avro_schema_from_file(schema_filepath: str) -> [str, str]:
    key_schema_string = """
    {"type": "string"}
    """

    with console.status(f"Loading Avro schema: {schema_filepath}"):
        key_schema = avro.loads(key_schema_string)
        value_schema = avro.load(schema_filepath)

    console.log(f"Loaded avro schema at {schema_filepath}")
    return key_schema, value_schema


def send_record(args):
    key_schema, value_schema = load_avro_schema_from_file(args.schema_file)

    producer_config = {
        "bootstrap.servers": args.bootstrap_servers,
        "schema.registry.url": args.schema_registry
    }

    producer = AvroProducer(producer_config, default_key_schema=key_schema, default_value_schema=value_schema)

    key = args.record_key if args.record_key else str(uuid.uuid4())
    value = args.record_value

    with console.status("[yellow]Producing record value...[/]"):
        try:
            producer.produce(topic=args.topic, key=key, value=value)
        except Exception as e:
            error_console.log(
                f"Exception while producing record value to topic '{args.topic}':\n» {e}")
        else:
            console.log(
                f"[bold green]Successfully producing record value to topic '{args.topic}'[/bold green]")

    producer.flush()


def file_exists(string):
    if os.path.exists(string):
        return string
    else:
        error_console.log(f"'{string}' file wasn't found")
        exit(1)


def json_filepath_or_string(string):
    with console.status("Processing record value..."):
        if os.path.isfile(string):
            with open(string) as file:
                value = json.load(file)
        else:
            value = json.loads(string)
        console.log(f"Loaded value: {json.dumps(value)}")
        return value


def parse_command_line_args():
    arg_parser = ArgumentParser()

    arg_parser.add_argument("--topic", required=True, help="Topic name")
    arg_parser.add_argument("--bootstrap-servers", required=False, default="localhost:9092",
                            help="Bootstrap server address")
    arg_parser.add_argument("--schema-registry", required=False, default="http://localhost:8081",
                            help="Schema Registry url")
    arg_parser.add_argument("--schema-file", required=True, type=file_exists, help="File path of Avro schema to use")
    arg_parser.add_argument("--record-key", required=False, type=str,
                            help="Record key. If not provided, will be a random UUID")
    arg_parser.add_argument("--record-value", required=True, type=json_filepath_or_string,
                            help="Record value, can be path to a json file or a valid json string")

    return arg_parser.parse_args()


if __name__ == "__main__":
    send_record(parse_command_line_args())
