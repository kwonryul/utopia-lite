(ns papercompany.utopia-lite.doms.main.userprofile
  (:require
   [papercompany.utopia-lite.doms.main :as main]
   [papercompany.utopia-lite.services.user :as user-service]
   [papercompany.utopia-lite.route :as route]
   [reagent.core :as reagent]))

(set! *warn-on-infer* false)

(defn profile-image-urls [user-no]
  (case user-no
    1 ["/images/freedom-gundam.jpg"
       "/images/jack.webp"
       "/images/hero.webp"]
    2 ["/images/blossom.png"]
    3 ["/images/destroy-pheonix.png"
       "/images/sesshomaru.png"
       "/images/destiny-gundam.png"]))

(defn scroll-top-handler [states]
  (fn []
    (when-let [userprofile-overflow (.getElementById js/document "userprofile-overflow")]
      (.scrollTo
       userprofile-overflow
       #js {:top 0
            :behavior "smooth"}))
    (when-let [userprofile-scroll (.getElementById js/document "userprofile-scroll")]
      (.scrollTo
       userprofile-scroll
       #js {:left (-
                   (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                         60
                                                         80))
                   (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                            0.8)
                         (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                           180
                           240))
                      2))}))))

(defn resize-handler [states]
  (fn []
    (when-let [userprofile-overflow (.getElementById js/document "userprofile-overflow")]
      (reset! (::userprofile-overflow? states) (> (.-scrollHeight userprofile-overflow)
                                                  (.-clientHeight userprofile-overflow))))
    (when-let [userprofile-search-overflow (.getElementById js/document "userprofile-search-overflow")]
      (reset! (::userprofile-search-overflow? states) (> (.-scrollHeight userprofile-search-overflow)
                                                         (.-clientHeight userprofile-search-overflow))))
    (when-let [userprofile-name (.getElementById js/document "userprofile-name")]
      (reset! (::userprofile-name-height states) (let [userprofile-name-height-string (.-height (.getComputedStyle js/window userprofile-name))]
                                                   (js/parseFloat
                                                    (subs userprofile-name-height-string 0 (- (count userprofile-name-height-string) 2))))))))

(defn init [states]
  (fn [_]
    (reset! (:papercompany.utopia-lite.doms.main/name states) "프로필")
    (reset! (:papercompany.utopia-lite.doms.main/details states) "관심 가는 사람들을 찾아 소통을 시작하세요")
    (reset! (::user-no states) (:user-no @(:papercompany.utopia-lite.doms.main/userprofile-params states)))
    (when-let [user-no @(::user-no states)]
      (reset! (::profile-image-url states) ((profile-image-urls user-no) 0))
      (reset! (::page states) 1)
      (reset! (::max-page states) (case user-no
                                    1 3
                                    2 1
                                    3 3)))
    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 200)
    (js/setTimeout (scroll-top-handler states) 225)))

