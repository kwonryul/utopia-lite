(ns papercompany.utopia-lite.doms.main.blossom
  (:require
   [papercompany.utopia-lite.route :as route]
   [papercompany.utopia-lite.markdown :as markdown]
   [reagent.core :as reagent]
   [clojure.string :as str]))

(set! *warn-on-infer* false)

(def article1
  (apply str
         (interpose
          "\n"
          ["##### 아직 선출되지 않았습니다"
           "- - -"
           "**3월**은 아직 준비중에 있습니다.  "
           "**벚꽃**이 공석인 동안에는 초대 **두루미**가 벚꽃의 역할을 겸합니다."
           ""
           "초대 **벚꽃**은 인문학과 정치 사회학을 탐구하는 열정적인 대학생을 상정하고 있습니다.  "
           "초대 **벚꽃**은 **백학대수**를 재집필하는 업무를 맡는데, 유토피아가 정상작동하기 위해서 **백학대수**의 초기버전은 초대 **두루미**, **벚꽃**, **보름달**의 독단이 반영될 수 있습니다."])))

(def articles [article1])

(defn scroll-top-handler [states]
  (fn []
    (.scrollTo
     (.getElementById js/document "blossom-overflow")
     #js {:top 0
          :behavior "smooth"})
    (.scrollTo
     (.getElementById js/document "blossom-scroll")
     #js {:left (-
                 (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                             60
                                             80))
                 (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                          0.8)
                       (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                         180
                         240))
                    2))
          :behavior "smooth"})
    (.scrollTo
     (.getElementById js/document "blossom-content")
     #js {:top 0
          :behavior "smooth"})))

(defn resize-handler [states]
  (fn []
    (reset! (::blossom-overflow? states) (let [blossom-overflow (.getElementById js/document "blossom-overflow")]
                                           (> (.-scrollHeight blossom-overflow)
                                              (.-clientHeight blossom-overflow))))
    (reset! (::blossom-name-height states) (let [blossom-name-height-string (.-height (.getComputedStyle js/window (.getElementById js/document "blossom-name")))]
                                             (js/parseFloat
                                              (subs blossom-name-height-string 0 (- (count blossom-name-height-string) 2)))))))

(defn init [states]
  (fn [_]
    (reset! (:papercompany.utopia-lite.doms.main/name states) "벚꽃")
    (reset! (:papercompany.utopia-lite.doms.main/details states) "민중의 대변자")
    (reset! (::content states) article1)
    (reset! (::max-page states) 1)
    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 200)
    (js/setTimeout (scroll-top-handler states) 225)))

