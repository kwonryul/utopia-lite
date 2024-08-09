(ns papercompany.utopia-lite.core
  (:require
    [clojure.tools.logging :as log]
    [integrant.core :as ig]
    [papercompany.utopia-lite.config :as config]
    [papercompany.utopia-lite.env :refer [defaults]]

    ;; Edges  
    [kit.edge.db.sql.conman]
    [kit.edge.db.sql.migratus]
    [kit.edge.db.postgres]
    [kit.edge.scheduling.quartz]
    [kit.edge.server.undertow]
    [papercompany.utopia-lite.web.handler]

    ;; Routes
    [papercompany.utopia-lite.web.routes.pages] 
    [papercompany.utopia-lite.web.routes.api]
    [papercompany.utopia-lite.web.routes.ws])
  (:gen-class))

;; log uncaught exceptions in threads
(Thread/setDefaultUncaughtExceptionHandler
  (reify Thread$UncaughtExceptionHandler
    (uncaughtException [_ thread ex]
      (log/error {:what :uncaught-exception
                  :exception ex
                  :where (str "Uncaught exception on" (.getName thread))}))))

(defonce system (atom nil))

(defn stop-app []
  ((or (:stop defaults) (fn [])))
  (some-> (deref system) (ig/halt!))
  (shutdown-agents))

(defn start-app [& [params]]
  ((or (:start params) (:start defaults) (fn [])))
  (->> (config/system-config (or (:opts params) (:opts defaults) {}))
       (ig/prep)
       (ig/init)
       (reset! system))
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app)))

(defn -main [& _]
  (start-app))
