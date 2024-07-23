(ns papercompany.utopia-lite.doms.main.usershare1list3
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
                     _ (reset! (:papercompany.utopia-lite.doms.main/details states) "[불] 세상에서 내가 정말 지키고 싶은 것")]
    [:div "버전 0.0.0"
     [:br]
     [:span "마지막 업데이트: 2024-07-09"]]))
