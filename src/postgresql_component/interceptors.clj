(ns postgresql-component.interceptors
  (:require [io.pedestal.interceptor :as pedestal.interceptor]
            [pg.core :as pg]
            [schema.core :as s]))

(s/defn http-friendly-exception
  "https://www.baeldung.com/rest-api-error-handling-best-practices"
  [status-code :- s/Int
   error :- s/Str
   message :- s/Str
   detail :- s/Any]
  (throw (ex-info (format "%s - %s" status-code error)
                  {:status  status-code
                   :error   error
                   :message message
                   :detail  detail})))

(s/defn resource-existence-check-interceptor
  "resource-identifier-fn -> function used to extract param used to query the resource, must receive a context as argument.
  sql-query -> postgresql query that will try to find the resource using the resource identifier"
  [resource-identifier-fn
   sql-query]
  (pedestal.interceptor/interceptor {:name  ::resource-existence-check-interceptor
                                     :enter (fn [{{:keys [components]} :request :as context}]
                                              (let [pool (:postgresql components)
                                                    resource-identifier (resource-identifier-fn context)
                                                    resource (-> (pg/with-connection [database-conn pool]
                                                                   (pg/execute database-conn sql-query {:params [resource-identifier]})) first)]
                                                (when-not resource
                                                  (http-friendly-exception 404
                                                                           "resource-not-found"
                                                                           "Resource could not be found"
                                                                           "Not Found")))
                                              context)}))
