import re

def dop2_main():
    def val_key_to_types(st):
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
                st = st.replace("'", "\&comma&").replace('"', "\&doublecomma&")
        return st

    with open('yaml.yaml', 'r') as yamlfile:
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

            val_i = re.match(r'^[^\'\"]*: ', pars_file[i][1])
            key = value = False

            if val_i:
                key, value = pars_file[i][1][:val_i.end()-1], pars_file[i][1][val_i.end():]
            else:
                if pars_file[i][1][-1] == ':':
                    key = pars_file[i][1][:-1]
                else:
                    value = pars_file[i][1]

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

        
        with open('json.json', 'w') as jsonfile:
            content = list(str(objs))
            for i in range(len(content)-1):
                if content[i] == "'":
                    content[i] = '"'
            content = "".join(content)
            content = content.replace(r"\\&comma&", "'").replace(r'\&doublecomma&', '"')
            jsonfile.write(content)
dop2_main()      