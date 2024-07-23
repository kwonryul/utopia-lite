(ns papercompany.utopia-lite.doms.main.notifications
  (:require
   [reagent.core :as reagent]))

(set! *warn-on-infer* false)

(defn init [states]
  (fn [_]
    ((:papercompany.utopia-lite.doms.main/resize-content states))))

(defn dom [states]
  (reagent/with-let [states (merge states {})
                     _ (reset! (:papercompany.utopia-lite.doms.main/init states) (init states))
                     _ ((init states) nil)
                     _ (reset! (:papercompany.utopia-lite.doms.main/name states) "알림")
                     _ (reset! (:papercompany.utopia-lite.doms.main/details states) "새로운 알림들을 확인하세요")]
    [:div "버전 0.0.0"
     [:br]
     [:span "마지막 업데이트: 2024-07-09"]]))
