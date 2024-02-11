Automation Testing is one of the essential aspects for organizations that build sophisticated applications.  
<b>RestAsssured</b> Framework, a Java-based framework,is commonly used for REST API Automation Testing in many companies.  
The framework enables users to access functionality and identify issues in REST Web Services. However, API Testing  
is not straightforward, especially in Java. But, the complications involved in API Testing can be eliminated as the  
RestAssured framework simplifies requesting and comprehending complex tests.  

<b>cars-api-test</b> is a test framework that uses <b>RestAsssured</b> and is intended to test <b>cars-rest-jwt-dev</b> backend api  
which is Java Spring REST API with JWT based authentication in the HTTP Authorization header. For details please visit https://github.com/IlkoPanchev/cars-rest-jwt-dev  

The main idea is to use this test framework in the CI/CD procces(for example see Heroku pipelines https://devcenter.heroku.com/articles/pipelines)  
If there is а new commit in <b>cars-rest-jwt-dev</b> the master branch is automatically deployed to the pipeline’s staging app for testing.  
In staging should be launched <b>cars-api-test</b> to test all functionality of the backen API.  
If the tests are successful, staging will be promoted to production, making application available to end users.
