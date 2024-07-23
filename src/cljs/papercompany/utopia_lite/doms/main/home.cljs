(ns papercompany.utopia-lite.doms.main.home
  (:require
   [papercompany.utopia-lite.doms.main :as main]
   [papercompany.utopia-lite.route :as route]
   [reagent.core :as reagent]))

(set! *warn-on-infer* false)

(defn init [states]
  (fn [_]))

(defn dom [states]
  (reagent/with-let [states (merge states {})
                     _ (reset! (:papercompany.utopia-lite.doms.main/init states) (init states))
                     _ ((init states) nil)
                     _ (reset! (:papercompany.utopia-lite.doms.main/name states) "유토피아-Lite")
                     _ (reset! (:papercompany.utopia-lite.doms.main/details states) "공감기반의 자유세계에 민주와 정의를 더하다")]
    (if @(:papercompany.utopia-lite.doms.main/mobile?? states)
      [:div
       [:img {:style {:width "100%"
                      :margin-bottom "3em"}
              :src "images/chart.png"}]
       [:table.tableblock.frame-all.grid-all.stretch
        [:tbody
         [:tr
          [:td.tableblock.halign-center.valign-center {:style {:width (if @main/content-width
                                                                        (str (/ @main/content-width 3) "px")
                                                                        "0px")
                                                               :height (if @main/content-width
                                                                         (str (/ @main/content-width 3) "px")
                                                                         "0px")
                                                               :position "relative"
                                                               :cursor "pointer"}
                                                       :on-click #(route/route "/blossom")}
           [:img {:style {:width "100%"
                          :height "100%"
                          :border-radius "1em"}
                  :src "images/blossom.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]
           [:img {:style {:width "30%"
                          :height "30%"
                          :position "absolute"
                          :right "0px"
                          :bottom "0px"
                          :z-index "1001"}
                  :src "images/light.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]]
          [:td.tableblock.halign-center.valign-center {:style {:width (if @main/content-width
                                                                        (str (/ @main/content-width 3) "px")
                                                                        "0px")
                                                               :height (if @main/content-width
                                                                         (str (/ @main/content-width 3) "px")
                                                                         "0px")
                                                               :position "relative"
                                                               :cursor "pointer"}
                                                       :on-click #(route/route "/crane")}
           [:img {:style {:width "100%"
                          :height "100%"
                          :border-radius "1em"}
                  :src "images/freedom-gundam.jpg"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]
           [:img {:style {:width "30%"
                          :height "30%"
                          :position "absolute"
                          :right "0px"
                          :bottom "0px"
                          :z-index "1001"}
                  :src "images/light.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]]
          [:td.tableblock.halign-center.valign-center {:style {:width (if @main/content-width
                                                                        (str (/ @main/content-width 3) "px")
                                                                        "0px")
                                                               :height (if @main/content-width
                                                                         (str (/ @main/content-width 3) "px")
                                                                         "0px")
                                                               :position "relative"
                                                               :cursor "pointer"}
                                                       :on-click #(route/route "/moon")}
           [:img {:style {:width "100%"
                          :height "100%"
                          :border-radius "1em"}
                  :src "images/destroy-pheonix.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]
           [:img {:style {:width "30%"
                          :height "30%"
                          :position "absolute"
                          :right "0px"
                          :bottom "0px"
                          :z-index "1001"}
                  :src "images/light.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]]]
         [:tr
          [:td.tableblock.halign-center.valign-center
           [:span {:style {:color "rgb(64, 128, 128)"
                           :cursor "pointer"}
                   :on-click #(route/route "/blossom")} "벚꽃"]]
          [:td.tableblock.halign-center.valign-center
           [:span {:style {:color "rgb(64, 128, 128)"
                           :cursor "pointer"}
                   :on-click #(route/route "/crane")} "두루미"]]
          [:td.tableblock.halign-center.valign-center
           [:span {:style {:color "rgb(64, 128, 128)"
                           :cursor "pointer"}
                   :on-click #(route/route "/moon")} "보름달"]]]]]]
                                        ;----------------------------------------------------------------------------------------------------------------------------
      [:div
       [:img {:style {:width "100%"
                      :margin-bottom "3em"}
              :src "images/chart.png"}]
       [:table.tableblock.frame-all.grid-all.stretch
        [:tbody
         [:tr
          [:td.tableblock.halign-center.valign-center {:style {:width (if @main/content-width
                                                                        (str (/ @main/content-width 3) "px")
                                                                        "0px")
                                                               :height (if @main/content-width
                                                                         (str (/ @main/content-width 3) "px")
                                                                         "0px")
                                                               :position "relative"
                                                               :cursor "pointer"}
                                                       :on-click #(route/route "/blossom")}
           [:img {:style {:width "100%"
                          :height "100%"
                          :border-radius "1em"}
                  :src "images/blossom.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]
           [:img {:style {:width "30%"
                          :height "30%"
                          :position "absolute"
                          :right "0px"
                          :bottom "0px"
                          :z-index "1001"}
                  :src "images/light.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]]
          [:td.tableblock.halign-center.valign-center {:style {:width (if @main/content-width
                                                                        (str (/ @main/content-width 3) "px")
                                                                        "0px")
                                                               :height (if @main/content-width
                                                                         (str (/ @main/content-width 3) "px")
                                                                         "0px")
                                                               :position "relative"
                                                               :cursor "pointer"}
                                                       :on-click #(route/route "/crane")}
           [:img {:style {:width "100%"
                          :height "100%"
                          :border-radius "1em"}
                  :src "images/freedom-gundam.jpg"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]
           [:img {:style {:width "30%"
                          :height "30%"
                          :position "absolute"
                          :right "0px"
                          :bottom "0px"
                          :z-index "1001"}
                  :src "images/light.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]]
          [:td.tableblock.halign-center.valign-center {:style {:width (if @main/content-width
                                                                        (str (/ @main/content-width 3) "px")
                                                                        "0px")
                                                               :height (if @main/content-width
                                                                         (str (/ @main/content-width 3) "px")
                                                                         "0px")
                                                               :position "relative"
                                                               :cursor "pointer"}
                                                       :on-click #(route/route "/moon")}
           [:img {:style {:width "100%"
                          :height "100%"
                          :border-radius "1em"}
                  :src "images/destroy-pheonix.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]
           [:img {:style {:width "30%"
                          :height "30%"
                          :position "absolute"
                          :right "0px"
                          :bottom "0px"
                          :z-index "1001"}
                  :src "images/light.png"
                  :on-load #((:papercompany.utopia-lite.doms.main/resize-content states))}]]]
         [:tr
          [:td.tableblock.halign-center.valign-center
           [:span {:style {:color "rgb(64, 128, 128)"
                           :cursor "pointer"}
                   :on-click #(route/route "/blossom")} "벚꽃"]]
          [:td.tableblock.halign-center.valign-center
           [:span {:style {:color "rgb(64, 128, 128)"
                           :cursor "pointer"}
                   :on-click #(route/route "/crane")} "두루미"]]
          [:td.tableblock.halign-center.valign-center
           [:span {:style {:color "rgb(64, 128, 128)"
                           :cursor "pointer"}
                   :on-click #(route/route "/moon")} "보름달"]]]]]])))
