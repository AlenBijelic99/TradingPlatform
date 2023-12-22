# Labo3

In this lab the focus is on developing a multi-tier application, including frontend, backend, and database components. The primary goal of this lab is to deploy a fully functional application. A *new* technology not explored in previous assignments or in the course has to be chosen and employed in the application.

Here are the mandatory deliverables of the lab:
* The initial report
* The whole application code and data
* A presentation for the *new* technology chosen

All the above deliverables are delivered by adding them to the same repository for each deadline communicated in course.
The presentation slides are to be added to the repository and will be presented in course.

## Initial report requirements
* A summary describing the purpose and business domain of the application.
* Several (i.e. between 3 and 10) user stories that the application will provide. Template: 
“As [a persona], I want [to perform this action] so that [I can accomplish this goal].”
* The chosen *new* technology that will be employed and presented by the group at the end of the lab.
* The following diagrams:
    * Architecture, including main components and their relations.
    * Data model of the domain, the focus is on the main entities and relations. The diagram will likely evolve during the implementation.

## Application requirements
* The application has to be a multi-tiers application with a frontend, backend and database.
* The backend is implemented in Java and Maven is used.
* JMS (Java Message Service) messaging has to be used for at least one feature within the backend.
* A *new* technology is meaningfully used in the application.
* The application must offer several cases of data reads. Different entities are read, or same entities are read differently.
* The application must offer several cases of data write. Different entities are written, or same entities are written differently.
* The application has to be deployed to a Kubernetes cluster (i.e. minikube)
* Unit tests and integration tests for core features within the perimeter of the backend application.


## Repository requirements
* A README file is included that describes the project.
* The README includes instructions how to run locally the whole application for development purposes.
* The README includes instructions how to deploy the whole application and its dependencies to a local Kubernetes cluster (i.e. minikube).
* The repository is self-contained and assumes little about the developer host system. Any required dependency has to be described in the README. The following elements can be expected from the host system:
    * Java 17
    * Maven
    * Minikube and kubectl
    * Container engine (Docker CLI compatible)
* Manifests or integration to deploy the whole application and its dependencies (e.g. database, JMS broker) to a Kubernetes cluster (i.e. the target is minikube).

## Presentation requirements
The presentation of the *new* technology must include:
* A brief description of the goal of the tool.
* The pros and cons of the tool as experienced during its usage.
* A feedback from the experience of using the tool.
* Demonstrate how it was concretely integrated into the application, with examples or demo.

## *New* technology
The goal is to explore a *new* technology and achieve concrete results with it. The technology must be employed meaningfully in the final delivered application.

Examples of technologies that could be selected (in no particular order):
* https://grpc.io/
* https://hilla.dev/
* https://www.thymeleaf.org/
* https://javalin.io/
* https://hotwired.dev/
* https://helidon.io
* https://www.jooq.org/
* https://www.jhipster.tech/
* https://hasura.io/
* https://lit.dev/
* https://projectlombok.org/
* https://site.mockito.org/
* https://wiremock.org/
* https://cucumber.io/
* https://opentelemetry.io/
