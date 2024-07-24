source ./config.sh
echo "Namespace in setup is $tekton_namespace"

# Delete fetebird common namespace
kubectl delete namespace $tekton_namespace