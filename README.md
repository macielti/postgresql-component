![Compatible with GraalVM](https://img.shields.io/badge/compatible_with-GraalVM-green)

# PostgreSQL Component

PostgreSQL Component is a library that provides a PostgreSQL component for use with the Integrant library.

## Dealing with migrations

This library does not provide any integration with migrations tooling. You should handle the migrations outside the
application. You should use [pg2 migrations tooling](https://github.com/igrishaev/pg2/blob/master/docs/migrations.md) or any other tooling that you prefer.

## Executing tests

To run the tests for this library, you need to have a Docker environment running.

```lein run auto-test``` or ```lein run auto-test-remote ```

## License

Copyright © 2024 Bruno do Nascimento Maciel

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
