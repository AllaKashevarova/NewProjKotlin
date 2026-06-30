# Project notes for Claude Code

## Pushing / opening PRs — auth workaround (IMPORTANT)

This repo is owned by the GitHub account **`AllaKashevarova`**, but the local `gh`
CLI is logged in as a **different** account (`AlaKashavarova`) that has **no write
access**. The user's `~/.gitconfig` routes all github.com auth through
`gh auth git-credential`, so a plain `git push` sends the wrong (read-only) token
and fails with **HTTP 403 / "Permission ... denied"**.

The macOS keychain already holds the correct owner credential. The fix is to push
while disabling the gh credential helper for github.com, so git falls back to the
keychain (owner) credential:

```bash
git -c credential.https://github.com.helper= push -u origin <branch>
```

This repo's **local** git config already has the override persisted
(`credential.https://github.com.helper=` empty), so a normal `git push` from this
repo should now work. If a 403 ever returns, use the explicit `-c` flag above.

### Creating the PR with the owner credential
`gh pr create` uses gh's own (read-only) token and will fail. Pull the owner token
from the keychain and pass it via `GH_TOKEN` (never echo it):

```bash
TOKEN=$(printf "protocol=https\nhost=github.com\n\n" \
  | git -c credential.https://github.com.helper= credential fill 2>/dev/null \
  | sed -n 's/^password=//p')
GH_TOKEN="$TOKEN" gh pr create --repo AllaKashevarova/NewProjKotlin --base main --head <branch> --title "..." --body "..."
```

## Commit / PR conventions
- Do **not** add `Co-Authored-By: Claude` (or any Claude/Anthropic attribution)
  to commits or PR bodies.

## Test-utility pattern
Test helpers live in `src/test/kotlin/api/tests/` as a `package api.tests` object
(e.g. `PetTagNormalizer`, `PetStatusParser`) paired with a JUnit 5 test class
(`<Name>Test.kt`) using `kotlin.test` assertions. Model classes are in
`src/test/kotlin/api/model/`.

## Build note
Gradle must run on **JDK 21** (Corretto 21 at
`/Users/alakashavarava/Library/Java/JavaVirtualMachines/corretto-21.0.6/Contents/Home`).
The Kotlin 1.9.22 compiler crashes on the system default JDK 25
(`IllegalArgumentException: 25.0.2`). Prefix builds with
`JAVA_HOME=<jdk21> ./gradlew ...`.