(defn dom [states]
  (reagent/with-let [states (merge states {::touch-start-x (reagent/atom nil)
                                           ::transition-x (reagent/atom 0)
                                           ::userprofile-overflow? (reagent/atom false)
                                           ::userprofile-name-height (reagent/atom 0)
                                           ::userprofile-search-overflow? (reagent/atom false)
                                           ::user-no (reagent/atom nil)
                                           ::search-mode (reagent/atom 0)
                                           ::profile-image-url (reagent/atom "")
                                           ::page (reagent/atom 1)
                                           ::max-page (reagent/atom 0)})
                     _ (js/setTimeout #(do
                                         (reset! (:papercompany.utopia-lite.doms.main/resize-handler states) (resize-handler states))
                                         (reset! (:papercompany.utopia-lite.doms.main/scroll-top-handler states) (scroll-top-handler states))
                                         (reset! (:papercompany.utopia-lite.doms.main/init states) (init states))) 25)
                     _ ((init states) nil)]
    (if @(::papercompany.utopia-lite.doms.main/mobile?? states)
      (vec (concat
            [:div {:style {:display "flex"
                           :flex-direction "column"
                           :width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                           :position "relative"}}]
            (if @(::user-no states)
              [[:div {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                              :height (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                              :position "relative"
                              :top "0px"
                              :left (str (cond
                                           (> @(::transition-x states) 20)
                                           (- 50 (/ 1000 @(::transition-x states)))
                                           (< @(::transition-x states) -20)
                                           (- -50 (/ 1000 @(::transition-x states)))
                                           (= @(::transition-x states) 0)
                                           0) "px")
                              :margin-bottom "1em"}
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
                                              (.getElementById js/document "userprofile-scroll")
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
                                             (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) (dec @(::page states))))
                                             (reset! (::max-page states) (case @(::user-no states)
                                                                           1 3
                                                                           2 1
                                                                           3 3))
                                             (reset! (::touch-start-x states) nil)
                                             (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))
                                         (< touch-end-x (-
                                                         @(::touch-start-x states)
                                                         100))
                                         (do (swap! (::page states)
                                                    (fn [index] (inc (mod index @(::max-page states)))))
                                             (.scrollTo
                                              (.getElementById js/document "userprofile-scroll")
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
                                             (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) (dec @(::page states))))
                                             (reset! (::max-page states) (case @(::user-no states)
                                                                           1 3
                                                                           2 1
                                                                           3 3))
                                             (reset! (::touch-start-x states) nil)
                                             (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))
                                       (reset! (::transition-x states) 0))}
                [:img.frame {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                     :height (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                     :position "absolute"
                                     :top "0px"
                                     :left "0px"}
                             :src @(::profile-image-url states)
                             :on-click #(when (not (= @(:papercompany.utopia-lite.doms.main/selected-user-no states) @(::user-no states)))
                                          (user-service/select states @(::user-no states)))
                             :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
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
                                     (.getElementById js/document "userplaylists-scroll")
                                     #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                  -120
                                                  -160)
                                          :behavior "smooth"}))} "<"]
                 (vec (concat [:div#userprofile-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
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
                                                          (.getElementById js/document "userprofile-scroll")
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
                                                         (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) (dec @(::page states))))
                                                         (reset! (::max-page states) (case @(:papercompany.utopia-lite.doms.main/selected-user-no states)
                                                                                       1 3
                                                                                       2 1
                                                                                       3 3))
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
                                     (.getElementById js/document "userproflie-scroll")
                                     #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                  120
                                                  160)
                                          :behavior "smooth"}))} ">"]]]


               [:div {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                              :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")
                              :position "relative"
                              :margin-top "1em"
                              :margin-bottom "3em"}}
                [:table#userprofile-name.tableblock.frame-all.grid-all.stretch {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                                                                        :position "absolute"
                                                                                        :top "0px"
                                                                                        :left "0px"
                                                                                        :margin "0px"}}
                 [:tbody
                  [:tr
                   [:td.tableblock.halign-center.valign-center
                    [:div.user {:style {:color "rgba(186, 57, 37, 0.5)"}}
                     [:strong (case @(::user-no states)
                                1 "STANDARD-TYPE"
                                2 "사쿠라"
                                3 "디파티드")]]]]]]
                [:div#userprofile-overflow {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                                    :height (str (-
                                                                  (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2)
                                                                  @(::userprofile-name-height states)
                                                                  -1) "px")
                                                    :position "absolute"
                                                    :top (str (- @(::userprofile-name-height states) 1) "px")
                                                    :border "1px solid rgba(0, 0, 0, 0)"
                                                    :border-width "1px 0px 1px 0px"
                                                    :overflow-y "auto"}}
                 [(if @(::userprofile-overflow? states)
                    :table.tableblock.grid-all.stretch.no-bottom-border
                    :table.tableblock.frame-all.grid-all.stretch) {:style {:width "100%"
                                                                           :position "absolute"
                                                                           :top "-1px"
                                                                           :margin "0px"}}
                  (case @(::user-no states)
                    1
                    [:tbody
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "프리덤 건담"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "칼의 노래"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "왕족"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "암살자"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "스파이"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "제정"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "알렉산더 대왕"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "율리우스 카이사르"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "니달리"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "바이폴라"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "천재"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "소년"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "애니메이션"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "우치하 이타치"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "반요"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "사회 자유주의"]]]]
                    2
                    [:tbody
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "세일러문"]]]]
                    3
                    [:tbody
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "저스티스 건담"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "검의 노래"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "왕족"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "집행자"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "아나키즘"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "천재"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "소년"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "애니메이션"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "순혈"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "신자유주의"]]]])]]
                [:div.background-grey {:style {:display (if @(::userprofile-overflow? states)
                                                          ""
                                                          "none")
                                               :width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                               :height "1px"
                                               :position "absolute"
                                               :top (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 1) "px")}}]]
               [:table.tableblock.frame-all.grid-all.stretch {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                                                      :margin-bottom "3em"}}
                [:tbody
                 [:tr
                  [:td.tableblock.halign-center.valign-center {:colSpan 2}
                   [:div {:style {:color (if (= @(:papercompany.utopia-lite.doms.main/selected-user-no states) @(::user-no states))
                                           (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                             "rgba(255, 255, 255, 0.1)"
                                             "rgba(0, 0, 0, 0.1)")
                                           "rgb(64, 128, 128)")
                                  :cursor (if (= @(:papercompany.utopia-lite.doms.main/selected-user-no states) @(::user-no states))
                                            ""
                                            "pointer")}
                          :on-click #(when (not (= @(:papercompany.utopia-lite.doms.main/selected-user-no states) @(::user-no states)))
                                       (user-service/select states @(::user-no states)))}
                    "소통하기"]]]
                 [:tr
                  [:td.tableblock.halign-center.valign-center {:colSpan 2}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(js/alert "아직 구현되지 않았습니다")}
                    "즐겨찾기 해제"]]]
                 [:tr
                  [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(route/route "/usercoin1")}
                    "백학코인"]]
                  [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(route/route "/usershare1list1")}
                    "백학문"]]]
                 [:tr
                  [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(route/route "/userrecord3list")}
                    "앵화기록"]]
                  [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(route/route "/userrecord8list")}
                    "만월기록"]]]]]]
              [])
            [[:table.tableblock.frame-all.grid-all.stretch {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                                                    :margin-bottom "0px"}}
              [:tbody
               [:tr
                [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                 [:div {:style {:color (if (= @(::search-mode states) 0)
                                         "#ba3925"
                                         "rgb(64, 128, 128)")
                                :cursor (if (= @(::search-mode states) 0)
                                          ""
                                          "pointer")}
                        :on-click #(when (not (= @(::search-mode states) 0))
                                     (reset! (::search-mode states) 0)
                                     (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))}
                  "즐겨찾기"]]
                [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                 [:div {:style {:color (if (= @(::search-mode states) 1)
                                         "#ba3925"
                                         "rgb(64, 128, 128)")
                                :cursor (if (= @(::search-mode states) 1)
                                          ""
                                          "pointer")}
                        :on-click #(js/alert "아직 구현되지 않았습니다")
                        ;; (when (not (= @(::search-mode states) 1))
                                   ;;   (reset! (::search-mode states) 1))
                        }
                  "검색"]]]]]
             (case @(::search-mode states)
               0
               [:div#userprofile-search-overflow {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                                          :max-height "40em"
                                                          :position "relative"
                                                          :top "-1px"
                                                          :border (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                                    "1px solid #424242"
                                                                    "1px solid #dedede")
                                                          :border-width "0px 0px 1px 0px"
                                                          :overflow-y "auto"}}
                [(if @(::userprofile-search-overflow? states)
                   :table.tableblock.grid-all.stretch.no-bottom-border-2
                   :table.tableblock.frame-all.grid-all.stretch)
                 {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                          :margin-bottom "0px"}}
                 [:tbody
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "20%"}
                                                                :rowSpan 2}
                    [:img {:style {:width "100%"
                                   :height "100%"
                                   :border-radius "1em"}
                           :src "/images/freedom-gundam.jpg"
                           :on-click #(do
                                        (reset! (::user-no states) 1)
                                        (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) 0))
                                        (reset! (::page states) 1)
                                        (reset! (::max-page states) (case @(::user-no states)
                                                                      1 3
                                                                      2 1
                                                                      3 3))
                                        (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)
                                        (js/setTimeout (fn []
                                                         (.scrollTo
                                                          js/window
                                                          #js {:top 0
                                                               :behavior "smooth"})
                                                         ((scroll-top-handler states))) 50))
                           :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
                   [:td.tableblock.halign-center.valign-center {:style {:width "80%"}}
                    [:div.user "STANDARD-TYPE"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "800%"}}
                    [:div.user {:style {:font-size "0.8em"
                                        :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                 "rgba(255,255, 255, 0.3)"
                                                 "rgba(0, 0, 0, 0.3)")}}
                     "#프리덤 건담 #칼의 노래 #왕족 #암살자 #제정"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "20%"}
                                                                :rowSpan 2}
                    [:img {:style {:width "100%"
                                   :height "100%"
                                   :border-radius "1em"}
                           :src "/images/blossom.png"
                           :on-click #(do
                                        (reset! (::user-no states) 2)
                                        (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) 0))
                                        (reset! (::page states) 1)
                                        (reset! (::max-page states) (case @(::user-no states)
                                                                      1 3
                                                                      2 1
                                                                      3 1))
                                        (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)
                                        (js/setTimeout (fn []
                                                         (.scrollTo
                                                          js/window
                                                          #js {:top 0
                                                               :behavior "smooth"})
                                                         ((scroll-top-handler states))) 50))
                           :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
                   [:td.tableblock.halign-center.valign-center {:style {:width "80%"}}
                    [:div.user "사쿠라"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "800%"}}
                    [:div.user {:style {:font-size "0.8em"
                                        :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                 "rgba(255,255, 255, 0.3)"
                                                 "rgba(0, 0, 0, 0.3)")}}
                     "#세일러문"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "20%"}
                                                                :rowSpan 2}
                    [:img {:style {:width "100%"
                                   :height "100%"
                                   :border-radius "1em"}
                           :src "/images/destroy-pheonix.png"
                           :on-click #(do
                                        (reset! (::user-no states) 3)
                                        (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) 0))
                                        (reset! (::page states) 1)
                                        (reset! (::max-page states) (case @(::user-no states)
                                                                      1 3
                                                                      2 1
                                                                      3 3))
                                        (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)
                                        (js/setTimeout (fn []
                                                         (.scrollTo
                                                          js/window
                                                          #js {:top 0
                                                               :behavior "smooth"})
                                                         ((scroll-top-handler states))) 50))
                           :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
                   [:td.tableblock.halign-center.valign-center {:style {:width "80%"}}
                    [:div.user "디파티드"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "800%"}}
                    [:div.user {:style {:font-size "0.8em"
                                        :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                 "rgba(255,255, 255, 0.3)"
                                                 "rgba(0, 0, 0, 0.3)")}}
                     "#저스티스 건담 #검의 노래 #왕족 #집행자 #아나키즘"]]]]]])]))
                                        ;-------------------------------------------------------------------------------------------------
      (vec (concat
            [:div {:style {:display "flex"
                           :flex-direction "column"
                           :width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                           :position "relative"}}]
            (if @(::user-no states)
              [[:div {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                              :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")
                              :position "relative"
                              :top "0px"
                              :left (str (cond
                                           (> @(::transition-x states) 20)
                                           (- 50 (/ 1000 @(::transition-x states)))
                                           (< @(::transition-x states) -20)
                                           (- -50 (/ 1000 @(::transition-x states)))
                                           (= @(::transition-x states) 0)
                                           0) "px")
                              :margin-bottom "1em"}}
                [:img.frame {:style {:width (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")
                                     :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")
                                     :position "absolute"
                                     :top "0px"
                                     :left "0px"}
                             :src @(::profile-image-url states)
                             :on-click #(when (not (= @(:papercompany.utopia-lite.doms.main/selected-user-no states) @(::user-no states)))
                                          (user-service/select states @(::user-no states)))
                             :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)
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
                                                     (.getElementById js/document "userprofile-scroll")
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
                                                    (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) (dec @(::page states))))
                                                    (reset! (::max-page states) (case @(::user-no states)
                                                                                  1 3
                                                                                  2 1
                                                                                  3 3))
                                                    (reset! (::touch-start-x states) nil)
                                                    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))
                                                (< touch-end-x (-
                                                                @(::touch-start-x states)
                                                                100))
                                                (do (swap! (::page states)
                                                           (fn [index] (inc (mod index @(::max-page states)))))
                                                    (.scrollTo
                                                     (.getElementById js/document "userprofile-scroll")
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
                                                    (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) (dec @(::page states))))
                                                    (reset! (::max-page states) (case @(::user-no states)
                                                                                  1 3
                                                                                  2 1
                                                                                  3 3))
                                                    (reset! (::touch-start-x states) nil)
                                                    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))
                                              (reset! (::transition-x states) 0))}]
                [:div {:style {:width (str (+ (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 2) "px")
                               :height (str @(::userprofile-name-height states) "px")
                               :position "absolute"
                               :top "0px"
                               :left (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 1) "px")
                               :border "1px solid rgba(0, 0, 0, 0)"
                               :border-width "0px 1px 0px 1px"}}
                 [:table#userprofile-name.tableblock.grid-all.stretch.no-left-bottom-border {:style {:width "100%"
                                                                                                     :position "absolute"
                                                                                                     :top "0px"
                                                                                                     :left "-1px"
                                                                                                     :margin "0px"}}
                  [:tbody
                   [:tr
                    [:td.tableblock.halign-center.valign-center
                     [:div.user {:style {:color "rgba(186, 57, 37, 0.5)"}}
                      [:strong (case @(::user-no states)
                                 1 "STANDARD-TYPE"
                                 2 "사쿠라"
                                 3 "디파티드")]]]]]]]
                [:div.background-grey {:style {:width (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 1) "px")
                                               :height "1px"
                                               :position "absolute"
                                               :top (str (- @(::userprofile-name-height states) 1) "px")
                                               :left (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")}}]
                [:div#userprofile-overflow {:style {:width (str (+ (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 2) "px")
                                                    :height (str (-
                                                                  (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2)
                                                                  @(::userprofile-name-height states)
                                                                  -1) "px")
                                                    :position "absolute"
                                                    :top (str (- @(::userprofile-name-height states) 1) "px")
                                                    :left (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 1) "px")
                                                    :border "1px solid rgba(0, 0, 0, 0)"
                                                    :overflow-y "auto"}}
                 [(if @(::userprofile-overflow? states)
                    :table.tableblock.grid-all.stretch.no-bottom-border
                    :table.tableblock..frame-all.grid-all.stretch) {:style {:width "100%"
                                                                            :position "absolute"
                                                                            :top "-1px"
                                                                            :left "-1px"
                                                                            :margin "0px"}}
                  (case @(::user-no states)
                    1
                    [:tbody
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "프리덤 건담"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "칼의 노래"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "왕족"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "암살자"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "스파이"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "제정"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "알렉산더 대왕"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "율리우스 카이사르"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "니달리"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "바이폴라"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "천재"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "소년"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "애니메이션"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "우치하 이타치"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "반요"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "사회 자유주의"]]]]
                    2
                    [:tbody
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "세일러문"]]]]
                    3
                    [:tbody
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "저스티스 건담"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "검의 노래"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "왕족"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "집행자"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "아나키즘"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "천재"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "소년"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "애니메이션"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "순혈"]]]
                     [:tr
                      [:td.tableblock.halign-center.valign-center
                       [:div.user "신자유주의"]]]])]]
                [:div.background-grey {:style {:display (if @(::userprofile-overflow? states)
                                                          ""
                                                          "none")
                                               :width (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 1) "px")
                                               :height "1px"
                                               :position "absolute"
                                               :top (str (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 1) "px")
                                               :left (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")}}]]
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
                                     (.getElementById js/document "userplaylists-scroll")
                                     #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                  -120
                                                  -160)
                                          :behavior "smooth"}))} "<"]
                 (vec (concat [:div#userprofile-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
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
                                                          (.getElementById js/document "userprofile-scroll")
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
                                                         (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) (dec @(::page states))))
                                                         (reset! (::max-page states) (case @(:papercompany.utopia-lite.doms.main/selected-user-no states)
                                                                                       1 3
                                                                                       2 1
                                                                                       3 3))
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
                                     (.getElementById js/document "userproflie-scroll")
                                     #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                  120
                                                  160)
                                          :behavior "smooth"}))} ">"]]]
               [:table.tableblock.frame-all.grid-all.stretch {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                                                      :margin-top "1em"
                                                                      :margin-bottom "3em"}}
                [:tbody
                 [:tr
                  [:td.tableblock.halign-center.valign-center {:colSpan 2}
                   [:div {:style {:color (if (= @(:papercompany.utopia-lite.doms.main/selected-user-no states) @(::user-no states))
                                           (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                             "rgba(255, 255, 255, 0.1)"
                                             "rgba(0, 0, 0, 0.1)")
                                           "rgb(64, 128, 128)")
                                  :cursor (if (= @(:papercompany.utopia-lite.doms.main/selected-user-no states) @(::user-no states))
                                            ""
                                            "pointer")}
                          :on-click #(when (not (= @(:papercompany.utopia-lite.doms.main/selected-user-no states) @(::user-no states)))
                                       (user-service/select states @(::user-no states)))}
                    "소통하기"]]]
                 [:tr
                  [:td.tableblock.halign-center.valign-center {:colSpan 2}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(js/alert "아직 구현되지 않았습니다")}
                    "즐겨찾기 해제"]]]
                 [:tr
                  [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(route/route "/usercoin1")}
                    "백학코인"]]
                  [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(route/route "/usershare1list1")}
                    "백학문"]]]
                 [:tr
                  [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(route/route "/userrecord3list")}
                    "앵화기록"]]
                  [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                   [:div {:style {:color "rgb(64, 128, 128)"
                                  :cursor "pointer"}
                          :on-click #(route/route "/userrecord8list")}
                    "만월기록"]]]]]]
              [])
            [[:table.tableblock.frame-all.grid-all.stretch {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                                                    :margin-bottom "0px"}}
              [:tbody
               [:tr
                [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                 [:div {:style {:color (if (= @(::search-mode states) 0)
                                         "#ba3925"
                                         "rgb(64, 128, 128)")
                                :cursor (if (= @(::search-mode states) 0)
                                          ""
                                          "pointer")}
                        :on-click #(when (not (= @(::search-mode states) 0))
                                     (reset! (::search-mode states) 0)
                                     (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))}
                  "즐겨찾기"]]
                [:td.tableblock.halign-center.valign-center {:style {:width "50%"}}
                 [:div {:style {:color (if (= @(::search-mode states) 1)
                                         "#ba3925"
                                         "rgb(64, 128, 128)")
                                :cursor (if (= @(::search-mode states) 1)
                                          ""
                                          "pointer")}
                        :on-click #(js/alert "아직 구현되지 않았습니다")
                        ;; (when (not (= @(::search-mode states) 1))
                        ;;   (reset! (::search-mode states) 1))
                        }
                  "검색"]]]]]
             (case @(::search-mode states)
               0
               [:div#userprofile-search-overflow {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                                          :max-height "120em"
                                                          :position "relative"
                                                          :top "-1px"
                                                          :border (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                                    "1px solid #424242"
                                                                    "1px solid #dedede")
                                                          :border-width "0px 0px 1px 0px"
                                                          :overflow-y "auto"}}
                [(if @(::userprofile-search-overflow? states)
                   :table.tableblock.grid-all.stretch.no-bottom-border-2
                   :table.tableblock.frame-all.grid-all.stretch)
                 {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                          :margin-bottom "0px"}}
                 [:tbody
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "20%"}
                                                                :rowSpan 2}
                    [:img {:style {:width "100%"
                                   :height "100%"
                                   :border-radius "1em"}
                           :src "/images/freedom-gundam.jpg"
                           :on-click #(do
                                        (reset! (::user-no states) 1)
                                        (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) 0))
                                        (reset! (::page states) 1)
                                        (reset! (::max-page states) (case @(::user-no states)
                                                                      1 3
                                                                      2 1
                                                                      3 3))
                                        (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)
                                        (js/setTimeout (fn []
                                                         (.scrollTo
                                                          js/window
                                                          #js {:top 0
                                                               :behavior "smooth"})
                                                         ((scroll-top-handler states))) 50))
                           :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
                   [:td.tableblock.halign-center.valign-center {:style {:width "80%"}}
                    [:div.user "STANDARD-TYPE"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "800%"}}
                    [:div.user {:style {:font-size "0.8em"
                                        :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                 "rgba(255,255, 255, 0.3)"
                                                 "rgba(0, 0, 0, 0.3)")}}
                     "#프리덤 건담 #칼의 노래 #왕족 #암살자 #제정"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "20%"}
                                                                :rowSpan 2}
                    [:img {:style {:width "100%"
                                   :height "100%"
                                   :border-radius "1em"}
                           :src "/images/blossom.png"
                           :on-click #(do
                                        (reset! (::user-no states) 2)
                                        (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) 0))
                                        (reset! (::page states) 1)
                                        (reset! (::max-page states) (case @(::user-no states)
                                                                      1 3
                                                                      2 1
                                                                      3 3))
                                        (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)
                                        (js/setTimeout (fn []
                                                         (.scrollTo
                                                          js/window
                                                          #js {:top 0
                                                               :behavior "smooth"})
                                                         ((scroll-top-handler states))) 50))
                           :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
                   [:td.tableblock.halign-center.valign-center {:style {:width "80%"}}
                    [:div.user "사쿠라"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "800%"}}
                    [:div.user {:style {:font-size "0.8em"
                                        :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                 "rgba(255,255, 255, 0.3)"
                                                 "rgba(0, 0, 0, 0.3)")}}
                     "#세일러문"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "20%"}
                                                                :rowSpan 2}
                    [:img {:style {:width "100%"
                                   :height "100%"
                                   :border-radius "1em"}
                           :src "/images/destroy-pheonix.png"
                           :on-click #(do
                                        (reset! (::user-no states) 3)
                                        (reset! (::profile-image-url states) ((profile-image-urls @(::user-no states)) 0))
                                        (reset! (::page states) 1)
                                        (reset! (::max-page states) (case @(::user-no states)
                                                                      1 3
                                                                      2 1
                                                                      3 3))
                                        (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)
                                        (js/setTimeout (fn []
                                                         (.scrollTo
                                                          js/window
                                                          #js {:top 0
                                                               :behavior "smooth"})
                                                         ((scroll-top-handler states))) 50))
                           :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
                   [:td.tableblock.halign-center.valign-center {:style {:width "80%"}}
                    [:div.user "디파티드"]]]
                  [:tr
                   [:td.tableblock.halign-center.valign-center {:style {:width "800%"}}
                    [:div.user {:style {:font-size "0.8em"
                                        :color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                 "rgba(255,255, 255, 0.3)"
                                                 "rgba(0, 0, 0, 0.3)")}}
                     "#저스티스 건담 #검의 노래 #왕족 #집행자 #아나키즘"]]]]]])])))
                                        ;---------------------------------------------------------------------------------------------------
    (finally
      (reset! (:papercompany.utopia-lite.doms.main/resize-handler states) (fn []))
      (reset! (:papercompany.utopia-lite.doms.main/scroll-top-handler states) (fn [])))))
