<h1 align="center">Strucut</h1>
<p align="center">
   <img src="https://img.shields.io/maven-central/v/io.github.diareuse/strucut"/>
   <img src="https://img.shields.io/github/languages/code-size/diareuse/strucut"/>
</p>

Strucut is a JSON structure validator for required and/or optional properties. The main purpose is to ensure that your
serialized string is correct without using the model it was serialized from. This decoupling helps to detect errors or
renamed properties quickly and efficiently.

> ⚠️ Strucut is not designed to work outside test environment.

## Install

```groovy
dependencies {
    testImplementation "io.github.diareuse:strucut:1.0.0"
}
```

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

## Helpful errors

Assertions are always throws as a subclass of `StrucutAssertionError` and carry contextual information about the
structure that failed to verify.

```
strucut.StrucutAssertionError$ObjectAsProperty: Expected object <property> but got <1.0>
strucut.StrucutAssertionError$ValueMismatch: Expected property <property> to be <blow> but was <2.0>
strucut.StrucutAssertionError$PropertyMissing: Expected property <nested> in <[]>
strucut.StrucutAssertionError$ObjectInObjectMissing: Expected object <nested> in <[]>
```

## Notes

- Deserializing numbers defaults to Double type. Though checking number values for equality will be performed on similar
  types, assertion throwable will contain its Double value.