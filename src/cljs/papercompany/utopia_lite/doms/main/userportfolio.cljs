(ns papercompany.utopia-lite.doms.main.userportfolio
  (:require
   [reagent.core :as reagent]))

(set! *warn-on-infer* false)

(defn init [states]
  (fn [_]
    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))

(defn dom [states]
  (reagent/with-let [states (merge states {})
                     _ (reset! (:papercompany.utopia-lite.doms.main/init states) (init states))
                     _ ((init states) nil)
                     _ (reset! (:papercompany.utopia-lite.doms.main/name states) "포트폴리오")
                     _ (reset! (:papercompany.utopia-lite.doms.main/details states) "해당 유저가 가진 모든 코인들에 대한 목록입니다")]
    [:div "버전 0.0.0"
     [:br]
     [:span "마지막 업데이트: 2024-07-09"]]))
