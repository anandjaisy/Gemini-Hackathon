## Install podman

### Mac

`brew install docker`

Next, create and start your first Podman machine:

`docker machine init`
`docker machine start`

You can then verify the installation information using:

`docker info`

Podman Install

brew install docker-compose

Stop/Start docker

`docker machine start`

`docker machine stop`

Start the keycloak and postgres sql

`docker-compose -f keycloak-postgres-podman.yml up`

The port is modified in the podman compose file - so the access url is http://localhost:5001

