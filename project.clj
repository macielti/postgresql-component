(defproject net.clojars.macielti/postgresql-component "2.1.1"

  :description "PostgreSQL Component"

  :url "https://github.com/macielti/postgresql-component"

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :plugins [[com.github.clojure-lsp/lein-clojure-lsp "1.4.13"]
            [com.github.liquidz/antq "RELEASE"]
            [lein-shell "0.5.0"]]

  :dependencies [[org.clojure/clojure "1.12.0"]
                 [io.pedestal/pedestal.interceptor "0.7.2"]
                 [com.github.igrishaev/pg2-migration "0.1.19"]
                 [com.github.igrishaev/pg2-core "0.1.19"]
                 [nubank/matcher-combinators "3.9.1"]
                 [com.taoensso/timbre "6.6.1"]
                 [clojure.java-time "1.4.2"]
                 [prismatic/schema "1.4.1"]
                 [integrant "0.13.1"]
                 [hashp "0.2.2"]]

  :profiles {:dev {:resource-paths ^:replace ["test/resources"]

                   :test-paths     ^:replace ["test/unit" "test/integration" "test/helpers"]

                   :dependencies   [[com.github.igrishaev/pg2-migration "0.1.18"]]

                   :injections     [(require 'hashp.core)]

                   :aliases        {"clean-ns"         ["clojure-lsp" "clean-ns" "--dry"] ;; check if namespaces are clean
                                    "format"           ["clojure-lsp" "format" "--dry"] ;; check if namespaces are formatted
                                    "diagnostics"      ["clojure-lsp" "diagnostics"]
                                    "lint"             ["do" ["clean-ns"] ["format"] ["diagnostics"]]
                                    "clean-ns-fix"     ["clojure-lsp" "clean-ns"]
                                    "format-fix"       ["clojure-lsp" "format"]
                                    "lint-fix"         ["do" ["clean-ns-fix"] ["format-fix"]]
                                    "migrate-all"      ["run" "-m" "pg.migration.cli" "-c" "resources/migration.config.edn" "migrate" "--all"]
                                    "auto-test"        ["do"
                                                        ["shell" "docker-compose" "-f" "test/resources/docker-compose.yml" "up" "-d"]
                                                        ["migrate-all"]
                                                        ["test"]
                                                        ["shell" "docker-compose" "-f" "test/resources/docker-compose.yml" "down"]]}
                   :repl-options   {:init-ns postgresql-component.core}}}

  :repl-options {:init-ns postgresql-component.core}

  :resource-paths ["resources"])
