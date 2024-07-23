(ns papercompany.utopia-lite.doms.main.contract
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
                     _ (reset! (:papercompany.utopia-lite.doms.main/name states) "거래")
                     _ (reset! (:papercompany.utopia-lite.doms.main/details states) "신뢰있는 거래를 통해 공감과 지지를 표현해주세요")]
    [:div "버전 0.0.0"
     [:br]
     [:span "마지막 업데이트: 2024-07-09"]]))
