# Order Closer microservice for Storehouse

1. Subscribed to pipe ***"full-content-detector"***:

>JSON - **{ "productId": "Integer", "spotCoord": { "row": "Integer", "shelf": "Integer", "place": "Integer" } }**

2. Sent to the ***"logs"***:
>JSON - **{ "date": "LocalDate", "time": "LocalTime", "messageType": "Enum = INFO, WARNING, ERROR", "serviceName": "String", "message": "String" }**

