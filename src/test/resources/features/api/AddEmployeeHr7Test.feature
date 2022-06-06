#language:ru
@test

Функционал: Проверка API HRM

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
    * проверка соответствия запроса схеме в "src/test/resources/jsonShema/shema.json"

