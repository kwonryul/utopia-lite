(ns papercompany.utopia-lite.env
  (:require
   [papercompany.utopia-lite.prod-middleware :refer [wrap-prod]]
   [clojure.tools.logging :as log]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[utopia-lite starting]=-"))
   :start      (fn []
                 (log/info "\n-=[utopia-lite started successfully]=-"))
   :stop       (fn []
                 (log/info "\n-=[utopia-lite has shut down successfully]=-"))
   :middleware wrap-prod
   :opts       {:profile :prod}})
