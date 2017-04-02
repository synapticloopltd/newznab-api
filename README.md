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
 - [Logging - slf4j](#documentr_heading_8)
   - [Log4j](#documentr_heading_9)
 - [Artefact Publishing - Github](#documentr_heading_14)
 - [Artefact Publishing - Bintray](#documentr_heading_15)
   - [maven setup](#documentr_heading_16)
   - [gradle setup](#documentr_heading_17)
   - [Dependencies - Gradle](#documentr_heading_18)
   - [Dependencies - Maven](#documentr_heading_19)
   - [Dependencies - Downloads](#documentr_heading_20)


# The NewzNab API for java 

Easily and quickly connect to your favourite NewzNab provider's API 
programmatically

Provided you have a Usenet Indexer that is based on the NewzNab API protocol, 
this API will allow you to:

 - Get various personal feeds
   - Your cart
   - Your 'My Shows' section
   - Your 'My Movies' section

 - Get feeds for
   - Movies
   - Consoles
   - PC (Games)
   - Other
   - TV
   - Audio

 - Commenting
   - add comments
   - view comments

 - Download
   - The NZB file
   - The nfo

 - Search
   - generic search
   - TV search
   - Book Search
   - Movie Search
   - Audio Search

See the following file for a quick start:



```
package synapticloop.newznab.api;

import java.io.IOException;
import java.util.List;

import synapticloop.newznab.api.exception.NewzNabApiException;
import synapticloop.newznab.api.response.CartResponse;
import synapticloop.newznab.api.response.CommentResponse;
import synapticloop.newznab.api.response.CommentsResponse;
import synapticloop.newznab.api.response.FeedResponse;
import synapticloop.newznab.api.response.SearchResponse;
import synapticloop.newznab.api.response.model.CommentItem;
import synapticloop.newznab.api.response.model.FeedItem;
import synapticloop.newznab.api.response.model.Item;

public class Main {
	private static final String NEWZNAB_API = "http://lolo.sickbeard.com/api";

	public static void main(String[] args) {
		// Set up the API call - normally you would use the following line, but
		// for example purposes - the lolo.sickbeard.com API will work
		// NewzNabApi newzNabApi = new NewzNabApi("YOUR_API_URL", "YOUR_API_KEY");
		NewzNabApi newzNabApi = new NewzNabApi(NEWZNAB_API);

		search(newzNabApi);

		// you can also get various feeds

		try {
			printFeedResponse("AUDIO", newzNabApi.getFeedForAudio());
			printFeedResponse("CONSOLES", newzNabApi.getFeedForConsoles());
			printFeedResponse("MOVIES", newzNabApi.getFeedForMovies());
			printFeedResponse("OTHER", newzNabApi.getFeedForOther());
			printFeedResponse("PC", newzNabApi.getFeedForPc());
			printFeedResponse("TV", newzNabApi.getFeedForTv());
			printFeedResponse("SITE", newzNabApi.getFeedForSite());
		} catch (IOException | NewzNabApiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// now get some details
		try {
			SearchResponse searchResponse = getSearchResponse(newzNabApi, 0);
			// get the first result
			Item item = searchResponse.getItems().get(0);

			String guid = item.getGuid();

			// now that we have the guid - get some of the details
			String nfo = newzNabApi.getNfo(guid);
			System.out.println(nfo);

			CommentsResponse comments = newzNabApi.getComments(guid);
			List<CommentItem> commentItems = comments.getItems();
			System.out.println("Found " + commentItems.size() + " comments");
			for (CommentItem commentItem : commentItems) {
				System.out.println("  [COMMENT]: " + commentItem.getComment());
			}

			CommentResponse commentResponse = newzNabApi.addComment(guid, "We can add a comment");
			System.out.println("Added a comment with id: " + commentResponse.getId());

			// we can also add this to our cart
			CartResponse cartAddResponse = newzNabApi.cartAdd(guid);
			System.out.println("Cart add with id: " + cartAddResponse.getId());

			// we can also list the cart contents
			FeedResponse feedForCart = newzNabApi.getFeedForCart();
			List<FeedItem> feedItems = feedForCart.getFeedItems();
			for (FeedItem feedItem : feedItems) {
				System.out.println("Found the following in the cart, guid: " + feedItem.getGuid().getGuid() + ", title" +  feedItem.getTitle());
			}

			CartResponse cartDeleteResponse = newzNabApi.cartDelete(guid);
			System.out.println("Cart delete with guid: " + cartDeleteResponse.getId());

		} catch (NewzNabApiException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private static void printFeedResponse(String group, FeedResponse feedResponse) {
		List<FeedItem> feedItems = feedResponse.getFeedItems();
		for (FeedItem feedItem : feedItems) {
			System.out.println("Found [" + group + "] item with title: " + feedItem.getTitle());
		}

	}

	private static void search(NewzNabApi newzNabApi) {

		try {
			SearchResponse searchResponse = getSearchResponse(newzNabApi, 0);

			System.out.println("Found " + searchResponse.getTotal() + " results for the search.");

			printResults(searchResponse);

			searchResponse = getSearchResponse(newzNabApi, 10);

			printResults(searchResponse);

		} catch (IOException | NewzNabApiException ex) {
			ex.printStackTrace();
		}
	}

	private static SearchResponse getSearchResponse(NewzNabApi newzNabApi, int offset) throws NewzNabApiException, IOException {
		SearchResponse searchResponse = newzNabApi.search(
				"wanted", // the search phrase
				offset, // the offset
				10, // the max number of results to return
				-1, // the number of days back to search - in this case all
				false, // delete from cart
				false, // return extended attributes
				null, // the categories to search in - in this case we are searching all
				null // the usenet groups to search in - all of them if null
				);
		return searchResponse;
	}

	private static void printResults(SearchResponse searchResponse) {
		List<Item> searchItems = searchResponse.getItems();
		for (Item item : searchItems) {
			System.out.println("  Found result in category: [" + item.getCategoryName() + "] with name: " + item.getTitle());
		}
	}
}

```







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

# Logging - slf4j <sup><sup>[top](#documentr_top)</sup></sup>

slf4j is the logging framework used for this project.  In order to set up a logging framework with this project, sample configurations are below:



<a name="documentr_heading_9"></a>

## Log4j <sup><sup>[top](#documentr_top)</sup></sup>


You will need to include dependencies for this - note that the versions may need to be updated.

### Maven



```
<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-slf4j-impl</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

<dependency>
	<groupId>org.apache.logging.log4j</groupId>
	<artifactId>log4j-core</artifactId>
	<version>2.5</version>
	<scope>runtime</scope>
</dependency>

```



### Gradle &lt; 2.1



```
dependencies {
	...
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-slf4j-impl', version: '2.5', ext: 'jar')
	runtime(group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.5', ext: 'jar')
	...
}
```


### Gradle &gt;= 2.1



```
dependencies {
	...
	runtime 'org.apache.logging.log4j:log4j-slf4j-impl:2.5'
	runtime 'org.apache.logging.log4j:log4j-core:2.5'
	...
}
```




### Setting up the logging:

A sample `log4j2.xml` is below:



```
<Configuration status="WARN">
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT">
			<PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
		</Console>
	</Appenders>
	<Loggers>
		<Root level="trace">
			<AppenderRef ref="Console"/>
		</Root>
	</Loggers>
</Configuration>
```





<a name="documentr_heading_14"></a>

# Artefact Publishing - Github <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [GitHub](https://github.com/)

> Note that the latest version can be found [https://github.com/synapticloopltd/newznab-api/releases](https://github.com/synapticloopltd/newznab-api/releases)

As such, this is not a repository, but a location to download files from.



<a name="documentr_heading_15"></a>

# Artefact Publishing - Bintray <sup><sup>[top](#documentr_top)</sup></sup>

This project publishes artefacts to [bintray](https://bintray.com/)

> Note that the latest version can be found [https://bintray.com/synapticloop/maven/newznab-api/view](https://bintray.com/synapticloop/maven/newznab-api/view)



<a name="documentr_heading_16"></a>

## maven setup <sup><sup>[top](#documentr_top)</sup></sup>

this comes from the jcenter bintray, to set up your repository:



```
<?xml version="1.0" encoding="UTF-8" ?>
<settings xsi:schemaLocation='http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd' xmlns='http://maven.apache.org/SETTINGS/1.0.0' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>
  <profiles>
    <profile>
      <repositories>
        <repository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray</name>
          <url>http://jcenter.bintray.com</url>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>bintray-plugins</name>
          <url>http://jcenter.bintray.com</url>
        </pluginRepository>
      </pluginRepositories>
      <id>bintray</id>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>bintray</activeProfile>
  </activeProfiles>
</settings>
```





<a name="documentr_heading_17"></a>

## gradle setup <sup><sup>[top](#documentr_top)</sup></sup>

Repository



```
repositories {
	maven {
		url  "http://jcenter.bintray.com" 
	}
}
```



or just



```
repositories {
	jcenter()
}
```





<a name="documentr_heading_18"></a>

## Dependencies - Gradle <sup><sup>[top](#documentr_top)</sup></sup>



```
dependencies {
	runtime(group: 'synapticloop', name: 'newznab-api', version: '1.1.0', ext: 'jar')

	compile(group: 'synapticloop', name: 'newznab-api', version: '1.1.0', ext: 'jar')
}
```



or, more simply for versions of gradle greater than 2.1



```
dependencies {
	runtime 'synapticloop:newznab-api:1.1.0'

	compile 'synapticloop:newznab-api:1.1.0'
}
```





<a name="documentr_heading_19"></a>

## Dependencies - Maven <sup><sup>[top](#documentr_top)</sup></sup>



```
<dependency>
	<groupId>synapticloop</groupId>
	<artifactId>newznab-api</artifactId>
	<version>1.1.0</version>
	<type>jar</type>
</dependency>
```





<a name="documentr_heading_20"></a>

## Dependencies - Downloads <sup><sup>[top](#documentr_top)</sup></sup>


You will also need to download the following dependencies:



### cobertura dependencies

  - `net.sourceforge.cobertura:cobertura:2.1.1`: (It may be available on one of: [bintray](https://bintray.com/net.sourceforge.cobertura/maven/cobertura/2.1.1/view#files/net.sourceforge.cobertura/cobertura/2.1.1) [mvn central](http://search.maven.org/#artifactdetails|net.sourceforge.cobertura|cobertura|2.1.1|jar))


### compile dependencies

  - `org.apache.httpcomponents:httpclient:4.5.3`: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.3/view#files/org.apache.httpcomponents/httpclient/4.5.3) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.3|jar))
  - `commons-io:commons-io:2.5`: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.5/view#files/commons-io/commons-io/2.5) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.5|jar))
  - `com.fasterxml.jackson.core:jackson-databind:2.8.5`: (It may be available on one of: [bintray](https://bintray.com/com.fasterxml.jackson.core/maven/jackson-databind/2.8.5/view#files/com.fasterxml.jackson.core/jackson-databind/2.8.5) [mvn central](http://search.maven.org/#artifactdetails|com.fasterxml.jackson.core|jackson-databind|2.8.5|jar))
  - `org.slf4j:slf4j-api:1.7.21`: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.21/view#files/org.slf4j/slf4j-api/1.7.21) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.21|jar))
  - `org.json:json:20160810`: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160810/view#files/org.json/json/20160810) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160810|jar))


### runtime dependencies

  - `org.apache.httpcomponents:httpclient:4.5.3`: (It may be available on one of: [bintray](https://bintray.com/org.apache.httpcomponents/maven/httpclient/4.5.3/view#files/org.apache.httpcomponents/httpclient/4.5.3) [mvn central](http://search.maven.org/#artifactdetails|org.apache.httpcomponents|httpclient|4.5.3|jar))
  - `commons-io:commons-io:2.5`: (It may be available on one of: [bintray](https://bintray.com/commons-io/maven/commons-io/2.5/view#files/commons-io/commons-io/2.5) [mvn central](http://search.maven.org/#artifactdetails|commons-io|commons-io|2.5|jar))
  - `com.fasterxml.jackson.core:jackson-databind:2.8.5`: (It may be available on one of: [bintray](https://bintray.com/com.fasterxml.jackson.core/maven/jackson-databind/2.8.5/view#files/com.fasterxml.jackson.core/jackson-databind/2.8.5) [mvn central](http://search.maven.org/#artifactdetails|com.fasterxml.jackson.core|jackson-databind|2.8.5|jar))
  - `org.slf4j:slf4j-api:1.7.21`: (It may be available on one of: [bintray](https://bintray.com/org.slf4j/maven/slf4j-api/1.7.21/view#files/org.slf4j/slf4j-api/1.7.21) [mvn central](http://search.maven.org/#artifactdetails|org.slf4j|slf4j-api|1.7.21|jar))
  - `org.json:json:20160810`: (It may be available on one of: [bintray](https://bintray.com/org.json/maven/json/20160810/view#files/org.json/json/20160810) [mvn central](http://search.maven.org/#artifactdetails|org.json|json|20160810|jar))


### testCompile dependencies

  - `junit:junit:4.12`: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - `org.mockito:mockito-all:2.0.2-beta`: (It may be available on one of: [bintray](https://bintray.com/org.mockito/maven/mockito-all/2.0.2-beta/view#files/org.mockito/mockito-all/2.0.2-beta) [mvn central](http://search.maven.org/#artifactdetails|org.mockito|mockito-all|2.0.2-beta|jar))
  - `org.apache.logging.log4j:log4j-slf4j-impl:2.7`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.7/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.7|jar))
  - `org.apache.logging.log4j:log4j-core:2.7`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.7/view#files/org.apache.logging.log4j/log4j-core/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.7|jar))
  - `org.powermock:powermock:1.6.6`: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock/1.6.6/view#files/org.powermock/powermock/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock|1.6.6|jar))
  - `org.powermock:powermock-api-mockito:1.6.6`: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock-api-mockito/1.6.6/view#files/org.powermock/powermock-api-mockito/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock-api-mockito|1.6.6|jar))
  - `org.powermock:powermock-module-junit4:1.6.6`: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock-module-junit4/1.6.6/view#files/org.powermock/powermock-module-junit4/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock-module-junit4|1.6.6|jar))
  - `cglib:cglib:3.2.4`: (It may be available on one of: [bintray](https://bintray.com/cglib/maven/cglib/3.2.4/view#files/cglib/cglib/3.2.4) [mvn central](http://search.maven.org/#artifactdetails|cglib|cglib|3.2.4|jar))


### testRuntime dependencies

  - `junit:junit:4.12`: (It may be available on one of: [bintray](https://bintray.com/junit/maven/junit/4.12/view#files/junit/junit/4.12) [mvn central](http://search.maven.org/#artifactdetails|junit|junit|4.12|jar))
  - `org.mockito:mockito-all:2.0.2-beta`: (It may be available on one of: [bintray](https://bintray.com/org.mockito/maven/mockito-all/2.0.2-beta/view#files/org.mockito/mockito-all/2.0.2-beta) [mvn central](http://search.maven.org/#artifactdetails|org.mockito|mockito-all|2.0.2-beta|jar))
  - `org.apache.logging.log4j:log4j-slf4j-impl:2.7`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-slf4j-impl/2.7/view#files/org.apache.logging.log4j/log4j-slf4j-impl/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-slf4j-impl|2.7|jar))
  - `org.apache.logging.log4j:log4j-core:2.7`: (It may be available on one of: [bintray](https://bintray.com/org.apache.logging.log4j/maven/log4j-core/2.7/view#files/org.apache.logging.log4j/log4j-core/2.7) [mvn central](http://search.maven.org/#artifactdetails|org.apache.logging.log4j|log4j-core|2.7|jar))
  - `org.powermock:powermock:1.6.6`: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock/1.6.6/view#files/org.powermock/powermock/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock|1.6.6|jar))
  - `org.powermock:powermock-api-mockito:1.6.6`: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock-api-mockito/1.6.6/view#files/org.powermock/powermock-api-mockito/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock-api-mockito|1.6.6|jar))
  - `org.powermock:powermock-module-junit4:1.6.6`: (It may be available on one of: [bintray](https://bintray.com/org.powermock/maven/powermock-module-junit4/1.6.6/view#files/org.powermock/powermock-module-junit4/1.6.6) [mvn central](http://search.maven.org/#artifactdetails|org.powermock|powermock-module-junit4|1.6.6|jar))
  - `cglib:cglib:3.2.4`: (It may be available on one of: [bintray](https://bintray.com/cglib/maven/cglib/3.2.4/view#files/cglib/cglib/3.2.4) [mvn central](http://search.maven.org/#artifactdetails|cglib|cglib|3.2.4|jar))

**NOTE:** You may need to download any dependencies of the above dependencies in turn (i.e. the transitive dependencies)

--

> `This README.md file was hand-crafted with care utilising synapticloop`[`templar`](https://github.com/synapticloop/templar/)`->`[`documentr`](https://github.com/synapticloop/documentr/)

--
