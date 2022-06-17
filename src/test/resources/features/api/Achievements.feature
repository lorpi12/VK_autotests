#language:ru
@test

Функционал: Проверка API HRM /achievements

  Сценарий: 1 Test case Get /achievements/

    #Авторизация
    * создать запрос
      | method | url                                     | body           |
      | POST   | http://178.154.246.238:58082/api/login/ | authAdmin.json |
    * добавить header
      | Content-Type | application/json |
      | accept       | application/json |
    * отправить запрос
    * статус код 200
    * извлечь данные
      | token | $.token |

    #Запрос
    * создать запрос
      | method | url                                            |
      | GET    | http://178.154.246.238:58082/api/achievements/ |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 200
    * проверка соответствия запроса схеме в "src/test/resources/jsonShema/GetAchievements.json"

  Сценарий: 2 Test case POST /achievements/

    # Рандомное имя
    * сгенерировать переменные
      | name | EEEEEEEE |

    #Запрос
    * создать запрос
      | method | url                                            | body              |
      | POST   | http://178.154.246.238:58082/api/achievements/ | PostAccounts.json |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 201
    * извлечь данные
      | id   | $.id   |
      | name | $.name |
    * проверка соответствия запроса схеме в "src/test/resources/jsonShema/PostAchievements.json"

  Сценарий: 3 Test case GET /achievements/{id}

    #Запрос
    * создать запрос
      | method | url                                                 |
      | GET    | http://178.154.246.238:58082/api/achievements/${id} |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 200
    * извлечь данные
      | id_get   | $.id   |
      | name_get | $.name |
    * сравнить значения
      | ${id}   | == | ${id_get}   |
      | ${name} | == | ${name_get} |
    * проверка соответствия запроса схеме в "src/test/resources/jsonShema/PostAchievements.json"

  Сценарий: 4 Test case PATCH /achievements/{id}
    # Рандомное имя
    * сгенерировать переменные
      | name_patch_rand | EEEEEEEE |

    #Запрос
    * создать запрос
      | method | url                                                 | body               |
      | PATCH  | http://178.154.246.238:58082/api/achievements/${id} | PatchAccounts.json |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 200
    * извлечь данные
      | id_patch   | $.id   |
      | name_patch | $.name |
    * сравнить значения
      | ${id}              | == | ${id_patch}   |
      | ${name_patch_rand} | == | ${name_patch} |
    * проверка соответствия запроса схеме в "src/test/resources/jsonShema/PostAchievements.json"

  Сценарий: 5 Test case DELETE /achievements/{id}

    #Запрос удаления
    * создать запрос
      | method | url                                                 |
      | DELETE | http://178.154.246.238:58082/api/achievements/${id} |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 204

    #Запрос проверки
    * создать запрос
      | method | url                                                 |
      | GET    | http://178.154.246.238:58082/api/achievements/${id} |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 404