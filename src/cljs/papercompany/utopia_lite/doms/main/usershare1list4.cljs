(ns papercompany.utopia-lite.doms.main.usershare1list4
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
                     _ (reset! (:papercompany.utopia-lite.doms.main/name states) "백학문")
                     _ (reset! (:papercompany.utopia-lite.doms.main/details states) "[물] 타인의 생각을 받아들이면 깊어지는 나의 세계")]
    [:div "버전 0.0.0"
     [:br]
     [:span "마지막 업데이트: 2024-07-09"]]))
