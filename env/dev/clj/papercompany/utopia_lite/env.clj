(ns papercompany.utopia-lite.env
  (:require
   [papercompany.utopia-lite.dev-middleware :refer [wrap-dev]]
   [clojure.tools.logging :as log]))

(def defaults
  {:init       (fn []
                 (log/info "\n-=[utopia-lite starting using the development or test profile]=-"))
   :start      (fn []
                 (log/info "\n-=[utopia-lite started successfully using the development or test profile]=-"))
   :stop       (fn []
                 (log/info "\n-=[utopia-lite has shut down successfully]=-"))
   :middleware wrap-dev
   :opts       {:profile       :dev
                :persist-data? true}})
