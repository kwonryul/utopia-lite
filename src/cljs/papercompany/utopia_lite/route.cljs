(ns papercompany.utopia-lite.route
  (:require
   [papercompany.utopia-lite.history :as history]
   [goog.events :as events]
   [goog.history.EventType :as EventType]))

(defn route [uri]
  (.setToken history/history uri))

(defn route-back []
  (.back js/history))

(defn route-forward []
  (.forward js/history))

