# Strucut

Validates JSON structures for required and/or optional properties.

## Usage

Given we have following json to verify:

```json
{
  "id": 1,
  "name": {
    "first": "",
    "last": ""
  },
  "image": {
    "small": {
      "width": 100,
      "height": 100,
      "url": ""
    }
  }
}
```

In Java we would verify the structure like so:

```java
public class MyTest {

    @Test
    public void testResponseStructure() {
        var scope = structureScopeOf(image -> image
                .prop("width")
                .prop("height")
                .prop("url")
        );
        new Strucut(root -> root
                .prop("id", 1)
                .prop("name", name -> name
                        .prop("first")
                        .prop("last")
                )
                .prop("image", image -> image
                        .propOpt("large", imageStruct)
                        .propOpt("medium", imageStruct)
                        .propOpt("small", imageStruct)
                )
        ).verify(myJsonInString);
    }

}
```

In Kotlin we would verify the structure like so:

```kotlin
@Test
fun testResponseStructure() {
    val imageStruct = structureScopeOf {
        prop("width")
        prop("height")
        prop("url")
    }
    verifyStructureOf(myJsonInString) {
        prop("id", 1)
        prop("name") {
            prop("first")
            prop("last")
        }
        prop("image") {
            propOpt("large", imageStruct)
            propOpt("medium", imageStruct)
            propOpt("small", imageStruct)
        }
    }
}
```