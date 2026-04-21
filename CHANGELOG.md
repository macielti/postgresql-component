# Change Log

All notable changes to this project will be documented in this file. This change log follows the conventions
of [keepachangelog.com](http://keepachangelog.com/).

## 3.2.0 - 2026-04-21

### Added

- Retry logic via `diehard` when initializing the PostgreSQL pool and running migrations, so the components tolerate the database being briefly unavailable during container startup. Uses exponential backoff (1s → capped at 15s) with up to 3 retries.

## 3.1.0 - 2026-04-19

### Added

- `postgresql.component/PostgreSQLPool` schema — Prismatic schema alias for `org.pg.Pool`, to validate pool references in consumer schemas.

## 3.0.0 - 2026-02-08

### Changed

- Reset version counting to a fresh start.
- Renamed project to `net.clojars.macielti/postgresql`. 
