#language:ru
@test

Функционал: Проверка API HRM /accounts

  Сценарий: 1 Test case Get /accounts/

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
      | method | url                                        |
      | GET    | http://178.154.246.238:58082/api/accounts/ |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 200
    * проверка соответствия запроса схеме в "src/test/resources/jsonShema/GetAccounts.json"

  Сценарий: 2 Test case POST /accounts/

    # Рандомное имя
    * сгенерировать переменные
      | name | EEEEEEEE |

    #Запрос
    * создать запрос
      | method | url                                        | body              |
      | POST   | http://178.154.246.238:58082/api/accounts/ | PostAccounts.json |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 201
    * извлечь данные
      | id           | $.id           |
      | active_admin | $.active_admin |
    * проверка соответствия запроса схеме в "src/test/resources/jsonShema/PostAccounts.json"

  Сценарий: 3 Test case GET /accounts/{id}

    #Запрос
    * создать запрос
      | method | url                                             |
      | GET    | http://178.154.246.238:58082/api/accounts/${id} |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 200
    * извлечь данные
      | id_get           | $.id           |
      | active_admin_get | $.active_admin |
      | name_get         | $.name         |
    * сравнить значения
      | ${id}           | == | ${id_get}           |
      | ${active_admin} | == | ${active_admin_get} |
      | ${name}         | == | ${name_get}         |
    * проверка соответствия запроса схеме в "src/test/resources/jsonShema/PostAccounts.json"

  Сценарий: 4 Test case PATCH /accounts/{id}
    # Рандомное имя
    * сгенерировать переменные
      | name_patch_rand | EEEEEEEE |

    #Запрос
    * создать запрос
      | method | url                                             | body               |
      | PATCH  | http://178.154.246.238:58082/api/accounts/${id} | PatchAccounts.json |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 200
    * извлечь данные
      | id_patch           | $.id           |
      | active_admin_patch | $.active_admin |
      | name_patch         | $.name         |
    * сравнить значения
      | ${id}              | == | ${id_patch}           |
      | ${active_admin}    | == | ${active_admin_patch} |
      | ${name_patch_rand} | == | ${name_patch}         |
    * проверка соответствия запроса схеме в "src/test/resources/jsonShema/PostAccounts.json"

  Сценарий: 5 Test case DELETE /accounts/{id}

    #Запрос удаления
    * создать запрос
      | method | url                                             |
      | DELETE | http://178.154.246.238:58082/api/accounts/${id} |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 204

    #Запрос проверки
    * создать запрос
      | method | url                                             |
      | GET    | http://178.154.246.238:58082/api/accounts/${id} |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 404






