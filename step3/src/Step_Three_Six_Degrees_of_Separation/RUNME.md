### Requirements   
> Java 11      
> Maven    

### Build   
On Step3/src/Step_Three_Six_Degrees_of_Separation folder execute:   

```
mvn clean package
```

### Run   
On Step3/src/Step_Three_Six_Degrees_of_Separation folder execute:   

```
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=dev --actors=Sylvester Stallone,Linda Kerridge"
```

The two minus signs should be used as described   

1. profile   
> should be dev or prod   
> only will change the log level   

2. actors   
> should be informed between "," (comma).   
> Do not add space after comma.   
> maximum of two actors   
   

