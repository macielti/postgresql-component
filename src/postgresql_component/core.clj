(ns postgresql-component.core
  (:require [clojure.tools.logging :as log]
            [integrant.core :as ig]
            [pg.pool]))

(defmethod ig/init-key ::postgresql
  [_ {:keys [components]}]
  (log/info :starting ::postgresql)
  (let [postgresql-config (-> components :config :postgresql)
        pool (pg.pool/pool postgresql-config)]
    pool))

(defmethod ig/halt-key! ::postgresql
  [_ pool]
  (log/info :stopping ::postgresql)
  (pg.pool/close pool))
