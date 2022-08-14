# Multiple Datasources

## Motivation

This small demo project shows how one can configure multiple Hikari `DataSource`s that

- share a common "base" configuration, but still
- expose custom settings (`poolName` in this case).

## How to Run

- Run `./mvnw test -Dspring.profiles.active=prog` for trying out the setup
  using `c.r.m.c.ProgrammaticDataSourceConfiguration`.
- Run `./mvnw test -Dspring.profiles.active=yaml-simple` for trying out the setup
  using `c.r.m.c.SimpleYamlAnchorDataSourceConfiguration`.
- Run `./mvnw test -Dspring.profiles.active=yaml-structured` for trying out the setup
  using `c.r.m.c.StructuredYamlAnchorDataSourceConfiguration`.