(defn dom [states]
  (reagent/with-let [states (merge states {::touch-start-x (reagent/atom nil)
                                           ::transition-x (reagent/atom 0)
                                           ::blossom-overflow? (reagent/atom false)
                                           ::blossom-name-height (reagent/atom 0)
                                           ::content (reagent/atom "")
                                           ::page (reagent/atom 1)
                                           ::max-page (reagent/atom 0)})
                     _ (js/setTimeout #(do
                                         (reset! (:papercompany.utopia-lite.doms.main/resize-handler states) (resize-handler states))
                                         (reset! (:papercompany.utopia-lite.doms.main/scroll-top-handler states) (scroll-top-handler states))
                                         (reset! (:papercompany.utopia-lite.doms.main/init states) (init states))) 25)
                     _ ((init states) nil)]
    (if @(:papercompany.utopia-lite.doms.main/mobile?? states)
      [:div {:style {:display "flex"
                     :flex-direction "column"
                     :width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")}}
       [:div {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                      :height (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                      :position "relative"
                      :margin-bottom "1.25em"}}
        [:img.frame {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                             :height (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                             :position "absolute"
                             :top "0px"
                             :left "0px"
                             :cursor "pointer"}
                     :src "images/blossom.png"
                     :on-click #(do
                                  (reset! (:papercompany.utopia-lite.doms.main/userprofile-params states) {:user-no 2})
                                  (route/route "/userprofile"))
                     :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]
        [:img {:style {:width (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 4) "px")
                       :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 4) "px")
                       :position "absolute"
                       :top (str (* (/ @(:papercompany.utopia-lite.doms.main/content-width states) 4) 3) "px")
                       :left (str (* (/ @(:papercompany.utopia-lite.doms.main/content-width states) 4) 3) "px")
                       :cursor "pointer"}
               :src "images/light.png"
               :on-click #(do
                            (reset! (:papercompany.utopia-lite.doms.main/userprofile-params states) {:user-no 2})
                            (route/route "/userprofile"))
               :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
       [:div {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                      :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) "px")
                      :position "relative"
                      :margin-bottom "1em"}}
        [:table#blossom-name.tableblock.frame-all.grid-all.stretch {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                                                            :position "absolute"
                                                                            :top "0px"
                                                                            :left "0px"
                                                                            :margin "0px"}}
         [:tbody
          [:tr
           [:td.tableblock.halign-center.valign-center
            [:div {:style {:font-size "1.2em"
                           :color "#ba3925"}}
             "업무목록"]]]]]
        [:div#blossom-overflow {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                        :height (str (-
                                                      (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3)
                                                      @(::blossom-name-height states)
                                                      -1) "px")
                                        :position "absolute"
                                        :top (str (- @(::blossom-name-height states) 1) "px")
                                        :border "1px solid rgba(0, 0, 0, 0)"
                                        :border-width "1px 0px 1px 0px"
                                        :overflow-y "auto"}}
         [(if @(::blossom-overflow? states)
            :table.tableblock.grid-all.stretch.no-bottom-border
            :table.tableblock.frame-all.grid-all.stretch) {:style {:width "100%"
                                                                   :position "absolute"
                                                                   :top "-1px"
                                                                   :margin "0px"}}
          [:tbody
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "초대 벚꽃 선출"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255,255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2026.12.31"]]]
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "백학대수 재집필"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255,255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2027.06.31"]]]]]]
        [:div.background-grey {:style {:display (if @(::blossom-overflow? states)
                                                  ""
                                                  "none")
                                       :width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                       :height "1px"
                                       :position "absolute"
                                       :top (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 1) "px")}}]]
       [:div {:style {:display "flex"
                      :flex-direction "column"
                      :align-items "center"
                      :color "rgba(64, 128, 128, 0.5)"}}
        [:div {:style {:width (str (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8) "px")
                       :display "flex"
                       :justify-content "space-between"}}
         [:div {:style {:display "inline-flex"
                        :justify-content "center"
                        :align-items "center"
                        :width "5em"
                        :height "3em"
                        :padding-left "2em"
                        :padding-right "2em"
                        :cursor "pointer"}
                :on-click (fn [_]
                            (.scrollBy
                             (.getElementById js/document "blossom-scroll")
                             #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                          -120
                                          -160)
                                  :behavior "smooth"}))} "<"]
         (vec (concat [:div#blossom-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
                                                                                (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                  120
                                                                                  160))]
                                                                   (if (> (* @(::max-page states)
                                                                             (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                               60
                                                                               80))
                                                                          width)
                                                                     {:width (str width "px")
                                                                      :white-space "nowrap"
                                                                      :overflow-x "auto"}
                                                                     {:display "flex"
                                                                      :justify-content "space-evenly"
                                                                      :width (str width "px")}))}]
                      (mapv (fn [x]
                              [:div {:style {:display "inline-flex"
                                             :justify-content "center"
                                             :align-items "center"
                                             :width "5em"
                                             :height "3em"
                                             :padding-left "2em"
                                             :padding-right "2em"
                                             :color (if (= @(::page states) x)
                                                      "#ba3925"
                                                      "")
                                             :cursor "pointer"}
                                     :on-click (fn [_]
                                                 (reset! (::page states) x)
                                                 (.scrollTo
                                                  (.getElementById js/document "blossom-scroll")
                                                  #js {:left (-
                                                              (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                          60
                                                                                          80))
                                                              (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                       0.8)
                                                                    (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                      180
                                                                      240))
                                                                 2))
                                                       :behavior "smooth"})
                                                 (reset! (::content states) (articles (dec x)))
                                                 (js/setTimeout
                                                  (fn []
                                                    (.scrollTo
                                                     (.getElementById js/document "blossom-content")
                                                     #js {:top 0
                                                          :behavior "smooth"}))
                                                  25)
                                                 (reset! (::max-page states) 1)
                                                 (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))} (str x)])
                            (range 1 (inc @(::max-page states))))))
         [:div {:style {:display "inline-flex"
                        :justify-content "center"
                        :align-items "center"
                        :width "5em"
                        :height "3em"
                        :padding-left "2em"
                        :padding-right "2em"
                        :cursor "pointer"}
                :on-click (fn [_]
                            (.scrollBy
                             (.getElementById js/document "blossom-scroll")
                             #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                          120
                                          160)
                                  :behavior "smooth"}))} ">"]]]
       [:div#blossom-content {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                      :min-height "20em"
                                      :max-height (str (- @(:papercompany.utopia-lite.doms.main/content-height states)
                                                          (* (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                               12
                                                               16) 3)) "px")
                                      :position "relative"
                                      :overflow-y "auto"}
                              :on-touch-start #(do
                                                 (reset! (::transition-x states) 0)
                                                 (reset! (::touch-start-x states) (.-screenX (aget (.-changedTouches %) 0))))
                              :on-touch-move #(reset! (::transition-x states)
                                                      (-
                                                       (.-screenX (aget (.-changedTouches %) 0))
                                                       @(::touch-start-x states)))
                              :on-touch-end #(let [touch-end-x (.-screenX (aget (.-changedTouches %) 0))]
                                               (cond
                                                 (> touch-end-x (+
                                                                 @(::touch-start-x states)
                                                                 100))
                                                 (do (swap! (::page states)
                                                            (fn [index] (inc (mod (- index 2) @(::max-page states)))))
                                                     (.scrollTo
                                                      (.getElementById js/document "blossom-scroll")
                                                      #js {:left (-
                                                                  (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                              60
                                                                                              80))
                                                                  (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                           0.8)
                                                                        (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                          180
                                                                          240))
                                                                     2))
                                                           :behavior "smooth"})
                                                     (reset! (::content states) (articles (dec @(::page states))))
                                                     (js/setTimeout
                                                      (fn []
                                                        (.scrollTo
                                                         (.getElementById js/document "blossom-content")
                                                         #js {:top 0
                                                              :behavior "smooth"}))
                                                      25)
                                                     (reset! (::max-page states) 5)
                                                     (reset! (::touch-start-x states) nil)
                                                     (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))
                                                 (< touch-end-x (-
                                                                 @(::touch-start-x states)
                                                                 100))
                                                 (do (swap! (::page states)
                                                            (fn [index] (inc (mod index @(::max-page states)))))
                                                     (.scrollTo
                                                      (.getElementById js/document "blossom-scroll")
                                                      #js {:left (-
                                                                  (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                              60
                                                                                              80))
                                                                  (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                           0.8)
                                                                        (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                          180
                                                                          240))
                                                                     2))
                                                           :behavior "smooth"})
                                                     (reset! (::content states) (articles (dec @(::page states))))
                                                     (js/setTimeout
                                                      (fn []
                                                        (.scrollTo
                                                         (.getElementById js/document "blossom-content")
                                                         #js {:top 0
                                                              :behavior "smooth"}))
                                                      25)
                                                     (reset! (::max-page states) 5)
                                                     (reset! (::touch-start-x states) nil)
                                                     (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))
                                               (reset! (::transition-x states) 0))                              }
        [:div.article.user {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states)"px")
                                    :position "relative"
                                    :top "0px"
                                    :left (str (cond
                                                 (> @(::transition-x states) 20)
                                                 (- 50 (/ 1000 @(::transition-x states)))
                                                 (< @(::transition-x states) -20)
                                                 (- -50 (/ 1000 @(::transition-x states)))
                                                 (= @(::transition-x states) 0)
                                                 0) "px")
                                    :padding-left "1em"
                                    :padding-right "1em"}
                            :dangerouslySetInnerHTML (markdown/innerHTML @(::content states))}]]]
                                        ;------------------------------------------------------------------------------------------------
      [:div {:style {:display "flex"
                     :flex-direction "column"}}
       [:div {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                      :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) "px")
                      :margin-bottom "1em"}}
        [:img.frame {:style {:width (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) "px")
                             :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) "px")
                             :position "absolute"
                             :top "0px"
                             :left "0px"
                             :cursor "pointer"}
                     :src "images/blossom.png"
                     :on-click #(do
                                  (reset! (:papercompany.utopia-lite.doms.main/userprofile-params states) {:user-no 2})
                                  (route/route "/userprofile"))
                     :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]
        [:img {:style {:width (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 12) "px")
                       :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 12) "px")
                       :position "absolute"
                       :top (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 4) "px")
                       :left (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 4) "px")
                       :cursor "pointer"}
               :src "images/light.png"
               :on-click #(do
                            (reset! (:papercompany.utopia-lite.doms.main/userprofile-params states) {:user-no 2})
                            (route/route "/userprofile"))
               :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]
        [:div {:style {:width (str (+ (* (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 2) 2) "px")
                       :height (str @(::blossom-name-height states) "px")
                       :position "absolute"
                       :top "0px"
                       :left (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 1) "px")
                       :border "1px solid rgba(0, 0, 0, 0)"
                       :border-width "0px 1px 0px 1px"}}
         [:table#blossom-name.tableblock.grid-all.stretch.no-left-bottom-border {:style {:width "100%"
                                                                                         :position "absolute"
                                                                                         :top "0px"
                                                                                         :left "-1px"
                                                                                         :margin "0px"}}
          [:tbody
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div {:style {:font-size "1.2em"
                            :color "#ba3925"}}
              "업무목록"]]]]]]
        [:div.background-grey {:style {:width (str (- (* (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 2) 1) "px")
                                       :height "1px"
                                       :position "absolute"
                                       :top (str (- @(::blossom-name-height states) 1) "px")
                                       :left (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) "px")}}]
        [:div#blossom-overflow {:style {:width (str (+ (* (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 2) 2) "px")
                                        :height (str (-
                                                      (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3)
                                                      @(::blossom-name-height states)
                                                      -1) "px")
                                        :position "absolute"
                                        :top (str (- @(::blossom-name-height states) 1) "px")
                                        :left (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 1) "px")
                                        :border "1px solid rgba(0, 0, 0, 0)"
                                        :overflow-y "auto"}}
         [(if @(::blossom-overflow? states)
            :table.tableblock.grid-all.stretch.no-bottom-border
            :table.tableblock.frame-all.grid-all.stretch) {:style {:width "100%"
                                                                   :position "absolute"
                                                                   :top "-1px"
                                                                   :left "-1px"
                                                                   :margin "0px"}}
          [:tbody
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "초대 벚꽃 선출"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255,255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2026.12.31"]]]
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "백학대수 재집필"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255,255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2027.06.31"]]]]]]
        [:div.background-grey {:style {:display (if @(::blossom-overflow? states)
                                                  ""
                                                  "none")
                                       :width (str (- (* (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 2) 1) "px")
                                       :height "1px"
                                       :position "absolute"
                                       :top (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 1) "px")
                                       :left (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) "px")}}]]
       [:div {:style {:display "flex"
                      :flex-direction "column"
                      :align-items "center"
                      :color "rgba(64, 128, 128, 0.5)"}}
        [:div {:style {:width (str (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8) "px")
                       :display "flex"
                       :justify-content "space-between"}}
         [:div {:style {:display "inline-flex"
                        :justify-content "center"
                        :align-items "center"
                        :width "5em"
                        :height "3em"
                        :padding-left "2em"
                        :padding-right "2em"
                        :cursor "pointer"}
                :on-click (fn [_]
                            (.scrollBy
                             (.getElementById js/document "blossom-scroll")
                             #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                          -120
                                          -160)
                                  :behavior "smooth"}))} "<"]
         (vec (concat [:div#blossom-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
                                                                                (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                  120
                                                                                  160))]
                                                                   (if (> (* @(::max-page states)
                                                                             (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                               60
                                                                               80))
                                                                          width)
                                                                     {:width (str width "px")
                                                                      :white-space "nowrap"
                                                                      :overflow-x "auto"}
                                                                     {:display "flex"
                                                                      :justify-content "space-evenly"
                                                                      :width (str width "px")}))}]
                      (mapv (fn [x]
                              [:div {:style {:display "inline-flex"
                                             :justify-content "center"
                                             :align-items "center"
                                             :width "5em"
                                             :height "3em"
                                             :padding-left "2em"
                                             :padding-right "2em"
                                             :color (if (= @(::page states) x)
                                                      "#ba3925"
                                                      "")
                                             :cursor "pointer"}
                                     :on-click (fn [_]
                                                 (reset! (::page states) x)
                                                 (.scrollTo
                                                  (.getElementById js/document "blossom-scroll")
                                                  #js {:left (-
                                                              (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                          60
                                                                                          80))
                                                              (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                       0.8)
                                                                    (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                      180
                                                                      240))
                                                                 2))
                                                       :behavior "smooth"})
                                                 (reset! (::content states) (articles (dec x)))
                                                 (js/setTimeout
                                                  (fn []
                                                    (.scrollTo
                                                     (.getElementById js/document "blossom-content")
                                                     #js {:top 0
                                                          :behavior "smooth"}))
                                                  25)
                                                 (reset! (::max-page states) 1)
                                                 (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))} (str x)])
                            (range 1 (inc @(::max-page states))))))
         [:div {:style {:display "inline-flex"
                        :justify-content "center"
                        :align-items "center"
                        :width "5em"
                        :height "3em"
                        :padding-left "2em"
                        :padding-right "2em"
                        :cursor "pointer"}
                :on-click (fn [_]
                            (.scrollBy
                             (.getElementById js/document "blossom-scroll")
                             #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                          120
                                          160)
                                  :behavior "smooth"}))} ">"]]]
       [:div#blossom-content {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                      :min-height "20em"
                                      :max-height (str (- @(:papercompany.utopia-lite.doms.main/content-height states)
                                                          (* (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                               12
                                                               16) 3)) "px")
                                      :position "relative"
                                      :overflow-y "auto"}
                              :on-touch-start #(do
                                                 (reset! (::transition-x states) 0)
                                                 (reset! (::touch-start-x states) (.-screenX (aget (.-changedTouches %) 0))))
                              :on-touch-move #(reset! (::transition-x states)
                                                      (-
                                                       (.-screenX (aget (.-changedTouches %) 0))
                                                       @(::touch-start-x states)))
                              :on-touch-end #(let [touch-end-x (.-screenX (aget (.-changedTouches %) 0))]
                                               (cond
                                                 (> touch-end-x (+
                                                                 @(::touch-start-x states)
                                                                 100))
                                                 (do (swap! (::page states)
                                                            (fn [index] (inc (mod (- index 2) @(::max-page states)))))
                                                     (.scrollTo
                                                      (.getElementById js/document "blossom-scroll")
                                                      #js {:left (-
                                                                  (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                              60
                                                                                              80))
                                                                  (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                           0.8)
                                                                        (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                          180
                                                                          240))
                                                                     2))
                                                           :behavior "smooth"})
                                                     (reset! (::content states) (articles (dec @(::page states))))
                                                     (js/setTimeout
                                                      (fn []
                                                        (.scrollTo
                                                         (.getElementById js/document "blossom-content")
                                                         #js {:top 0
                                                              :behavior "smooth"}))
                                                      25)
                                                     (reset! (::max-page states) 5)
                                                     (reset! (::touch-start-x states) nil)
                                                     (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))
                                                 (< touch-end-x (-
                                                                 @(::touch-start-x states)
                                                                 100))
                                                 (do (swap! (::page states)
                                                            (fn [index] (inc (mod index @(::max-page states)))))
                                                     (.scrollTo
                                                      (.getElementById js/document "blossom-scroll")
                                                      #js {:left (-
                                                                  (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                              60
                                                                                              80))
                                                                  (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                           0.8)
                                                                        (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                          180
                                                                          240))
                                                                     2))
                                                           :behavior "smooth"})
                                                     (reset! (::content states) (articles (dec @(::page states))))
                                                     (js/setTimeout
                                                      (fn []
                                                        (.scrollTo
                                                         (.getElementById js/document "blossom-content")
                                                         #js {:top 0
                                                              :behavior "smooth"}))
                                                      25)
                                                     (reset! (::max-page states) 5)
                                                     (reset! (::touch-start-x states) nil)
                                                     (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))
                                               (reset! (::transition-x states) 0))}
        [:div.article.user {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                    :position "relative"
                                    :top "0px"
                                    :left (str (cond
                                                 (> @(::transition-x states) 20)
                                                 (- 50 (/ 1000 @(::transition-x states)))
                                                 (< @(::transition-x states) -20)
                                                 (- -50 (/ 1000 @(::transition-x states)))
                                                 (= @(::transition-x states) 0)
                                                 0) "px")
                                    :padding-left "1em"
                                    :padding-right "1em"}
                            :dangerouslySetInnerHTML (markdown/innerHTML @(::content states))}]]])
    (finally
      (reset! (:papercompany.utopia-lite.doms.main/resize-handler states) (fn []))
      (reset! (:papercompany.utopia-lite.doms.main/scroll-top-handler states) (fn [])))))
