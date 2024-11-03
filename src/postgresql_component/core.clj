(ns postgresql-component.core
  (:require [integrant.core :as ig]
            [pg.migration.core :as mig]
            [taoensso.timbre :as log]
            [pg.pool]))

(defmethod ig/init-key ::postgresql
  [_ {:keys [components]}]
  (log/info :starting ::postgresql)
  (let [postgresql-config (-> components :config :postgresql)
        pool (pg.pool/pool postgresql-config)]
    (mig/migrate-all postgresql-config)
    pool))

(defmethod ig/halt-key! ::postgresql
  [_ pool]
  (log/info :stopping ::postgresql)
  (pg.pool/close pool))
