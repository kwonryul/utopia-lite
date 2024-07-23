(ns papercompany.utopia-lite.doms.main.crane
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
          ["##### 환영합니다"
           "- - -"
           "유토피아에 대한 공지사항이나 웹사이트 사용방법은 현재 페이지인 **두루미** 페이지에서 확인이 가능합니다.  "
           "페이지를 넘겨서 다음 공지사항들을 확인하세요."
           ""
           "유토피아에 대한 설명은 **메뉴**의 **1월**의 **백학대수**를 읽어주세요."])))

(def article2
  (apply str
         (interpose
          "\n"
          ["##### 디자인 규칙 [컨텐츠]"
           "- - -"
           "이미지들은 클릭했을때 상호작용이 있을 가능성이 있습니다."
           ""
           "표는 위 아래로 스크롤이 가능할 수 있습니다."
           ""
           "메인 컨텐츠는 책장을 넘기는 것과 같은 경험을 제공하기 위해 노력했습니다.  "
           "페이지바는 좌우 스크롤이 가능하며, 이를 통해 원하는 페이지로 빠르게 이동할 수 있습니다.  "
           "메인 컨텐츠를 좌 우로 스와이프 하여 페이지를 한 장 넘길 수 있습니다."])))

(def article3
  (apply str
         (interpose
          "\n"
          ["##### 디자인 규칙 [폰트]"
           "- - -"
           "- 굵은 글씨: 키워드, 강조"
           ""
           "- 보통 글씨: 페이지의 틀을 담당하는 고정 글씨"
           "- 연한 글씨: 유저데이터"
           "- 아주 연한 글씨: 세부사항"
           ""
           "- 빨간색 글씨: 제목"
           "- 검은색 글씨: 일반"
           "- 초록색 글씨: 누르면 상호작용이 있음"
           ""
           "다크모드에서는 모든 색이 동일하나, 검은색 계열만 흰색 계열로 바뀝니다."])))

(def article4
  (apply str
         (interpose
          "\n"
          ["##### UI/UX 팁"
           "- - -"
           "브라우저의 뒤로가기와 앞으로가기의 사용을 장려합니다."
           ""
           "브라우저의 새로고침은 사용하지 않길 추천드립니다.  "
           "브라우저의 새로고침 대신 페이지 최상단의 붉은 제목을 누르시면 새로고침과 동일한 효과를 얻으실 수 있습니다."
           ""
           "페이지의 하단 좌측의 **Powered By PaperCompany**를 누르면 페이지가 최상단으로 스크롤됩니다."
           ""
           "페이지의 하단 우측에 다크모드 버튼이 있습니다."])))

(def article5
  (apply str
         (interpose
          "\n"
          ["##### 소통"
           "- - -"
           "**소통** 탭에 있는 항목들은 현재 소통중인 유저를 대상으로 진행됩니다.  "
           ""
           "유토피아에서 활동중에 누군가의 **프로필**에서 상호작용을 진행하면, 그 사람과 소통상태가 됩니다.  "
           "**메뉴**를 열면 현재 소통중인 상대를 확인할 수 있습니다."
           ""
           "**메뉴**의 재생버튼은 현재 소통중인 유저의 대표 플레이리스트에 있는 곡을 랜덤으로 재생합니다."])))

(def articles [article1 article2 article3 article4 article5])

(defn scroll-top-handler [states]
  (fn []
    (.scrollTo
     (.getElementById js/document "crane-overflow")
     #js {:top 0
          :behavior "smooth"})
    (.scrollTo
     (.getElementById js/document "crane-scroll")
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
     (.getElementById js/document "crane-content")
     #js {:top 0
          :behavior "smooth"})))

(defn resize-handler [states]
  (fn []
    (reset! (::crane-overflow? states) (let [crane-overflow (.getElementById js/document "crane-overflow")]
                                         (> (.-scrollHeight crane-overflow)
                                            (.-clientHeight crane-overflow))))
    (reset! (::crane-name-height states) (let [crane-name-height-string (.-height (.getComputedStyle js/window (.getElementById js/document "crane-name")))]
                                           (js/parseFloat
                                            (subs crane-name-height-string 0 (- (count crane-name-height-string) 2)))))))

(defn init [states]
  (fn [_]
    (reset! (:papercompany.utopia-lite.doms.main/name states) "두루미")
    (reset! (:papercompany.utopia-lite.doms.main/details states) "모두의 마음을 하나로 모아")
    (reset! (::content states) article1)
    (reset! (::page states) 1)
    (reset! (::max-page states) 5)
    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 200)
    (js/setTimeout (scroll-top-handler states) 225)))

