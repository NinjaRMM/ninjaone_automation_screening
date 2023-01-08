### Requirements   
> Java 11      
> Maven    

### Build   
On Step2/src/Step_Two_Filter_Movies folder execute:   

```
mvn clean package
```

### Run   
On Step2/src/Step_Two_Filter_Movies folder execute:   

```
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=dev --decade=2010 --output=2010s-movies.json"
```
Alternatively, call the jar created in the target folder: 

```
java -jar -Dspring.profiles.active=dev ./target/Step_Two_Filter_Movies-1.0.0.jar "--decade=2000" "--output=2000s-movies.json"
```


The two minus signs should be used as described   

1. profile   
> should be dev or prod   
> only will change the log level   

2. decade   
> should be informed between 1900 and 2010   
> should be lowercase   
> should ends with 0   

3. output   
> should be lowercase   
> the file will have same name informed.   
> by default, the file will be save on same folder that is movides.json, data folder.   
> If the same file name exits, the validation will stop the process and an exception will be thrown.