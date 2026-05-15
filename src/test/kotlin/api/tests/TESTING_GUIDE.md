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

## Design principles

- Keep each test focused on one business flow.
- Validate both happy path and failure behavior.
- Always clean up created data when feasible.
- Add unit tests for test helpers to prevent silent test infrastructure regressions.
- Add contract tests for API configuration constants to catch accidental endpoint drift.
