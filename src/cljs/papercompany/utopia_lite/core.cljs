(ns papercompany.utopia-lite.core
    (:require
     [papercompany.utopia-lite.doms.main.home :as home]
     [papercompany.utopia-lite.doms.main.login :as login]
     [papercompany.utopia-lite.doms.main.enroll :as enroll]
     [papercompany.utopia-lite.doms.main.userinfo :as userinfo]
     [papercompany.utopia-lite.doms.main.notifications :as notifications]
     [papercompany.utopia-lite.doms.main.contracts :as contracts]
     [papercompany.utopia-lite.doms.main.contract :as contract]
     [papercompany.utopia-lite.doms.main.userprofile :as userprofile]
     [papercompany.utopia-lite.doms.main.userplaylist :as userplaylist]
     [papercompany.utopia-lite.doms.main.userplaylists :as userplaylists]
     [papercompany.utopia-lite.doms.main.userportfolio :as userportfolio]
     [papercompany.utopia-lite.doms.main.usercoin1 :as usercoin1]
     [papercompany.utopia-lite.doms.main.usercoin3list :as usercoin3list]
     [papercompany.utopia-lite.doms.main.usercoin8list :as usercoin8list]
     [papercompany.utopia-lite.doms.main.usershare1list1 :as usershare1list1]
     [papercompany.utopia-lite.doms.main.usershare1list2 :as usershare1list2]
     [papercompany.utopia-lite.doms.main.usershare1list3 :as usershare1list3]
     [papercompany.utopia-lite.doms.main.usershare1list4 :as usershare1list4]
     [papercompany.utopia-lite.doms.main.usershare1list5 :as usershare1list5]
     [papercompany.utopia-lite.doms.main.usershare1list6 :as usershare1list6]
     [papercompany.utopia-lite.doms.main.usershare3list :as usershare3list]
     [papercompany.utopia-lite.doms.main.usershare8list :as usershare8list]
     [papercompany.utopia-lite.doms.main.userrecord3list :as userrecord3list]
     [papercompany.utopia-lite.doms.main.userrecord8list :as userrecord8list]
     [papercompany.utopia-lite.doms.main.crane :as crane]
     [papercompany.utopia-lite.doms.main.algebra1 :as algebra1]
     [papercompany.utopia-lite.doms.main.coin1plaza :as coin1plaza]
     [papercompany.utopia-lite.doms.main.share1plaza :as share1plaza]
     [papercompany.utopia-lite.doms.main.blossom :as blossom]
     [papercompany.utopia-lite.doms.main.analysis3 :as analysis3]
     [papercompany.utopia-lite.doms.main.coin3plaza :as coin3plaza]
     [papercompany.utopia-lite.doms.main.share3plaza :as share3plaza]
     [papercompany.utopia-lite.doms.main.moon :as moon]
     [papercompany.utopia-lite.doms.main.analysis8 :as analysis8]
     [papercompany.utopia-lite.doms.main.coin8plaza :as coin8plaza]
     [papercompany.utopia-lite.doms.main.share8plaza :as share8plaza]
     [papercompany.utopia-lite.doms.main.history :as history]
     [papercompany.utopia-lite.doms.main :as main]
     [papercompany.utopia-lite.history :as history']
     [reagent.core :as reagent]
     [reagent.dom :as dom]
     [reitit.core :as reitit]
     [goog.events :as events]
     [goog.history.EventType :as HistoryEventType]
     [clojure.string :as string]))

(set! *warn-on-infer* false)

;; -------------------------
;; States

(def route (reagent/atom nil))
(def loaded (reagent/atom [false false]))

;; -------------------------
;; Routes

(def main-route
  {:home home/dom
   :login login/dom
   :enroll enroll/dom
   :userinfo userinfo/dom
   :notifications notifications/dom
   :contracts contracts/dom
   :contract contract/dom
   :userprofile userprofile/dom
   :userplaylist userplaylist/dom
   :userplaylists userplaylists/dom
   :userportfolio userportfolio/dom
   :usercoin1 usercoin1/dom
   :usercoin3list usercoin3list/dom
   :usercoin8list usercoin8list/dom
   :usershare1list1 usershare1list1/dom
   :usershare1list2 usershare1list2/dom
   :usershare1list3 usershare1list3/dom
   :usershare1list4 usershare1list4/dom
   :usershare1list5 usershare1list5/dom
   :usershare1list6 usershare1list6/dom
   :usershare3list usershare3list/dom
   :usershare8list usershare8list/dom
   :userrecord3list userrecord3list/dom
   :userrecord8list userrecord8list/dom
   :crane crane/dom
   :algebra1 algebra1/dom
   :coin1plaza coin1plaza/dom
   :share1plaza share1plaza/dom
   :blossom blossom/dom
   :analysis3 analysis3/dom
   :coin3plaza coin3plaza/dom
   :share3plaza share3plaza/dom
   :moon moon/dom
   :analysis8 analysis8/dom
   :coin8plaza coin8plaza/dom
   :share8plaza share8plaza/dom
   :history history/dom})

(def router
  (reitit/router
   [["/" :home]
    ["/login" :login]
    ["/enroll" :enroll]
    ["/userinfo" :userinfo]
    ["/notifications" :notifications]
    ["/contracts" :contracts]
    ["/contract" :contract]
    ["/userprofile" :userprofile]
    ["/userplaylist" :userplaylist]
    ["/userplaylists" :userplaylists]
    ["/userportfolio" :userportfolio]
    ["/usercoin1" :usercoin1]
    ["/usercoin3list" :usercoin3list]
    ["/usercoin8list" :usercoin8list]
    ["/usershare1list1" :usershare1list1]
    ["/usershare1list2" :usershare1list2]
    ["/usershare1list3" :usershare1list3]
    ["/usershare1list4" :usershare1list4]
    ["/usershare1list5" :usershare1list5]
    ["/usershare1list6" :usershare1list6]
    ["/usershare3list" :usershare3list]
    ["/usershare8list" :usershare8list]
    ["/userrecord3list" :userrecord3list]
    ["/userrecord8list" :userrecord8list]
    ["/crane" :crane]
    ["/algebra1" :algebra1]
    ["/coin1plaza" :coin1plaza]
    ["/share1plaza" :share1plaza]
    ["/blossom" :blossom]
    ["/analysis3" :analysis3]
    ["/coin3plaza" :coin3plaza]
    ["/share3plaza" :share3plaza]
    ["/moon" :moon]
    ["/analysis8" :analysis8]
    ["/coin8plaza" :coin8plaza]
    ["/share8plaza" :share8plaza]
    ["/history" :history]]))

(defn match-route [uri]
  (->> (or (not-empty (string/replace uri #"^.*#" "")) "/")
       (reitit/match-by-path router)
       :data
       :name))

(defn hook-browser-navigation! []
  (doto history'/history
    (events/listen
     HistoryEventType/NAVIGATE
     (fn [event]
       (reset! route ((match-route (.-token event)) main-route))
       (swap! loaded assoc 0 true)))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn ^:dev/after-load mount-root []
  (dom/render [main/dom {::route route
                         ::loaded loaded}] (.getElementById js/document "app")))

(defn ^:export ^:dev/once init! []
  (hook-browser-navigation!)
  (mount-root))
