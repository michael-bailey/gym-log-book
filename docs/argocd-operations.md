# Argo CD Operations

Argo CD is the reconciler for both environments.

## Applications

- local app: `gym-log-book-local`
- prod app: `gym-log-book-prod`

## Common commands

```bash
argocd app get gym-log-book-local
argocd app get gym-log-book-prod

argocd app sync gym-log-book-local
argocd app sync gym-log-book-prod

argocd app wait gym-log-book-prod --sync --health
```

## Inspect rollouts

```bash
kubectl get rollouts -n gym-log-book
kubectl argo rollouts get rollout gym-log-book-web -n gym-log-book
kubectl argo rollouts get rollout gym-log-book-server -n gym-log-book
```

## Rollback options

Argo CD itself does not generate the rollback target. The source of truth is Git.

Rollback choices:

- revert the promotion commit in `master`
- revert the release tag and promote a new known-good version
- use `kubectl argo rollouts undo` for emergency traffic rollback, then reconcile Git immediately afterward

## Debugging

Useful checks:

```bash
kubectl get pods -n gym-log-book
kubectl describe rollout gym-log-book-server -n gym-log-book
kubectl describe rollout gym-log-book-web -n gym-log-book
kubectl logs deployment/argo-rollouts -n argo-rollouts
kubectl logs deployment/argocd-application-controller -n argocd
```
