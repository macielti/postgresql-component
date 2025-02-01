(ns postgresql-component.core
  (:require [clojure.tools.logging :as log]
            [integrant.core :as ig]
            [pg.core]))

(defmethod ig/init-key ::postgresql
  [_ {:keys [components]}]
  (log/info :starting ::postgresql)
  (let [postgresql-config (-> components :config :postgresql)
        pool (pg.core/pool postgresql-config)]
    pool))

(defmethod ig/halt-key! ::postgresql
  [_ pool]
  (log/info :stopping ::postgresql)
  (pg.core/close pool))
