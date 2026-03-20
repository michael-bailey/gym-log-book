# DevOps Overview

This repo uses a same-repo GitOps setup:

- application code, CI/CD workflows, and Kubernetes manifests live together
- GitHub Actions builds and publishes artifacts
- Argo CD watches the repo and reconciles cluster state
- Argo Rollouts manages application rollouts

## Environments

### Local

- target: Docker Desktop Kubernetes
- ingress: `ingress-nginx`
- rollout strategy: blue/green
- images: locally built into the Docker Desktop daemon

### Production

- target: Raspberry Pi `k3s`
- ingress: existing `Traefik v3`
- TLS: existing `cert-manager`
- rollout strategy: canary with Traefik-managed weighted traffic
- images: GHCR multi-arch images built from GitHub Actions

## Delivery Model

The application is exposed through a single host:

- `/` serves the wasm web application
- `/rpc/counter` serves the server websocket RPC endpoint

This keeps the web client same-origin with the API and avoids CORS.

## Repo Layout

- `docker/`: container build definitions
- `deploy/base`: shared Kubernetes manifests
- `deploy/overlays/local`: Docker Desktop overlay
- `deploy/overlays/prod`: production overlay
- `deploy/argocd`: Argo CD `Application` resources
- `.github/workflows`: CI, release, and promotion workflows

## Promotion Model

- pull requests and pushes to `master` run CI only
- tags matching `v*` publish release artifacts and GHCR images
- GitHub Release publication triggers prod promotion
- prod promotion updates the prod overlay in Git
- Argo CD auto-syncs the cluster from that Git change
