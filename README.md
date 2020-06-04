# oauth2-demo2:
The API shows an example of oauth2 uses. It contains three services:
 - Authorization server: `spring-authorization-test-server`
 - Resource server: `resource-test-server`
 - Client application: `client-to-resource-server`
 
To make easier to use this example I made `secret-provider.sh` and `build.sh` scripts as well.

> If you run the services with docker from a console you can follow the requests between the services on the basis of logs.

### Sample Usage
User can call the protected resource via two endpoints:
<a href=http://localhost:8080/swagger-ui.html#/>localhost:8080/swagger-ui.html</a>
 - `/oauth2example/resource/by_client`
 - `/oauth2example/resource/by_user`

The first uses the application client id and secret to provide the resource.
They are stored in application.yml at `client-to-resource-server` 
and `spring-authorization-test-server` in memory database init script.

![alt text](https://github.com/ibartuszek/oauth2-demo2/blob/master/flow.jpg "Flow")

### Dependencies:

 - Spring-boot (web, data, security)
 - io.springfox:springfox-swagger2
 - com.h2database:h2

## Build:
 - `secret-provider` uses openssl which can be download from <a>https://www.openssl.org/source/</a> 
 - much easier to run all services with the main `docker-compose`.
  You can install docker: <a href="https://docs.docker.com/engine/install/ubuntu/">install docker on ubuntu</a> 
  or <a href="https://docs.docker.com/docker-for-windows/install/">download docker for windows</a> 
 - clone from github: `git clone git@github.com:ibartuszek/oauth2-demo2.git`
 - step into `oauth2-demo2` folder
 - run `build.sh` with two arguments:
   - First: general password for the key generation
   - Second: the key alias which should be contained by the key store
   
   
   > Build script create a keystore on the basis of `secret-provider/configuration.cnf`. 
   > Copy the temporary files into its places then delete temporary files.
   > Runs `mvn clean package` with the maven wrapper 
   > then creates docker images with `docker-compose build`.

## Run:
 
 - `docker-compose up` starts all containers. This case you can reach swagger on: <a href=http://localhost:8080/swagger-ui.html#/>localhost:8080</a> port.

### Useful links

- <a href="https://github.com/ibartuszek/oauth2-demo2">oauth2-demo2 github repo</a>
- <a href="https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/">Spring boot documentation</a>
- <a href="https://www.docker.com/">docker.com</a>
- <a href="https://swagger.io/">swagger.io</a>
- <a href="https://sourceforge.net/projects/gnuwin32/files/openssl/0.9.8h-1/openssl-0.9.8h-1-setup.exe/download?use_mirror=netcologne">Openssl for windows users</a>


### Contact:
- github: <a href="https://github.com/ibartuszek">github.com/ibartuszek</a>
- email: <a href="mailto:istvan.bartuszek@gmail.com">istvan.bartuszek@gmail.com</a>