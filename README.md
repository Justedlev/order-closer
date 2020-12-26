# Order Closer microservice for Storehouse
- service getting data from a middleware about the orders going to be closed. The service works with DB for updating orders data (moving to the state “closed”)

## 1. Subscribed to pipe ***"full-content-detector"***:

>JSON - **{ "productId": "Integer", "spotCoord": { "row": "Integer", "shelf": "Integer", "place": "Integer" } }**

## 2. Sent to the ***"logs"***:
>JSON - **{ "date": "LocalDate", "time": "LocalTime", "messageType": "Enum = INFO, WARNING, ERROR", "serviceName": "String", "message": "String" }**

