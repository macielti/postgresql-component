(ns postgresql.component
  (:require [clojure.tools.logging :as log]
            [diehard.core :as dh]
            [integrant.core :as ig]
            [pg.core])
  (:import (org.pg Pool)))

(def PostgreSQLPool Pool)

(defmethod ig/init-key ::postgresql
  [_ {:keys [components]}]
  (log/info :starting ::postgresql)
  (let [postgresql-config (-> components :config :postgresql)
        pool (dh/with-retry {:max-retries 3
                             :backoff-ms  [1000 15000]
                             :retry-on    Exception
                             :on-retry    (fn [_ _]
                                            (log/warn :retrying-postgresql-pool))}
               (pg.core/pool postgresql-config))]
    pool))

(defmethod ig/halt-key! ::postgresql
  [_ pool]
  (log/info :stopping ::postgresql)
  (pg.core/close pool))
