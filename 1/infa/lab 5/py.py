import openpyxl

CEELS = ['H','I','J','K','M','N','O','P','R','S','T','U','W','X','Y','Z']
FLAGS = ['AC','AE','AG','AI','AK','AM']
wb = openpyxl.load_workbook('main.xlsx', data_only=True)
worksheet = wb['Лист1']

def calc(n):
    number1 = ''
    number2 = ''

    for i in range(len(CEELS)):
        number1 += str(worksheet[f'{CEELS[i]}{n}'].value)
        number2 += str(worksheet[f'{CEELS[i]}{n+1}'].value)

    flags = [0,0,0,0,0,0]
    number3 = ''
    ov = False
    for i in range(-1, -17, -1):
        v = int(number1[i]) + int(number2[i])
        if v == 2:
            if i == -16:
                flags[0] = 1
            if i == -3:
                flags[2] = 1

            if ov:
                number3 = '1' + number3
            else:
                ov = True
                number3 = '0' + number3
        elif v == 1:
            if ov:
                if i == -16:
                    flags[0] = 1
                if i == -3:
                    flags[2] = 1
                
                ov = True
                number3 = '0' + number3
            else:
                number3 = '1' + number3
        elif v == 0:
            if ov:
                number3 = '1' + number3
                ov = False
            else:
                number3 = '0' + number3

    if number3.count('1') % 2 == 0:
        flags[1] = 1
    if number3.count('1') == 0:
        flags[3] = 1
    flags[4] = int(number3[0])

    if int(number1[0]) != flags[4]:
        flags[5] = 1
    wb.close()

    wb1 = openpyxl.load_workbook('main.xlsx')
    worksheet1 = wb1['Лист1']

    for i in range(len(CEELS)):
        worksheet1[f'{CEELS[i]}{n+3}'] = int(number3[i])
    
    for i in range(len(FLAGS)):
        worksheet1[f'{FLAGS[i]}{n}'] = flags[i]

    wb1.save(r'C:\Users\denis\Documents\sppo\infa\lab 5\main.xlsx')
    wb1.close()


calc(17)
calc(22)
calc(27)
calc(32)
calc(37)
calc(42)
calc(47)
