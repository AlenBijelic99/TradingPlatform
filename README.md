# Trading platform

This project is a trading platform for cryptocurrencies using Hilla (Spring Boot and React). It allows users to buy and
sell listed cryptocurrencies, and to consult their portfolio.
The dashboard displays the current price of the cryptocurrencies that is retrieved from a JMS queue every 10 seconds.
We use the Coinbase API to get the current price of the cryptocurrencies.

## Running the database

The project uses a PostgreSQL database. To run it, you can use the provided `docker-compose.yml` file. Run the following
command in the root directory of the project:

```bash
docker-compose up
```

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project.

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/myapp-1.0-SNAPSHOT.jar` (NOTE, replace
`myapp-1.0-SNAPSHOT.jar` with the name of your jar).

## Project structure

<table style="width:100%; text-align: left;">
  <tr><th>Directory</th><th>Description</th></tr>
  <tr><td><code>frontend/</code></td><td>Client-side source directory</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>index.html</code></td><td>HTML template</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>index.ts</code></td><td>Frontend 
entrypoint, bootstraps a React application</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>routes.tsx</code></td><td>React Router routes definition</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>MainLayout.tsx</code></td><td>Main 
layout component, contains the navigation menu, uses <a href="https://hilla.dev/docs/react/components/app-layout">
App Layout</a></td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>views/</code></td><td>UI view 
components</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>themes/</code></td><td>Custom  
CSS styles</td></tr>
  <tr><td><code>src/main/java/ch/heigvd/application</code></td><td>Server-side 
source directory, contains the server-side Java views</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>Application.java</code></td><td>Server entry-point</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>data/</code></td><td>Data definitions (DTOs, entities and repositories)</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>security/</code></td><td>App security (authentication & authorization)</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>services/</code></td><td>Services (JMS and endpoints)</td></tr>
  <tr><td><code>test/java/ch/heigvd/application/</code></td><td>Server-side test directory</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>data/</code></td><td>Entities tests</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>services/</code></td><td>CryptoPrice and JmsPrice tests</td></tr>
</table>

## Useful links

- Read the documentation at [hilla.dev/docs](https://hilla.dev/docs/).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/hilla) or join
  our [Discord channel](https://discord.gg/MYFq5RTbBn).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin/hilla).
