**Json reader util** 

Mostly util cab be used for test puposes (i.e. getting data from files).
The library decreses test complexcity and help to load data from file.
Util class is pro.javatar.reader.JsonReader.

Main functions:
- create object (list) from file
- create object (list) from string
- get content from file as string

---

## How to configure

1. Add maven dependency
	<dependency>
        <groupId>pro.javatar.work</groupId>
    	<artifactId>json-reader</artifactId>
    	<version>0.1-SNAPSHOT</version>
		<scope>test</scope>
    </dependency>
2. Create necessarry file in resources folder 
3. How to use
	JsonReader jsonReader = new JsonReader();
	User user = jsonReader.getObjectFromFile("user.json", User.class);