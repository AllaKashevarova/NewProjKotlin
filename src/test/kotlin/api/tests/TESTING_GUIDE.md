# API Test Module Guide

This folder contains integration-style automated tests for Petstore API workflows.

## What is covered

- Pet lifecycle and availability flows
- Store order placement and validation flows
- User account lifecycle and profile update flows
- Utility/unit tests for test data builders and response parsers
- Utility/unit tests for analytics helpers and API config contracts
- Store inventory analytics flow against live Petstore inventory
- Pet name validation, order pricing helpers, and inventory report builders
- Pet status parsing, order status transition rules, and credential validation
- Pet list summaries and store order budget flow checks
- Pet status updates, sold-pet discovery, and user session token extraction flows
- URL builder and pet list matcher helpers for readable assertions
- Order quantity validation, HTTP status checks, inventory low-stock alerts, and photo URL validation
- Pending pet flow, quantity-based store orders, and user creation HTTP status checks

## Supporting test utilities

- `TestDataFactory`, `PetTestDataFactory`, `UserTestDataFactory`:
  build realistic, unique payloads for reliable parallel runs.
- `ApiAssertions`:
  centralize common field-level checks to keep tests readable.
- `SessionResponseParser`:
  extracts login session token from API response text.
- `InventoryAnalytics` / `InventoryReportBuilder`:
  provides safe inventory calculations and summarized reports for unit and flow tests.
- `PetNameValidator`:
  keeps generated pet names API-safe before requests are sent.
- `OrderPricing`:
  calculates order line totals in cents for budget checks in store scenarios.
- `PetStatusParser` / `PetListSummaryBuilder`:
  normalize pet statuses and summarize lists returned by find-by-status calls.
- `OrderStatusRules`:
  validates order status values and allowed lifecycle transitions.
- `UserCredentialsValidator`:
  guards generated usernames and passwords before user API flows.
- `ApiUrlBuilder`:
  composes base URLs and substitutes `{param}` placeholders in endpoint templates.
- `PetMatchers`:
  filters pets by status, name prefix, and photo presence in list assertions.
- `UserSessionHelper`:
  extracts session tokens from login responses for user auth flow tests.
- `OrderQuantityValidator`:
  validates store order quantities before live API placement.
- `HttpStatusAssertions`:
  centralizes 2xx and exact HTTP status checks for client responses.
- `InventoryThresholdChecker`:
  flags inventory statuses at or below a configurable stock threshold.
- `PetPhotoUrlValidator`:
  ensures generated pet payloads include valid HTTP(S) photo URLs.

## Test layers

| Layer | Examples | Needs network |
| --- | --- | --- |
| Unit | `*ParserTest`, `*RulesTest`, `*SummaryTest` | No |
| Contract | `ApiConfigContractTest` | No |
| Flow / integration | `*FlowTest`, `PetApiTest` | Yes |

## Design principles

- Keep each test focused on one business flow.
- Validate both happy path and failure behavior.
- Always clean up created data when feasible.
- Add unit tests for test helpers to prevent silent test infrastructure regressions.
- Add contract tests for API configuration constants to catch accidental endpoint drift.
