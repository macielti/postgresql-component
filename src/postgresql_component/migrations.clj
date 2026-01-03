(ns postgresql-component.migrations
  (:require [pg.migration.core :as migrations]
            [clojure.tools.logging :as log]
            [integrant.core :as ig]))

(defmethod ig/init-key ::postgresql-migrations
  [_ {:keys [components]}]
  (log/info :starting ::postgresql-migrations)
  (let [postgresql-config (-> components :config :postgresql)
        migrations-config (-> components :config :postgresql-migrations)
        configuration (merge postgresql-config migrations-config)]
    (migrations/migrate-all configuration)))

(defmethod ig/halt-key! ::postgresql-migrations
  [_ pool]
  (log/info :stopping ::postgresql-migrations)
  (pg.core/close pool))
