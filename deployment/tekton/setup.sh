source ./config.sh
echo "Namespace in setup is $tekton_namespace"

# Create a fetebird gemini hackathon namespace
kubectl create namespace $tekton_namespace

# Install storage & persistance volume claim
kubectl apply -f ./storage/persistance-volume-claim.yaml --namespace $tekton_namespace

# Install secrets account
kubectl apply -f ./secret/git-secret.yaml --namespace $tekton_namespace

# Apply service account
kubectl apply -f ./service-account/git-service-account.yaml --namespace $tekton_namespace

#Apply task
sh task.sh
