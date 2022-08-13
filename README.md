# SpringBoot Authentication and Authorization Application

##Authentication

The application takes the user credentials i.e. username and password and authentictes the user and providers the access token and refresh token:

http://localhost:8080/api/login

![Authentication](images/Authentication.png)


To call a service using the authorization token:

http://localhost:8080/api/users

![Accessig Resource](images/AccessingResource.png)


To generate a access token using the refresh token

http://localhost:8080/api/token/refresh

![Using Refresh Token](images/RefreshToken.png)

