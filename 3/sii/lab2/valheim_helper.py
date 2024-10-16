from pyswip import Prolog
from utils import biome_dict, creature_dict, resource_dict, structure_dict

prolog = Prolog()
prolog.consult("valheim_knowledge_base.pl")

def execute_query(params):
    query_type = params['query_type']
    if query_type == 'resources_in_biome':
        biome = params['biome']
        prolog_query = f"resources_in_biome({biome}, Resource)"
        results = list(prolog.query(prolog_query))
        if results:
            resources = set(result['Resource'] for result in results)
            resources_names = [resource_dict.get(res, res) for res in resources]
            return f"В биоме {biome_dict.get(biome, biome)} доступны следующие ресурсы: {', '.join(resources_names)}."
        else:
            return f"В биоме {biome_dict.get(biome, biome)} нет доступных ресурсов или биом неизвестен."
    elif query_type == 'creatures_in_biome':
        biome = params['biome']
        prolog_query = f"creatures_in_biome({biome}, Creature)"
        results = list(prolog.query(prolog_query))
        if results:
            creatures = set(result['Creature'] for result in results)
            creatures_names = [creature_dict.get(crea, crea) for crea in creatures]
            return f"В биоме {biome_dict.get(biome, biome)} обитают следующие существа: {', '.join(creatures_names)}."
        else:
            return f"В биоме {biome_dict.get(biome, biome)} нет известных существ или биом неизвестен."
    elif query_type == 'materials_needed_for_structure':
        structure = params['structure']
        prolog_query = f"materials_needed_for_structure({structure}, Material)"
        results = list(prolog.query(prolog_query))
        if results:
            materials = set(result['Material'] for result in results)
            materials_names = [resource_dict.get(mat, mat) for mat in materials]
            return f"Для постройки {structure_dict.get(structure, structure)} нужны следующие материалы: {', '.join(materials_names)}."
        else:
            return f"Постройка {structure_dict.get(structure, structure)} неизвестна или нет информации о необходимых материалах."
    elif query_type == 'creatures_unique_to_biome':
        biome = params['biome']
        prolog_query = f"creatures_unique_to_biome(Creature, {biome})"
        results = list(prolog.query(prolog_query))
        if results:
            creatures = set(result['Creature'] for result in results)
            creatures_names = [creature_dict.get(crea, crea) for crea in creatures]
            return f"Существа, уникальные для биома {biome_dict.get(biome, biome)}: {', '.join(creatures_names)}."
        else:
            return f"В биоме {biome_dict.get(biome, biome)} нет уникальных существ или биом неизвестен."
    elif query_type == 'common_biomes_for_creatures':
        creature1 = params['creature1']
        creature2 = params['creature2']
        prolog_query = f"common_biomes_for_creatures({creature1}, {creature2}, Biome)"
        results = list(prolog.query(prolog_query))
        if results:
            biomes = set(result['Biome'] for result in results)
            biomes_names = [biome_dict.get(bio, bio) for bio in biomes]
            return f"Общие биомы для {creature1} и {creature2}: {', '.join(biomes_names)}."
        else:
            return f"Нет общих биомов для {creature1} и {creature2}."
    elif query_type == 'resources_in_creature_biome':
        creature = params['creature']
        prolog_query = f"resources_in_creature_biome({creature}, Resource)"
        results = list(prolog.query(prolog_query))
        if results:
            resources = set(result['Resource'] for result in results)
            resources_names = [resource_dict.get(res, res) for res in resources]
            return f"Ресурсы в биомах с существом {creature_dict.get(creature, creature)}: {', '.join(resources_names)}."
        else:
            return f"Нет ресурсов в биомах с существом {creature_dict.get(creature, creature)}."
    elif query_type == 'biomes_with_creature_and_resource':
        creature = params['creature']
        resource = params['resource']
        prolog_query = f"biomes_with_creature_and_resource({creature}, {resource}, Biome)"
        results = list(prolog.query(prolog_query))
        if results:
            biomes = set(result['Biome'] for result in results)
            biomes_names = [biome_dict.get(bio, bio) for bio in biomes]
            return f"Биомы с существом {creature_dict.get(creature, creature)} и ресурсом {resource_dict.get(resource, resource)}: {', '.join(biomes_names)}."
        else:
            return f"Нет биомов, где вместе встречаются {creature_dict.get(creature, creature)} и {resource_dict.get(resource, resource)}."
    else:
        return "Извините, я не понимаю ваш запрос. Пожалуйста, попробуйте сформулировать его иначе."

