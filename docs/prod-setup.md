# Production Setup

This environment targets the Raspberry Pi `k3s` cluster and assumes:

- `k3s` is already running
- `Traefik v3` is already the ingress controller
- `cert-manager` is already installed

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

### Traefik API group for Rollouts

Note: Argo Rollouts currently expects Traefik CRDs in the `traefik.containo.us` group. Even if you use Traefik v3, you
must install the legacy CRDs for the `TraefikService` resources to be found by the Rollout controller.

```bash
# Install Traefik v2 CRDs (backwards compatibility)
kubectl apply -f https://raw.githubusercontent.com/traefik/traefik/v2.10/docs/content/reference/dynamic-configuration/kubernetes-crd-definition-v1.yaml
```

Optional CLI plugin:

```bash
brew install argoproj/tap/kubectl-argo-rollouts
```

## Prepare GHCR pull access

If the GHCR packages are private, create an image pull secret in the target namespace:

```bash
kubectl create namespace gym-log-book --dry-run=client -o yaml | kubectl apply -f -
kubectl create secret docker-registry ghcr-pull \
  --namespace gym-log-book \
  --docker-server=ghcr.io \
  --docker-username=YOUR_GITHUB_USERNAME \
  --docker-password=YOUR_GITHUB_TOKEN \
  --docker-email=you@example.com
```

If you need that secret attached to pods by default, patch the service account or extend the rollout templates.

## Configure production host and TLS

Update these files before bootstrapping prod:

- [certificate.yaml](/Users/michaelbailey/Repos/Gym_Log_Book/deploy/overlays/prod/certificate.yaml)
- [ingressroute.yaml](/Users/michaelbailey/Repos/Gym_Log_Book/deploy/overlays/prod/ingressroute.yaml)
- [prod-app.yaml](/Users/michaelbailey/Repos/Gym_Log_Book/deploy/argocd/prod-app.yaml)

Required edits:

- change `gym.example.com` to the real hostname
- change the cert-manager issuer name if you do not use `letsencrypt-prod`
- set the Argo CD application `repoURL`

## Bootstrap Argo CD

```bash
kubectl apply -f deploy/argocd/prod-app.yaml
```

## DNS and networking

You need:

- a public DNS record pointing at your cluster ingress
- router forwarding for ports `80` and `443`
- Traefik entrypoints configured for `websecure`

## Useful checks

```bash
kubectl get applications -n argocd
kubectl get ingressroute,traefikservice,certificate -n gym-log-book
kubectl get rollouts -n gym-log-book
kubectl argo rollouts get rollout gym-log-book-web -n gym-log-book
kubectl argo rollouts get rollout gym-log-book-server -n gym-log-book
```
