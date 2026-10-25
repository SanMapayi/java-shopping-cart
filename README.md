# Java Shopping Cart and Inventory

A console-based Java exercise modelling products, inventory, reserved quantities, and shopping carts.

## What is implemented

Product records, generic inventory types, cart totals, collection operations, and date-based cart behaviour.

## Build and run

Use JDK 23 or newer and Maven from the repository root. The Maven file is the source of truth for any lower compiler target.

~~~sh
mvn -B compile
java -cp target/classes Main
~~~

## Scope and limitations

Lombok getters/setters are configured explicitly as an annotation processor in Maven. This is a single-process learning example; it does not provide transactional inventory guarantees, payment processing, or concurrent reservation safety.

## Verification

See [verification notes](docs/verification.md) for the exact checks performed. A compile result is not an end-to-end test result.

Local configuration, credentials, browser drivers, generated reports, IDE metadata, and personal data are excluded from new source commits. Existing source notices are retained.