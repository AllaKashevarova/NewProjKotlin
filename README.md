# NewProjKotlin

Kotlin JVM project with automated API tests against the [Petstore Swagger API](https://petstore.swagger.io/).

## Requirements

- JDK 21
- Gradle (wrapper included)

## Run tests

```bash
./gradlew test
```

Run only fast unit tests (no live API calls):

```bash
./gradlew test --tests "api.tests.PetStatusParserTest" --tests "api.tests.OrderStatusRulesTest" --tests "api.tests.UserCredentialsValidatorTest" --tests "api.tests.PetListSummaryTest"
```

## Project layout

| Path | Purpose |
| --- | --- |
| `src/main/kotlin/` | Small sample app entrypoint |
| `src/test/kotlin/api/client/` | Ktor HTTP clients for Pet, Store, and User APIs |
| `src/test/kotlin/api/model/` | Serializable API models |
| `src/test/kotlin/api/config/` | Base URL and endpoint path constants |
| `src/test/kotlin/api/tests/` | Flow tests, factories, and test utilities |

## Documentation

See [src/test/kotlin/api/tests/TESTING_GUIDE.md](src/test/kotlin/api/tests/TESTING_GUIDE.md) for test conventions, utilities, and how to add new coverage.

## Application

```bash
./gradlew run
```
