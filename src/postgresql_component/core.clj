(ns postgresql-component.core
  (:require [integrant.core :as ig]
            [pg.pool]
            [clojure.tools.logging :as log]))

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
