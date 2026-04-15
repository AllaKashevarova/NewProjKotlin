# API Test Module Guide

This folder contains integration-style automated tests for Petstore API workflows.

## What is covered

- Pet lifecycle and availability flows
- Store order placement and validation flows
- User account lifecycle and profile update flows

## Supporting test utilities

- `TestDataFactory`, `PetTestDataFactory`, `UserTestDataFactory`:
  build realistic, unique payloads for reliable parallel runs.
- `ApiAssertions`:
  centralize common field-level checks to keep tests readable.
- `SessionResponseParser`:
  extracts login session token from API response text.

## Design principles

- Keep each test focused on one business flow.
- Validate both happy path and failure behavior.
- Always clean up created data when feasible.