(defn dom [states]
  (reagent/with-let [states (merge states {::touch-start-x (reagent/atom nil)
                                           ::transition-x (reagent/atom 0)
                                           ::crane-overflow? (reagent/atom false)
                                           ::crane-name-height (reagent/atom 0)
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
                     :src "images/freedom-gundam.jpg"
                     :on-click #(do
                                  (reset! (:papercompany.utopia-lite.doms.main/userprofile-params states) {:user-no 1})
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
                            (reset! (:papercompany.utopia-lite.doms.main/userprofile-params states) {:user-no 1})
                            (route/route "/userprofile"))
               :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
       [:div {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                      :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) "px")
                      :position "relative"
                      :margin-bottom "1em"}}
        [:table#crane-name.tableblock.frame-all.grid-all.stretch {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
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
        [:div#crane-overflow {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                      :height (str (-
                                                    (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3)
                                                    @(::crane-name-height states)
                                                    -1) "px")
                                      :position "absolute"
                                      :top (str (- @(::crane-name-height states) 1) "px")
                                      :border "1px solid rgba(0, 0, 0, 0)"
                                      :border-width "1px 0px 1px 0px"
                                      :overflow-y "auto"}}
         [(if @(::crane-overflow? states)
            :table.tableblock.grid-all.stretch.no-bottom-border
            :table.tableblock.frame-all.grid-all.stretch) {:style {:width "100%"
                                                                   :position "absolute"
                                                                   :top "-1px"
                                                                   :margin "0px"}}
          [:tbody
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "예시 데이터를 통한 서비스 소개"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "2024.07.25 ~ 2024.08.31"]]]
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "페이퍼컴퍼니에 합류할 인문학적 소양을 지닌 인재 모집"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2026.12.31"]]]
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "유토피아-Lite 1월 오픈"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2024.10.31"]]]
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "유토피아-Lite 거래소 오픈"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2024.12.31"]]]]]]
        [:div.background-grey {:style {:display (if @(::crane-overflow? states)
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
                             (.getElementById js/document "crane-scroll")
                             #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                          -120
                                          -160)
                                  :behavior "smooth"}))} "<"]
         (vec (concat [:div#crane-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
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
                                                  (.getElementById js/document "crane-scroll")
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
                                                     (.getElementById js/document "crane-content")
                                                     #js {:top 0
                                                          :behavior "smooth"}))
                                                  25)
                                                 (reset! (::max-page states) 5)
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
                             (.getElementById js/document "crane-scroll")
                             #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                          120
                                          160)
                                  :behavior "smooth"}))} ">"]]]
       [:div#crane-content {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
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
                                                    (.getElementById js/document "crane-scroll")
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
                                                       (.getElementById js/document "crane-content")
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
                                                    (.getElementById js/document "crane-scroll")
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
                                                       (.getElementById js/document "crane-content")
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
                     :src "images/freedom-gundam.jpg"
                     :on-click #(do
                                  (reset! (:papercompany.utopia-lite.doms.main/userprofile-params states) {:user-no 1})
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
                            (reset! (:papercompany.utopia-lite.doms.main/userprofile-params states) {:user-no 1})
                            (route/route "/userprofile"))
               :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]
        [:div {:style {:width (str (+ (* (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 2) 2) "px")
                       :height (str @(::crane-name-height states) "px")
                       :position "absolute"
                       :top "0px"
                       :left (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 1) "px")
                       :border "1px solid rgba(0, 0, 0, 0)"
                       :border-width "0px 1px 0px 1px"}}
         [:table#crane-name.tableblock.grid-all.stretch.no-left-bottom-border {:style {:width "100%"
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
                                       :top (str (- @(::crane-name-height states) 1) "px")
                                       :left (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) "px")}}]
        [:div#crane-overflow {:style {:width (str (+ (* (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 2) 2) "px")
                                      :height (str (-
                                                    (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3)
                                                    @(::crane-name-height states)
                                                    -1) "px")
                                      :position "absolute"
                                      :top (str (- @(::crane-name-height states) 1) "px")
                                      :left (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 3) 1) "px")
                                      :border "1px solid rgba(0, 0, 0, 0)"
                                      :overflow-y "auto"}}
         [(if @(::crane-overflow? states)
            :table.tableblock.grid-all.stretch.no-bottom-border
            :table.tableblock.frame-all.grid-all.stretch) {:style {:width "100%"
                                                                   :position "absolute"
                                                                   :top "-1px"
                                                                   :left "-1px"
                                                                   :margin "0px"}}
          [:tbody
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "예시 데이터를 통한 서비스 소개"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "2024.07.25 ~ 2024.08.31"]]]
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "페이퍼컴퍼니에 합류할 인문학적 소양을 지닌 인재 모집"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2026.12.31"]]]
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "유토피아-Lite 1월 오픈"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2024.12.31"]]]
           [:tr
            [:td.tableblock.halign-center.valign-center
             [:div.user "유토피아-Lite 거래소 오픈"]
             [:div.user {:style {:font-size "0.8em"
                                 :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
              "~ 2025.06.31"]]]]]]
        [:div.background-grey {:style {:display (if @(::crane-overflow? states)
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
                             (.getElementById js/document "crane-scroll")
                             #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                          -120
                                          -160)
                                  :behavior "smooth"}))} "<"]
         (vec (concat [:div#crane-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
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
                                                  (.getElementById js/document "crane-scroll")
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
                                                     (.getElementById js/document "crane-content")
                                                     #js {:top 0
                                                          :behavior "smooth"}))
                                                  25)
                                                 (reset! (::max-page states) 5)
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
                             (.getElementById js/document "crane-scroll")
                             #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                          120
                                          160)
                                  :behavior "smooth"}))} ">"]]]
       [:div#crane-content {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
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
                                                    (.getElementById js/document "crane-scroll")
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
                                                       (.getElementById js/document "crane-content")
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
                                                    (.getElementById js/document "crane-scroll")
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
                                                       (.getElementById js/document "crane-content")
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
