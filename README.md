 <a name="#documentr_top"></a>

> **This project requires JVM version of at least 1.8**






<a name="documentr_heading_0"></a>

# newznab-api <sup><sup>[top](#documentr_top)</sup></sup>



> NewzNab API in java






<a name="documentr_heading_1"></a>

# Table of Contents <sup><sup>[top](#documentr_top)</sup></sup>



 - [newznab-api](#documentr_heading_0)
 - [Table of Contents](#documentr_heading_1)
 - [Building the Package](#documentr_heading_2)
   - [*NIX/Mac OS X](#documentr_heading_3)
   - [Windows](#documentr_heading_4)
 - [Running the Tests](#documentr_heading_5)
   - [*NIX/Mac OS X](#documentr_heading_6)
   - [Windows](#documentr_heading_7)
   - [Dependencies - Gradle](#documentr_heading_8)
   - [Dependencies - Maven](#documentr_heading_9)
   - [Dependencies - Downloads](#documentr_heading_10)






<a name="documentr_heading_2"></a>

# Building the Package <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_3"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`./gradlew build`




<a name="documentr_heading_4"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

`./gradlew.bat build`


This will compile and assemble the artefacts into the `build/libs/` directory.

Note that this may also run tests (if applicable see the Testing notes)



<a name="documentr_heading_5"></a>

# Running the Tests <sup><sup>[top](#documentr_top)</sup></sup>



<a name="documentr_heading_6"></a>

## *NIX/Mac OS X <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`gradlew --info test`



<a name="documentr_heading_7"></a>

## Windows <sup><sup>[top](#documentr_top)</sup></sup>

From the root of the project, simply run

`gradle --info test`

if you do not have gradle installed, try:

`./gradlew.bat --info test`


The `--info` switch will also output logging for the tests



<a name="documentr_heading_8"></a>

## Dependencies - Gradle <sup><sup>[top](#documentr_top)</sup></sup>



```
dependencies {
	runtime(group: 'synapticloopltd', name: 'newznab-api', version: '1.0.0', ext: 'jar')

	compile(group: 'synapticloopltd', name: 'newznab-api', version: '1.0.0', ext: 'jar')
}
```



or, more simply for versions of gradle greater than 2.1



```
dependencies {
	runtime 'synapticloopltd:newznab-api:1.0.0'

	compile 'synapticloopltd:newznab-api:1.0.0'
}
```





<a name="documentr_heading_9"></a>

## Dependencies - Maven <sup><sup>[top](#documentr_top)</sup></sup>



```
<dependency>
	<groupId>synapticloopltd</groupId>
	<artifactId>newznab-api</artifactId>
	<version>1.0.0</version>
	<type>jar</type>
</dependency>
```





<a name="documentr_heading_10"></a>

## Dependencies - Downloads <sup><sup>[top](#documentr_top)</sup></sup>


You will also need to download the following dependencies:



### cobertura dependencies

  - net.sourceforge.cobertura:cobertura:2.0.3: (It may be available on one of: [bintray](https://bintray.com/net.sourceforge.cobertura/maven/cobertura/2.0.3/view#files/net.sourceforge.cobertura/cobertura/2.0.3) [mvn central](http://search.maven.org/#artifactdetails|net.sourceforge.cobertura|cobertura|2.0.3|jar))


### compile dependencies

  - org.apache.httpcomponents:httpclient:4.5.3: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.3/view#files/org.apache.httpcomponents/httpclient/4.5.3) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.3|jar))
  - commons-io:commons-io:2.5: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.5/view#files/commons-io/commons-io/2.5) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.5|jar))
  - org.slf4j:slf4j-api:1.7.21: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.21/view#files/org.slf4j/slf4j-api/1.7.21) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.21|jar))
  - org.json:json:20160810: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160810/view#files/org.json/json/20160810) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160810|jar))


### runtime dependencies

  - org.apache.httpcomponents:httpclient:4.5.3: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.3/view#files/org.apache.httpcomponents/httpclient/4.5.3) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.3|jar))
  - commons-io:commons-io:2.5: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.5/view#files/commons-io/commons-io/2.5) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.5|jar))
  - org.slf4j:slf4j-api:1.7.21: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.21/view#files/org.slf4j/slf4j-api/1.7.21) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.21|jar))
  - org.json:json:20160810: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160810/view#files/org.json/json/20160810) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160810|jar))


### testCompile dependencies

  - junit:junit:4.12: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - org.mockito:mockito-all:2.0.2-beta: (It may be available on one of: [bintray](https://bintray.com/org.mockito/maven/mockito-all/2.0.2-beta/view#files/org.mockito/mockito-all/2.0.2-beta) [mvn central](http://search.maven.org/#artifactdetails|org.mockito|mockito-all|2.0.2-beta|jar))
  - org.apache.logging.log4j:log4j-slf4j-impl:2.7: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.7/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.7|jar))
  - org.apache.logging.log4j:log4j-core:2.7: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.7/view#files/org.apache.logging.log4j/log4j-core/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.7|jar))
  - org.powermock:powermock:1.6.6: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock/1.6.6/view#files/org.powermock/powermock/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock|1.6.6|jar))
  - org.powermock:powermock-api-mockito:1.6.6: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock-api-mockito/1.6.6/view#files/org.powermock/powermock-api-mockito/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock-api-mockito|1.6.6|jar))
  - org.powermock:powermock-module-junit4:1.6.6: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock-module-junit4/1.6.6/view#files/org.powermock/powermock-module-junit4/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock-module-junit4|1.6.6|jar))
  - cglib:cglib:3.2.4: (It may be available on one of: [bintray](https://bintray.com/cglib/maven/cglib/3.2.4/view#files/cglib/cglib/3.2.4) [mvn central](http://search.maven.org/#artifactdetails|cglib|cglib|3.2.4|jar))


### testRuntime dependencies

  - junit:junit:4.12: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - org.mockito:mockito-all:2.0.2-beta: (It may be available on one of: [bintray](https://bintray.com/org.mockito/maven/mockito-all/2.0.2-beta/view#files/org.mockito/mockito-all/2.0.2-beta) [mvn central](http://search.maven.org/#artifactdetails|org.mockito|mockito-all|2.0.2-beta|jar))
  - org.apache.logging.log4j:log4j-slf4j-impl:2.7: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.7/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.7|jar))
  - org.apache.logging.log4j:log4j-core:2.7: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.7/view#files/org.apache.logging.log4j/log4j-core/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.7|jar))
  - org.powermock:powermock:1.6.6: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock/1.6.6/view#files/org.powermock/powermock/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock|1.6.6|jar))
  - org.powermock:powermock-api-mockito:1.6.6: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock-api-mockito/1.6.6/view#files/org.powermock/powermock-api-mockito/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock-api-mockito|1.6.6|jar))
  - org.powermock:powermock-module-junit4:1.6.6: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock-module-junit4/1.6.6/view#files/org.powermock/powermock-module-junit4/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock-module-junit4|1.6.6|jar))
  - cglib:cglib:3.2.4: (It may be available on one of: [bintray](https://bintray.com/cglib/maven/cglib/3.2.4/view#files/cglib/cglib/3.2.4) [mvn central](http://search.maven.org/#artifactdetails|cglib|cglib|3.2.4|jar))

**NOTE:** You may need to download any dependencies of the above dependencies in turn (i.e. the transitive dependencies)

--

> `This README.md file was hand-crafted with care utilising synapticloop`[`templar`](https://github.com/synapticloop/templar/)`->`[`documentr`](https://github.com/synapticloop/documentr/)

--
