# Order Closer - microservice for Storehouse
- service getting data from a middleware about the orders going to be closed. The service works with DB for updating orders data (moving to the state "CLOSED")

## 1. Subscribed:

>JSON - **{ "productId": LONG, "spotCoord": { "row": INTEGER, "shelf": INTEGER, "place": INTEGER } }**

## 2. Sent:
>JSON - **{ "log": "String in format "yyyy-MM-dd HH:mm:ss | TYPE_LOG(Enum - ERROR, WARNING, INFO) [SERVICE_NAME] - MESSAGE"" }**

