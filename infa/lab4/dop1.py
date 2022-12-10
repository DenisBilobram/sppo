import json
import yaml

def dop1_main():
    with open('yaml.yaml', 'r') as yamlfile, open('json.json', 'w') as jsonfile:
        yaml_object = yaml.safe_load(yamlfile)
        json.dump(yaml_object, jsonfile)