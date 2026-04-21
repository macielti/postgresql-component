(ns postgresql.migrations
  (:require [clojure.tools.logging :as log]
            [diehard.core :as dh]
            [integrant.core :as ig]
            [pg.migration.core :as migrations]))

(defmethod ig/init-key ::postgresql-migrations
  [_ {:keys [components]}]
  (log/info :starting ::postgresql-migrations)
  (let [postgresql-config (-> components :config :postgresql)
        migrations-config (-> components :config :postgresql-migrations)
        configuration (merge postgresql-config migrations-config)]
    (dh/with-retry {:max-retries 3
                    :backoff-ms  [1000 15000]
                    :retry-on    Exception
                    :on-retry    (fn [_ _]
                                   (log/warn :retrying-postgresql-migrations))}
      (migrations/migrate-all configuration))))

(defmethod ig/halt-key! ::postgresql-migrations
  [_ _]
  (log/info :stopping ::postgresql-migrations))
