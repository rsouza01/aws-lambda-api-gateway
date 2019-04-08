package com.rsouza01.mvc;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;


public class PersonAPIHandler implements RequestStreamHandler {

    private String DYNAMODB_TABLE_NAME = "Person";

    //StorePersonFunction
    //com.rsouza01.mvc.PersonAPIHandler::handleRequest
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject responseJson = new JSONObject();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDb = new DynamoDB(client);

        try {
            JSONObject event = (JSONObject) parser.parse(reader);

            if (event.get("body") != null) {
                Person person = new Person((String) event.get("body"));

                dynamoDb.getTable(DYNAMODB_TABLE_NAME)
                        .putItem(
                                new PutItemSpec().withItem(
                                        new Item()
                                                .withNumber("id", person.getId())
                                                .withString("firstName", person.getFirstName())
                                                .withString("lastName", person.getLastName())
                                                .withNumber("age", person.getAge())
                                                .withString("address", person.getAddress())
                                )
                        );

            }

            JSONObject responseBody = new JSONObject();
            responseBody.put("message", "New item created");

            JSONObject headerJson = new JSONObject();
            headerJson.put("x-custom-header", "my custom header value");

            responseJson.put("statusCode", 200);
            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toString());

        } catch (ParseException pex) {
            responseJson.put("statusCode", 400);
            responseJson.put("exception", pex);
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toString());
        writer.close();
    }

    //GetPersonByHTTPParamFunction
    //com.rsouza01.mvc.PersonAPIHandler::handleGetByParam
    public void handleGetByParam(
            InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        JSONParser parser = new JSONParser();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject responseJson = new JSONObject();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDb = new DynamoDB(client);

        Item result = null;
        try {
            JSONObject event = (JSONObject) parser.parse(reader);
            JSONObject responseBody = new JSONObject();

            if (event.get("pathParameters") != null) {
                JSONObject pps = (JSONObject) event.get("pathParameters");
                if (pps.get("id") != null) {
                    int id = Integer.parseInt((String) pps.get("id"));
                    result = dynamoDb.getTable(DYNAMODB_TABLE_NAME).getItem("id", id);
                }
            }
            else if (event.get("pathParameters") != null) {
                JSONObject pps = (JSONObject) event.get("pathParameters");
                if (pps.get("id") != null) {
                    int id = Integer.parseInt((String) pps.get("id"));
                    result = dynamoDb.getTable(DYNAMODB_TABLE_NAME).getItem("id", id);
                }
            }
            if (result != null) {
                Person person = new Person(result.toJSON());
                responseBody.put("Person", person);
                responseJson.put("statusCode", 200);
            } else {
                responseBody.put("message", "No item found");
                responseJson.put("statusCode", 404);
            }

            JSONObject headerJson = new JSONObject();
            headerJson.put("x-custom-header", "my custom header value");

            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toString());

        } catch (ParseException pex) {
            responseJson.put("statusCode", 400);
            responseJson.put("exception", pex);
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toString());
        writer.close();    }
}
