def ham_code(st):
    if not st.isnumeric():
        exit()
    st = list(map(int, list(st)))

    if len(st) != 7 or set(st) != {0, 1}:
        print("wrong message")
        exit()

    r1 = (st[2]+st[4]+st[6])%2
    r2 = (st[2]+st[5]+st[6])%2
    r3 = (st[4]+st[5]+st[6])%2

    n = 0

    if r1 != st[0]: n += 1
    if r2 != st[1]: n += 2
    if r3 != st[3]: n += 4

    if n != 0 and n not in (1,2,4):
        st[n-1] = (st[n-1]+1)%2

    return f'{st[2]}{st[4]}{st[5]}{st[6]}, ошибка в {n}-ом бите'

print(ham_code('1110001'))