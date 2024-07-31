# Source the global variable
source "$(dirname "$0")/config.sh"
echo "Namespace in task.sh is $tekton_namespace"

#Git Clone
kubectl apply -f https://raw.githubusercontent.com/tektoncd/catalog/main/task/git-clone/0.9/git-clone.yaml --namespace $tekton_namespace

#Git version 
kubectl apply -f https://raw.githubusercontent.com/tektoncd/catalog/main/task/git-version/0.1/git-version.yaml --namespace $tekton_namespace