# Sample App using `jasypt-spring-boot-starter` dependency

 ## Asymmetric Encryption
 
 This app showcases the usage of asymmetric encryption/decryption using a pair of PEM certificates
 in an embedded embedded web server container with a default embedded environment. 
 
## Encryptable Property Sources enabled by:

In `pom.xml`:

```xml
    <dependency>
        <groupId>com.github.ulisesbocchio</groupId>
        <artifactId>jasypt-spring-boot-starter</artifactId>
        <version>1.10</version>
    </dependency>

```
The starter jar has a `spring.factories` definition that Spring Boot uses to bootstrap Autoconfiguration. The autoconfiguration
 simply loads the `@EnableEncryptableProperties` annotation that decorates all property sources in the Spring `Environment` to be
 encryptable.
 
 ## Custom Environment
 
 This app also showcases early initialization property encryption with a custom `Environment`:
 
 ```java
         new SpringApplicationBuilder()
                 .environment(new StandardEncryptableEnvironment())
                 .sources(SimpleStarterDemoApplication.class).run(args);
 ```
 
 **This is not required in most scenarios**
 
 ## Test jasypt-maven-plugin encryption

### Encrypt
 ```bash
mvn jasypt:encrypt-value -Dspring.config.location="file:src/main/resources/bootstrap.yml" -Djasypt.encryptor.public-key-format="PEM" -Djasypt.encryptor.public-key-location="file:src/main/resources/publickey.pem" -Djasypt.plugin.value="theValueYouWantToEncrypt"
 ```

### Decrypt
```bash
mvn jasypt:decrypt-value -Dspring.config.location="file:src/main/resources/bootstrap.yml" -Djasypt.encryptor.private-key-format="PEM" -Djasypt.encryptor.private-key-location="file:src/main/resources/privatekey.pem" -Djasypt.plugin.value="ENC(YSU0GjEtGFm3StP7UugLc+IIhsJfOQ8Oh3WumKwNdXnaq5WKG4PQ0Xyr4O7hLOMvK+/zOvVWgCEDV6CceOkM4DA8hbNv4qXaAmO+Sk6zu/1S40CkEw82pLxGn8yn4VMxHNt1TSUpFsVHabBgcoCcgwy9OpO5upVIADqDPZOSvJJc7EOgyqcVAtkWyK8YWKUXfzfCYuHL2kOODnbBCSqVUjutB0bTRRcgpnpClrTnfXZ1bYXrq6vPNqlfDNxR16OEa4+u2xu5Ou564Ks64CBLp594t9D0+sCb28C0zy8DmbaO7FM6NYEMTgAP1I9aytIhSufYLdq2OEuHIAAg8WEYEA==)"
```
 
