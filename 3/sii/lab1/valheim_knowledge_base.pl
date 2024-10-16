% Биомы
biome(luga).          % Луга
biome(cherny_les).    % Черный лес
biome(boloto).        % Болото
biome(gory).          % Горы
biome(pustosh).       % Пустошь

% Существа
creature(greydwarf).
creature(draugr).
creature(troll).
creature(dragon).
creature(wolf).
creature(skeleton).
creature(fuling).

% Ресурсы и материалы
resource(drevesina).
resource(kamen).
resource(zhelezno).
resource(serebro).
resource(med).
resource(olovo).
resource(chernyj_metall).
resource(smola).
resource(yadro_surtlinga).

% Оружие
weapon(mech).
weapon(sekira).
weapon(luk).
weapon(shield).
weapon(kopje).

% Постройки
structure(dom).
structure(korabl).
structure(kuznitsa).
structure(portal).

% Существа и их биомы
creature_biome(greydwarf, cherny_les).
creature_biome(greydwarf, luga).
creature_biome(draugr, boloto).
creature_biome(draugr, cherny_les).
creature_biome(troll, cherny_les).
creature_biome(troll, luga).
creature_biome(wolf, gory).
creature_biome(dragon, gory).
creature_biome(skeleton, cherny_les).
creature_biome(skeleton, boloto).
creature_biome(fuling, pustosh).

% Ресурсы и их местонахождение
resource_biome(drevesina, luga).
resource_biome(drevesina, cherny_les).
resource_biome(kamen, luga).
resource_biome(kamen, cherny_les).
resource_biome(kamen, gory).
resource_biome(kamen, pustosh).
resource_biome(med, cherny_les).
resource_biome(olovo, cherny_les).
resource_biome(zhelezno, boloto).
resource_biome(serebro, gory).
resource_biome(chernyj_metall, pustosh).
resource_biome(smola, cherny_les).
resource_biome(smola, luga).
resource_biome(yadro_surtlinga, luga).

% Постройки и необходимые материалы
structure_material(dom, drevesina).
structure_material(dom, kamen).
structure_material(korabl, drevesina).
structure_material(korabl, zhelezno).
structure_material(korabl, smola).
structure_material(kuznitsa, kamen).
structure_material(kuznitsa, zhelezno).
structure_material(portal, drevesina).
structure_material(portal, yadro_surtlinga).

% Правила

% Правило для поиска ресурсов, доступных в заданном биоме
resources_in_biome(Biome, Resource) :-
    resource_biome(Resource, Biome).

% Правило для поиска существ, обитающих в заданном биоме
creatures_in_biome(Biome, Creature) :-
    creature_biome(Creature, Biome).

% Правило для определения материалов, необходимых для постройки
materials_needed_for_structure(Structure, Material) :-
    structure_material(Structure, Material).

% Поиск существ, которые обитают только в одном биоме
creatures_unique_to_biome(Creature, Biome) :-
    creature_biome(Creature, Biome),
    \+ (creature_biome(Creature, OtherBiome), OtherBiome \= Biome).

% Определение биомов, общих для двух заданных существ
common_biomes_for_creatures(Creature1, Creature2, Biome) :-
    creature_biome(Creature1, Biome),
    creature_biome(Creature2, Biome).


% Определение ресурсов, доступных в биомах, где обитает определенное существо
resources_in_creature_biome(Creature, Resource) :-
    creature_biome(Creature, Biome),
    resource_biome(Resource, Biome).

% Определение биомов, где можно найти как определенное существо, так и ресурс
biomes_with_creature_and_resource(Creature, Resource, Biome) :-
    creature_biome(Creature, Biome),
    resource_biome(Resource, Biome).