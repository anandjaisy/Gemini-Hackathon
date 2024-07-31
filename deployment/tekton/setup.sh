source ./config.sh
echo "Namespace in setup is $tekton_namespace"

# Create a fetebird gemini hackathon namespace
kubectl create namespace $tekton_namespace

# Install storage & persistance volume claim
kubectl apply -f ./storage/persistance-volume-claim.yml --namespace $tekton_namespace

# Install secrets account
kubectl apply -f ./secret/git-secret.yml --namespace $tekton_namespace

# Apply service account
kubectl apply -f ./service-account/git-service-account.yml --namespace $tekton_namespace

#Apply task
sh task.sh

# Apply pipeline
kubectl apply -f ./pipeline/pipeline.yml --namespace $tekton_namespace

#Apply pipeline-run
kubectl apply -f ./pipeline/pipeline-run.yml --namespace $tekton_namespace
