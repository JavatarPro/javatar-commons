**Json & Yaml reader utils** 

Mostly util cab be used for test purposes (i.e. getting data from files).
The library decreases test complexity and help to load data from file.
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
        <version>0.9</version>
        <scope>test</scope>
    </dependency>
    ```
2. Create necessary file in resources folder 

3. How to use
	```
	ResourceReader reader = JsonReader.getInstance();
	User user = jsonReader.getObjectFromFile("user.json", User.class);
	```
