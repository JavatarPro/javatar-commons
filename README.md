**Json & Yaml reader utils** 

Mostly util cab be used for test puposes (i.e. getting data from files).
The library decreses test complexcity and help to load data from file.
Util classes are 
- pro.javatar.reader.JsonReader
- pro.javatar.reader.YamlReader

Main functions:
- create object (list) from file
- create object (list) from string
- get content from file as string

---

## How to configure

1. Add maven dependency
    ```
    <dependency>
        <groupId>pro.javatar.commons</groupId>
        <artifactId>javatar-commons</artifactId>
        <version>0.6</version>
        <scope>test</scope>
    </dependency>
    ```
2. Create necessarry file in resources folder 

3. How to use
	```
	ResourceReader reader = JsonReader.getInstance();
	User user = jsonReader.getObjectFromFile("user.json", User.class);
	```
