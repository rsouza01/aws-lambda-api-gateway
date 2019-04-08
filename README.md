# aws-lambda-api-gateway



## Build

´´´
mvn clean package shade:shade
´´´

## Resources

https://www.baeldung.com/java-aws-lambda
https://www.baeldung.com/aws-lambda-dynamodb-java
https://www.baeldung.com/aws-lambda-api-gateway

## Test

curl -X PUT 'https://xxxxxxx.execute-api.us-east-1.amazonaws.com/v1/persons' \
-H 'content-type: application/json' \
-d '{"id": 5, "firstName": "John", "lastName": "Doe", "age": 30, "address": "Uruguay"}'
