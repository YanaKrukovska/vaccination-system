# Система запису на вакцинацію

**Мета розробки**: створення системи адміністрування та моніторингу процесу вакцинації у медичному закладі. 

## Технічні вимоги:
Клієнт-серверне застосування з веб-інтерфейсом користувача. Серверна частина зберігає данні в реляційній базі даних.

### Функціональні вимоги:
1.	Користувач повинен мати змогу:
    -	Реєструватися
    -	Записуватися на вакцинацію
    -	Переглядати статус свого запису
    -	Редагувати, скасовувати свій запис
    -	Вести журнал своєї температури
    
    
2.	Лікар повинен мати змогу:
    -	Переглядати чергу пацієнтів на вакцинацію
    -	Підтверджувати та скасовувати запити пацієнтів на вакцинацію
    -	Переглядати журнали здоров’я  усіх пацієнтів
    -	Фільтрувати пацієнтів з критичними показниками
    -	Бачити статистичний звіт по пацієнтам

Доступ до системи повинен бути захищеним паролем. 

Користувачі не повинні мати доступ до даних інших користувачів. 

Програма повинна бути покрита тестами.
