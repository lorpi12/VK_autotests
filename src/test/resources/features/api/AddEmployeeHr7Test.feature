#language:ru
@test

Функционал: Проверка API HRM

  Сценарий: 1 Test case Get /accounts/

    #Авторизация
    * создать запрос
      | method | path    | body           |
      | POST   | /login/ | authAdmin.json |
    * добавить header
      | Content-Type | application/json |
      | accept       | application/json |
    * отправить запрос
    * статус код 200
    * извлечь данные
      | token | $.token |

    #Запрос
    * создать запрос
      | method | path       |
      | GET    | /accounts/ |
    * добавить header
      | Content-Type | application/json |
    * добавить авторизацию в хедер
    * отправить запрос
    * статус код 200