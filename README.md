# Transaction API 

Author: Joni Pohjaniemi

GitHub repository:
https://github.com/pohjaniemi/transaction_api

### Try the online demo

Documentation:

https://api.pohjaniemi.com/docs/

API root endpoint:

https://api.pohjaniemi.com/transactions

### Pre-requisites for local installation

>To run this demo project locally on your own machine, make sure to have [Git](https://git-scm.com/downloads) and [Docker](https://docs.docker.com/get-docker/) installed first.

### Installation steps

1. Clone the project [repository](https://github.com/pohjaniemi/transaction_api) to your local environment.

2. Create a simple file called <code>.env</code> in the root folder of the project.

3. Add a **DB_PASS** environment variable to the <code>.env</code> file. Example: **DB_PASS=secret**
4. Start the servers: 
  <code>docker-compose up -d</code>
5. Verify that the server responds on http://localhost:8080/
6. See the [OpenAPI definition](http://localhost:8080/swagger-ui/index.html) for the REST API endpoints.
7. Tip: You can use the API directly in your browser. See the "Try it out" button in Swagger.
8. Stop the servers: <code>docker-compose down</code>
