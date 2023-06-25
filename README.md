<img src="https://cdn.discordapp.com/attachments/1058925155021226055/1122645618611990618/Framework.png">

## Implementation of the framework

### Gradle
```kotlin
repositories {
    maven ("https://jitpack.io")
}
```

```kotlin
dependencies {
    implementation ("com.github.NoximityMC:Framework:VERSION")
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url> 
    </repository>
</repositories>
```

```xml
<dependency>
    <groupId>com.github.NoximityMC</groupId>
    <artifactId>Framework</artifactId>
    <version>VERSION</version>
</dependency>
```


