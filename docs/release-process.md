# Release Process

Production promotion is tag-driven.

## How to release

1. Merge the desired code to `main`.
1. Merge the desired code to `master`.
2. Create and push a release tag:

```bash
git tag v0.1.0
git push origin v0.1.0
```

## What happens next

`release.yml`:

- builds and publishes the server image
- builds and publishes the web image
- uploads the desktop JAR to the GitHub Release

`promote-prod.yml`:

- updates the prod overlay with the tag image references
- commits that change back to `master`
- waits for Argo CD to complete deployment
- records the deployment in GitHub

## Where the promotion lives

The production deployment state is stored in Git at:

[image-patches.yaml](/Users/michaelbailey/Repos/Gym_Log_Book/deploy/overlays/prod/image-patches.yaml)

This is the canonical record of which release is meant to be running in production.

## Expected GitHub outputs

After a release:

- GHCR contains new `server` and `web` images
- GitHub Releases contains the desktop jar
- GitHub Deployments shows the production rollout
