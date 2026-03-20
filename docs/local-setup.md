# Local Setup

This environment targets Docker Desktop Kubernetes and is intended for developer machines.

## Prerequisites

- Docker Desktop with Kubernetes enabled
- `kubectl`
- access to this repo locally

## Install ingress-nginx

```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml
kubectl rollout status deployment/ingress-nginx-controller -n ingress-nginx
```

## Install Argo CD

```bash
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
kubectl rollout status deployment/argocd-server -n argocd
```

## Install Argo Rollouts

```bash
kubectl create namespace argo-rollouts --dry-run=client -o yaml | kubectl apply -f -
kubectl apply -n argo-rollouts -f https://github.com/argoproj/argo-rollouts/releases/latest/download/install.yaml
kubectl rollout status deployment/argo-rollouts -n argo-rollouts
```

Optional CLI plugin:

```bash
brew install argoproj/tap/kubectl-argo-rollouts
```

## Build local images

From the repo root:

```bash
docker build -f docker/server.Dockerfile -t gym-log-book-server:dev .
docker build -f docker/web.Dockerfile -t gym-log-book-web:dev .
```

These image names match the local Kustomize overlay.

## Bootstrap Argo CD

1. Edit [local-app.yaml](/Users/michaelbailey/Repos/Gym_Log_Book/deploy/argocd/local-app.yaml) and set
   `spec.source.repoURL` to your GitHub repo URL.
2. Apply the app:

```bash
kubectl apply -f deploy/argocd/local-app.yaml
```

## Access the app

The local ingress host is:

`http://gym.127.0.0.1.sslip.io`

The local ingress routes:

- `/` to the wasm client
- `/rpc/counter` to the server

## Useful checks

```bash
kubectl get applications -n argocd
kubectl get rollouts -n gym-log-book
kubectl get ingress -n gym-log-book
kubectl argo rollouts get rollout gym-log-book-web -n gym-log-book
kubectl argo rollouts get rollout gym-log-book-server -n gym-log-book
```
