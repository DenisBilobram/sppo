import re

biome_dict = {
    'luga': 'луга',
    'cherny_les': 'черный_лес',
    'boloto': 'болото',
    'gory': 'горы',
    'pustosh': 'пустошь'
}

creature_dict = {
    'greydwarf': 'грейдворф',
    'draugr': 'драугр',
    'troll': 'тролль',
    'dragon': 'дракон',
    'wolf': 'волк',
    'skeleton': 'скелет',
    'fuling': 'фулинг'
}

resource_dict = {
    'drevesina': 'древесина',
    'kamen': 'камень',
    'zhelezno': 'железо',
    'serebro': 'серебро',
    'med': 'медь',
    'olovo': 'олово',
    'chernyj_metall': 'черный_металл',
    'smola': 'смола',
    'yadro_surtlinga': 'ядро_сёртлинга'
}

structure_dict = {
    'dom': 'дом',
    'korabl': 'корабль',
    'kuznitsa': 'кузница',
    'portal': 'портал'
}

russian_to_biome = {v: k for k, v in biome_dict.items()}
russian_to_creature = {v: k for k, v in creature_dict.items()}
russian_to_resource = {v: k for k, v in resource_dict.items()}
russian_to_structure = {v: k for k, v in structure_dict.items()}

def validate_input(user_input):
   
    if not user_input.strip():
        return False
    return True


def parse_input(user_input):
    # Приводим строку к нижнему регистру
    user_input = user_input.lower()
    
    # Инициализируем словарь с параметрами
    params = {
        'biome': None,
        'creature': None,
        'resource': None,
        'structure': None,
        'creature1': None,
        'creature2': None,
        'query_type': None
    }
    
    # Определяем тип запроса
    if 'какие ресурсы доступны в биоме' in user_input:
        params['query_type'] = 'resources_in_biome'
        match = re.search(r'биоме\s+([\wА-Яа-я_]+)', user_input)
        if match:
            biome_russian = match.group(1)
            biome = russian_to_biome.get(biome_russian, biome_russian)
            params['biome'] = biome
    elif 'какие существа обитают в биоме' in user_input:
        params['query_type'] = 'creatures_in_biome'
        match = re.search(r'биоме\s+([\wА-Яа-я_]+)', user_input)
        if match:
            biome_russian = match.group(1)
            biome = russian_to_biome.get(biome_russian, biome_russian)
            params['biome'] = biome
    elif 'какие материалы нужны для постройки' in user_input:
        params['query_type'] = 'materials_needed_for_structure'
        match = re.search(r'постройки\s+([\wА-Яа-я_]+)', user_input)
        if match:
            structure_russian = match.group(1)
            structure = russian_to_structure.get(structure_russian, structure_russian)
            params['structure'] = structure
    elif 'какие существа уникальны для биома' in user_input:
        params['query_type'] = 'creatures_unique_to_biome'
        match = re.search(r'биома\s+([\wА-Яа-я_]+)', user_input)
        if match:
            biome_russian = match.group(1)
            biome = russian_to_biome.get(biome_russian, biome_russian)
            params['biome'] = biome
    elif 'биомы общие для существ' in user_input:
        params['query_type'] = 'common_biomes_for_creatures'
        match = re.search(r'существ\s+([\wА-Яа-я_]+)\s+и\s+([\wА-Яа-я_]+)', user_input)
        if match:
            creature1_russian = match.group(1)
            creature2_russian = match.group(2)
            params['creature1'] = russian_to_creature.get(creature1_russian, creature1_russian)
            params['creature2'] = russian_to_creature.get(creature2_russian, creature2_russian)

    elif 'ресурсы в биомах с существом' in user_input:
        params['query_type'] = 'resources_in_creature_biome'
        match = re.search(r'существом\s+([\wА-Яа-я_]+)', user_input)
        if match:
            creature_russian = match.group(1)
            creature = russian_to_creature.get(creature_russian, creature_russian)
            params['creature'] = creature

    elif 'биомы с существом' in user_input and 'и ресурсом' in user_input:
        params['query_type'] = 'biomes_with_creature_and_resource'
        creature_match = re.search(r'существом\s+([\wА-Яа-я_]+)', user_input)
        resource_match = re.search(r'ресурсом\s+([\wА-Яа-я_]+)', user_input)
        if creature_match and resource_match:
            creature_russian = creature_match.group(1)
            resource_russian = resource_match.group(1)
            params['creature'] = russian_to_creature.get(creature_russian, creature_russian)
            params['resource'] = russian_to_resource.get(resource_russian, resource_russian)
    else:
        params['query_type'] = 'unknown'
    
    return params

