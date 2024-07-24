# invoice-app
### Provide users with net payable amount based on a given bill


## Code styling

For Intellij Idea, import Google code style scheme from
### ``intellij-java-google-style.xml``


### To generate report, run
### ``mvn checkstyle:check``


## To run tests for app
### ``mvn clean verify``


## To run the app
### ``mvn spring-boot:run``

## API Definition

Once deployed, Swagger UI can be accessed for API Definition at
### ``http://localhost:8080/api/v1/swagger-ui/index.html``

### Generate net payable endpoint

### Method: POST
### ``http://localhost:8080/api/v1/invoices/discounts``

#### POST DATA example

```
{
    "user": {
        "name": "hirantha",
        "type": "CUSTOMER",
        // "type": "EMPLOYEE",
        // "type": "AFFILIATE",
        // "registeredDate": "2021-05-22"
        "registeredDate": "2024-05-22"
    },
    "items": [
        {
            "name": "item1",
            "price": 123.89,
            "grocery": false
        },
        {
            "name": "itemG1",
            "price": 12.32,
            "grocery": true
        }
    ]
}
```
