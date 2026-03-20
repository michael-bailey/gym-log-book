# GitHub Actions

This repo uses three workflows.

## `ci.yml`

Runs on:

- pull requests
- pushes to `master`

It:

- runs Gradle validation
- builds the server fat jar
- builds the wasm production distribution
- builds the desktop fat jar
- renders both Kustomize overlays
- uploads the desktop jar as a workflow artifact

## `release.yml`

Runs on tags matching `v*`.

It:

- builds the desktop fat jar
- builds and pushes multi-arch GHCR images for:
    - server
    - web
- creates or updates the GitHub Release
- uploads the desktop jar as a release asset

## `promote-prod.yml`

Runs when a GitHub Release is published.

It:

- updates [image-patches.yaml](/Users/michaelbailey/Repos/Gym_Log_Book/deploy/overlays/prod/image-patches.yaml) with the
  release tag
- commits the prod manifest change back to `master`
- waits for Argo CD to report the prod app as synced and healthy
- uses the GitHub `production` environment so deployment history is visible in GitHub

## Required GitHub configuration

### Repository or environment secrets

- `ARGOCD_SERVER`
- `ARGOCD_AUTH_TOKEN`

### Repository or environment variables

- `PROD_HOSTNAME`
- `ARGOCD_INSECURE` set to `true` only if your Argo CD endpoint requires insecure CLI access

### Permissions

- GitHub Packages must allow publishing to `GHCR`
- the workflows need `contents: write` for release creation and prod manifest promotion

## Release tag format

Use tags like:

```text
v0.1.0
v0.1.1
```
