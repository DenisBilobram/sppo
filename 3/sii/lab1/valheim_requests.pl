% Поиск всех существ в биоме "черный лес":
creatures_in_biome(cherny_les, Creature).

% Поиск материалов для постройки "корабль":
materials_needed_for_structure(korabl, Material).

% Поиск существ, обитающих только в биоме "пустошь":
creatures_unique_to_biome(Creature, pustosh).

% Поиск общих биомов для "драугр" и "скелет":
common_biomes_for_creatures(draugr, skeleton, Biome).

% Поиск ресурсов в биомах с существом "тролль":
resources_in_creature_biome(troll, Resource).

% Поиск биомов с "волком" и ресурсом "серебро":
biomes_with_creature_and_resource(wolf, serebro, Biome).

% Поиск существ в биомах с ресурсом "черный металл":
resource_biome(chernyj_metall, Biome),
creature_biome(Creature, Biome).
