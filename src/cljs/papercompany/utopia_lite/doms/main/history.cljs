(ns papercompany.utopia-lite.doms.main.history
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
                     _ (reset! (:papercompany.utopia-lite.doms.main/name states) "역사")
                     _ (reset! (:papercompany.utopia-lite.doms.main/details states) "과거의 소중한 것들을 계승하여 미래로 나아가자")]
    [:div
     [:span "유토피아-Lite는 실험적 성격을 많이 띄기에, 역사를 기록하기에는 비용이 크다고 생각하여 배제하였습니다"]
     [:br]
     [:span "유토피아-프리덤 / 유토피아-저스티스 / 유토피아-데스티니 에 대해서는 역사 기록의 기능이 추가됩니다"]]))
