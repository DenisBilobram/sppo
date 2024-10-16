from pyswip import Prolog
from utils import validate_input, parse_input
from valheim_helper import execute_query


def main():
    print("Добро пожаловать в систему поддержки принятия решений Valheim!")
    print("Вы можете задать вопросы о биомах, существах, ресурсах и постройках.")
    print("Например:")
    print("- Какие ресурсы доступны в биоме {луга}?")
    print("- Какие существа обитают в биоме {горы}?")
    print("- Какие материалы нужны для постройки {портала}?")
    print("- Какие существа уникальны для биома {пустошь}?")
    print("- Какие биомы общие для существ {грейдворф} и {драугр}")
    print("- Какие ресурсы в биомах с существом {дракон}")
    print("- Какие биомы с существом {грейдворф} и ресурсом {древесина}")
    print("Для выхода введите 'выход'.\n")
    
    while True:
        user_input = input("Введите ваш запрос: ").strip()
        
        if user_input.lower() == 'выход':
            print("Спасибо за использование системы. До свидания!")
            break
        
        if not validate_input(user_input):
            print("Пустой ввод. Пожалуйста, введите ваш запрос.")
            continue
        
        params = parse_input(user_input)
        
        response = execute_query(params)
        
        print(response)
        print()
    
if __name__ == "__main__":
    main()