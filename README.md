# Transaction API 

Author: Joni Pohjaniemi

GitHub repository:
https://github.com/pohjaniemi/transaction_api

### Pre-requisites

>To run this demo project from your own machine, make sure you have [Git](https://git-scm.com/downloads) and [Docker](https://docs.docker.com/get-docker/) installed first.

### Installation steps

1. Clone the project [repository](https://github.com/pohjaniemi/transaction_api) to your local environment.


2. Create a simple file called <code>.env</code> in the root folder of the project. 


3. Add a <code>DB_PASS=</code> environment variable to the <code>.env</code> file. Example: <code>DB_PASS=secret<code>


4. Start the servers (MySQL + API) with the command:
<code>docker-compose up</code>


5. Verify that the server responds on http://localhost:8080/


6. See the OpenAPI definition for the REST API endpoints. 
   You can also use the API directly with the "Try it out" button.
      [OpenAPI definition](http://localhost:8080/swagger-ui/index.html)


7. Turn off the servers
<code>docker-compose down</code>