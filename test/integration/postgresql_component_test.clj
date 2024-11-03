(ns postgresql-component-test
  (:require [clojure.test :refer [is testing]]
            [integrant.core :as ig]
            [java-time.api :as jt]
            [matcher-combinators.test :refer [match?]]
            [pg.core :as pg]
            [pg.pool]
            [postgresql-component.core :as component.postgresql]
            [schema.test :as s])
  (:import (org.pg Pool)))

(def config {::component.postgresql/postgresql {:components {:config {:postgresql {:host     "localhost"
                                                                                   :port     5432
                                                                                   :user     "postgres"
                                                                                   :password "root"
                                                                                   :database "postgres-db"}}}}})

(s/deftest postgresql-integrant-component-test
  (let [system (ig/init config)
        pool (:postgresql-component.core/postgresql system)
        now (jt/local-date)]

    (testing "Should be able to init a system with PostgreSQL component"
      (is (match? {:postgresql-component.core/postgresql #(= (type %) Pool)}
                  system)))

    (testing "Should be able to use the initiated PostgreSQL component to perform database operations"
      (is (match? [{:apelido    "brunão"
                    :nascimento now
                    :nome       "nascimento"}]
                  (pg.pool/with-connection [conn pool]
                    (pg/execute conn
                                "INSERT INTO pessoa (apelido, nome, nascimento) VALUES ($1, $2, $3)
                                 returning *"
                                {:params ["brunão" "nascimento" now]})))))

    (testing "The System was stopped"
      (is (nil? (ig/halt! system))))))
