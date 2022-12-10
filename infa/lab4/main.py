'''
Моудль main содержит функции для парсинга и конвертации yaml файла к формату json.

Описание алгоритма:
Алгортм конвертирует данные из yaml файла в Python dict, а затем из Python dict в json файл. 
Осуществляется парсинг yaml файла, каждая строчка записывается в формате [кол-во отступов, значение строки].
Затем каждая запись обрабатывается в цикле, первым делом определяется - запись является контейнером или значением.
Бывает два типа контейнеров: dict и list, при обработке значений проверяется тип последнего контейнера, которых
хранится в переменной last, ссылка на сам же контейнер хратися в переменной buff. Все контейнеры хранятся в
переменной buffers, при окончании заполнения контейнера зачениями, осуществляется переход в другой контейнер через
переменную buffers.

Костыли:
Алгорм заменяет символы ' и " на \&comma321654897321265468& и \&doublecomma&321654897321265468, а затем обратно.

'''

def val_key_to_types(st):
    '''
    Функция приводит данные типа строка к другим типам.
    Например: 123(str) -> 123(int).
    '''
    if st:
        if st.isnumeric():
            st = int(st)
        elif st == 'false':
            st = False
        elif st == 'Flase':
            st = False
        elif st == 'null':
            st = None
        else:
            if (st[0] == "'" and st[-1] == "'") or (st[0] == '"' and st[-1] == '"'):
                st = st[1:-1]
            st = st.replace("'", "\&comma321654897321265468&").replace('"', "\&doublecomma&321654897321265468")
    return st


def yaml_to_json(yaml_name, json_name):
    '''
    Функция предназначенная для парсинга yaml и конвертации в json.
    '''
    with open(yaml_name, 'r') as yamlfile:
        yamlstrs = yamlfile.readlines()

        pars_file = []
        for i in range(len(yamlstrs)):

            yamlstr = yamlstrs[i].strip().replace('\n', '')
            j = 0
            for ch in yamlstrs[i]:
                if ch == ' ':
                    j += 1
                    continue
                break
            if yamlstr[0] == '#':
                continue        
            pars_file.append([j, yamlstr])

        objs = {}
        last_i = False
        
        buff = objs
        buffers = []
        last = dict

        for i in range(len(pars_file)):
            if i == len(pars_file)-1:
                last_i = True

            if not last_i:
                diff = (pars_file[i][0] - pars_file[i+1][0]) // 2
            else:
                diff = 0

            vals = pars_file[i][1].split(': ')
            for j in range(len(pars_file[i][1])-2):
                if pars_file[i][1][j:j+2] == ': ':
                    vals = (pars_file[i][1][:j], pars_file[i][1][j+2:])
                    break
            
            key = value = False

            if len(vals) == 2:
                key, value = vals[0], vals[1]
            else:
                if vals[0][-1] == ':':
                    key = vals[0][:-1]
                else:
                    value = vals[0]

            value = val_key_to_types(value)
            key = val_key_to_types(key)

            if diff == 0:
                if last == list:
                    if value and key:
                        key = key[2:]
                        key = val_key_to_types(key)
                        buff.append({key: value})
                    else:
                        value = value[2:]
                        value = val_key_to_types(value)
                        buff.append(value)
                elif last == dict:
                    if key and value:
                        buff[key] = value
                    elif value:
                        if '' not in buff.keys():
                            buff[''] = []
                        buff[''].append(value)            

            elif diff < 0:
                if pars_file[i+1][1][:2] == '- ':
                    if last == list:
                        key = key[2:]
                        key = val_key_to_types(key)
                        buff.append({key: []})
                        buff = buff[-1][key]
                        last = list
                    elif last == dict:
                        buff[key] = []
                        buff = buff[key]
                        last = list
                else:
                    if last == list:
                        key = key[2:]
                        key = val_key_to_types(key)
                        buff.append({key: {}})
                        buff = buff[-1][key]
                        last = dict
                    elif last == dict:
                        buff[key] = {}
                        buff = buff[key]
                        last = dict
                buffers.append(buff)

            elif diff > 0:
                if last == list:
                    value = value[2:]
                    value = val_key_to_types(value)
                    buff.append(value)
                elif last == dict:
                    if value and key:
                        buff[key] = value
                    elif value:
                        if None not in buff.keys():
                            buff[None] = []
                        buff[None].append(value)   
                buff = buffers[-diff-1]
                last = type(buff)
                buffers = buffers[:-diff]

        
        with open(json_name, 'w') as jsonfile:
            content = list(str(objs))
            for i in range(len(content)-1):
                if content[i] == "'":
                    content[i] = '"'
            content = "".join(content)
            content = content.replace(r"\\&comma321654897321265468&", "'").replace(r'\&doublecomma321654897321265468&', '"')
            jsonfile.write(content)