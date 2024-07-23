(ns papercompany.utopia-lite.doms.main
  (:require
   [papercompany.utopia-lite.services.music :as music-service]
   [papercompany.utopia-lite.services.user :as user-service]
   [papercompany.utopia-lite.route :as route]
   [reagent.core :as reagent]))

(set! *warn-on-infer* false)

(def toc2-on-image-url "/images/toc2-on.png")
(def toc2-off-image-url "/images/toc2-off.png")

(def mobile? (reagent/atom false))
(def mobile?? (reagent/atom false))
(def dark-mode? (reagent/atom false))
(def toc2-on? (reagent/atom false))
(def content-width (reagent/atom 0))
(def content-height (reagent/atom 0))
(def resize-handler (reagent/atom (fn [])))
(def scroll-top-handler (reagent/atom (fn [])))

(defn ^:export resize-content []
  (reset! content-height 0)
  (if @mobile?
    (if @dark-mode?
      (if @toc2-on?
        (set! (.-backgroundColor (.-style (.-documentElement js/document))) "#323232")
        (set! (.-backgroundColor (.-style (.-documentElement js/document))) "black"))
      (if @toc2-on?
        (set! (.-backgroundColor (.-style (.-documentElement js/document))) "#f8f8f7")
        (set! (.-backgroundColor (.-style (.-documentElement js/document))) "white")))
    (if @dark-mode?
      (set! (.-backgroundColor (.-style (.-documentElement js/document))) "black")
      (set! (.-backgroundColor (.-style (.-documentElement js/document))) "white")))
  (reagent/flush)
  (let [content-computed-style (.getComputedStyle js/window (.getElementById js/document "content"))
        content-width-string (.-width content-computed-style)
        content-width-float (js/parseFloat
                             (subs content-width-string 0 (- (count content-width-string) 2)))]
    (reset! mobile? (< (.-innerWidth js/window) 675))
    (reset! mobile?? (< content-width-float 675))
    (reagent/flush)
    (let [content-computed-style (.getComputedStyle js/window (.getElementById js/document "content"))
          html-height-string (.-height (.getComputedStyle js/window (.-documentElement js/document)))
          content-width-string (.-width content-computed-style)
          content-width-float (js/parseFloat
                               (subs content-width-string 0 (- (count content-width-string) 2)))
          content-height-string (.-height content-computed-style)
          calculated-content-height (js/parseFloat
                                     (subs content-height-string 0 (- (count content-height-string) 2)))
          diff (-
                (.-innerHeight js/window)
                (js/parseFloat
                 (subs html-height-string 0 (- (count html-height-string) 2))))]
      (reset! content-width content-width-float)
      (when (> diff 0)
        (reset! content-height (+ calculated-content-height diff)))))
  (reagent/flush)
  (@resize-handler))

(defn toggle-display [keyword states]
  (fn [event]
    (swap! (::display? states) #(assoc % keyword (not (get % keyword))))))

(defn toc2-on [states]
  (reset! (::toc2-on? states) true)
  (js/setTimeout resize-content 25)
  (when @(::mobile? states)
    (if @(::dark-mode? states)
      (set! (.-backgroundColor (.-style (.-documentElement js/document))) "#323232")
      (set! (.-backgroundColor (.-style (.-documentElement js/document))) "#f8f8f7"))))

(defn toc2-off [states]
  (reset! (::toc2-on? states) false)
  (js/setTimeout resize-content 25)
  (when @(::mobile? states)
    (if @(::dark-mode? states)
      (set! (.-backgroundColor (.-style (.-documentElement js/document))) "black")
      (set! (.-backgroundColor (.-style (.-documentElement js/document))) "white"))))

(defn dom [states]
  (reagent/with-let [states (merge states {::name (reagent/atom "")
                                           ::details (reagent/atom "")
                                           ::mobile? mobile?
                                           ::mobile?? mobile??
                                           ::dark-mode? dark-mode?
                                           ::toc2-on? toc2-on?
                                           ::content-width content-width
                                           ::content-height content-height
                                           ::display? (reagent/atom {:account false
                                                                     :profile false
                                                                     :coin false
                                                                     :share false
                                                                     :share1 false
                                                                     :record false
                                                                     :january false
                                                                     :plaza1 false
                                                                     :march false
                                                                     :plaza3 false
                                                                     :august false
                                                                     :plaza8 false})
                                           ::init (reagent/atom (fn [_]))
                                           ::resize-content resize-content
                                           ::resize-handler resize-handler
                                           ::scroll-top-handler scroll-top-handler
                                        ;---------------------------------------------------------
                                           ::userprofile-params (reagent/atom nil)
                                           ::userplaylist-params (reagent/atom nil)
                                           ::userplaylists-params (reagent/atom nil)
                                        ;---------------------------------------------------------
                                           ::login-user-no (reagent/atom nil)
                                           ::login-user-id (reagent/atom nil)
                                           ::login-user-nickname (reagent/atom nil)
                                           ::login-user-profile-image-url (reagent/atom nil)
                                           ::selected-user-no (reagent/atom nil)
                                           ::music-pending? (reagent/atom false)
                                           ::music-playing? (reagent/atom false)
                                           ::music-playlist (reagent/atom [])})
                     _ (swap! (:papercompany.utopia-lite.core/loaded states) assoc 1 true)]
    (if (reduce #(and %1 %2) true @(:papercompany.utopia-lite.core/loaded states))
      (if @mobile?
        [(if @(::dark-mode? states)
           :div#main.book.toc2.dark
           :div#main.book.toc2) {:style {:padding-left (if @(::toc2-on? states)
                                                         ""
                                                         "0em")}}
         [:div#header.mobile {:style {:display (if @(::toc2-on? states)
                                                 "none"
                                                 "")
                                      :overflow "hidden"}}
          [:h1
           [:div {:style {:display "flex"
                          :justify-content "space-between"
                          :align-items "center"}}
            [:span {:style {:cursor "pointer"}
                    :on-click @(::init states)}
             @(::name states)]
            [:img {:style {:width "1em"
                           :height "1em"
                           :filter (if @(::dark-mode? states)
                                     "invert(100%)"
                                     "")
                           :cursor "pointer"}
                   :src toc2-on-image-url
                   :on-click #(toc2-on states)
                                }]]]
          [:div#details {:style {:font-size "15px"
                                 :color (if @(::dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
           [:span.revnumber @(::details states)]]]
         [:div#toc.toc2 {:style {:display (if @(::toc2-on? states)
                                            ""
                                            "none")
                                 :width "100%"
                                 :min-height (.-innerHeight js/window)
                                 :position "absolute"
                                 :overflow-x "hidden"
                                 :font-size "25px"
                                 :border-right "1px solid rgba(0, 0, 0, 0)"
                                 :z-index "1002"}}
          [:div {:style {:display "flex"
                         :justify-content "space-between"}}
           [:div#toctitle {:style {:cursor "pointer"}
                           :on-click #(do
                                        (route/route "/")
                                        (toc2-off states))} "유토피아-Lite"]
           [:img {:style {:width "2em"
                          :height "2em"
                          :cursor "pointer"}
                  :src toc2-off-image-url
                  :on-click #(toc2-off states)}]]
          [:br {:style {:font-size "1.2em"}}]
          [:div {:style {:display "flex"
                         :justify-content "space-between"
                         :align-items "center"}}
           (if @(::selected-user-no states)
             [:div {:style {:display "flex"
                            :flex-direction "column"
                            :align-items "center"}}
              [:img {:style {:width "2.5em"
                             :height "2.5em"
                             :border-radius "50%"
                             :cursor "pointer"}
                     :src (case @(::selected-user-no states)
                            1 "images/freedom-gundam.jpg"
                            2 "images/blossom.png"
                            3 "images/destroy-pheonix.png")
                     :on-click #(do (reset! (::userprofile-params states) {:user-no @(::selected-user-no states)})
                                    (route/route "/userprofile")
                                    (toc2-off states))}]
              [:span {:style {:font-size "0.6em"
                              :color "rgb(128, 128, 128)"}}
               "소통중"]]
             [:div {:style {:display "flex"
                            :flex-direction "column"
                            :align-items  "center"}}
              [:img {:style {:width "2.5em"
                             :height "2.5em"
                             :cursor "pointer"}
                     :src "images/null-user.png"
                     :on-click #(do
                                  (reset! (::userprofile-params states) {:user-no nil})
                                  (route/route "/userprofile")
                                  (toc2-off states))}]
              [:span {:style {:font-size "0.6em"
                              :color "rgb(128, 128, 128)"}}
               "유저검색"]])
           (if @(::selected-user-no states)
             (if @(::music-playing? states)
               [:img {:style {:width "2.5em"
                              :height "2.5em"
                              :cursor "pointer"}
                      :src "images/stop.png"
                      :on-click #(music-service/stop states)}]
               [:img {:style {:width "2.5em"
                              :height "2.5em"
                              :cursor "pointer"}
                      :src "images/play.png"
                      :on-click #(music-service/play states)}])
             [:div {:style {:width "2.5em"
                            :height "2.5em"}}])
           (if @(::login-user-no states)
             [:div {:style {:display "flex"
                            :flex-direction "column"
                            :align-items "center"}}
              [:img {:style {:width "2.5em"
                             :height "2.5em"
                             :border-radius "50%"
                             :cursor "pointer"}
                     :src (case @(::login-user-no states)
                            1 "images/freedom-gundam.jpg"
                            2 "images/blossom.png"
                            3 "images/destroy-pheonix.png")
                     :on-click #(do
                                  (reset! (::userprofile-params states) {:user-no @(::login-user-no states)})
                                  (route/route "/userprofile")
                                  (toc2-off states))}]
              [:span {:style {:font-size "0.6em"
                              :color "rgb(128, 128, 128)"}}
               "로그인"]]
             [:div {:style {:display "flex"
                            :flex-direction "column"
                            :align-items "center"}}
              [:img {:style {:width "2.5em"
                             :height "2.5em"
                             :cursor "pointer"}
                     :src "images/null-user.png"
                     :on-click #(do
                                  (route/route "/login")
                                  (toc2-off states))}]
              [:span {:style {:font-size "0.6em"
                              :color "rgb(128, 128, 128)"}}
               "로그인"]])]
          [:br {:style {:font-size "0.13em"}}]
          [:ul.sectlevel1
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :account states)} "계정"]
            [:ul.sectlevel2 {:style {:display (if (:account @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/login")
                                  (toc2-off states))} "로그인"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/enroll")
                                  (toc2-off states))} "회원가입"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/userinfo")
                                  (toc2-off states))} "유저정보"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/login")
                                  (toc2-off states))} "로그아웃"]]]]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click #(do
                                (route/route "/notifications")
                                (toc2-off states))} "알림"]]
           [:br]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click #(do
                                (route/route "/contracts")
                                (toc2-off states))} "거래소"]]
           [:br]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :profile states)} "소통"]
            [:ul.sectlevel2 {:style {:display (if (:profile @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (reset! (::userprofile-params states) {:user-no @(::selected-user-no states)})
                                  (route/route "/userprofile")
                                  (toc2-off states))} "프로필"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (reset! (::userplaylist-params states) {:playlist @(::music-playlist states)})
                                  (route/route "/userplaylist")
                                  (toc2-off states))} "플레이리스트"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/userportfolio")
                                  (toc2-off states))} "포트폴리오"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :coin states)} "코인"]
              [:ul.sectlevel3 {:style {:display (if (:coin @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/usercoin1")
                                    (toc2-off states))} "백학코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/usercoin3list")
                                    (toc2-off states))} "앵화코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/usercoin8list")
                                    (toc2-off states))} "만월코인"]]]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :share states)} "문예"]
              [:ul.sectlevel3 {:style {:display (if (:share @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click (toggle-display :share1 states)} "백학문"]
                [:ul.sectlevel4 {:style {:display (if (:share1 @(::display? states)) "" "none")}}
                 [:div {:style {:color "rgba(184, 134, 11, 0.5)"
                                :cursor "pointer"}
                        :on-click #(do
                                     (route/route "/usershare1list1")
                                     (toc2-off states))} "빛"]
                 [:div {:style {:color "rgba(0, 0, 0, 0.5)"
                                :cursor "pointer"}
                        :on-click #(do
                                     (route/route "/usershare1list2")
                                     (toc2-off states))} "어둠"]
                 [:div {:style {:color "rgba(128, 0, 0, 0.5)"
                                :cursor "pointer"}
                        :on-click #(do
                                     (route/route "/usershare1list3")
                                     (toc2-off states))} "불"]
                 [:div {:style {:color "rgba(0, 0, 128, 0.5)"
                                :cursor "pointer"}
                        :on-click #(do
                                     (route/route "/usershare1list4")
                                     (toc2-off states))} "물"]
                 [:div {:style {:color "rgba(0, 128, 0, 0.5)"
                                :cursor "pointer"}
                        :on-click #(do
                                     (route/route "/usershare1list5")
                                     (toc2-off states))} "바람"]
                 [:div {:style {:color "rgba(165, 42, 42, 0.5)"
                                :cursor "pointer"}
                        :on-click #(do
                                     (route/route "/usershare1list6")
                                     (toc2-off states))} "땅"]]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/usershare3list")
                                    (toc2-off states))} "앵화문"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/usershare8list")
                                    (toc2-off states))} "만월문"]]]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :record states)} "기록"]
              [:ul.sectlevel3 {:style {:display (if (:record @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/userrecord3list")
                                    (toc2-off states))} "앵화기록"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/userrecord8list")
                                    (toc2-off states))} "만월기록"]]]]]]
           [:br]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :january states)} "1월"]
            [:ul.sectlevel2 {:style {:display (if (:january @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/crane")
                                  (toc2-off states))} "두루미"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/algebra1")
                                  (toc2-off states))} "백학대수"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :plaza1 states)} "광장"]
              [:ul.sectlevel3 {:style {:display (if (:plaza1 @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/coin1plaza")
                                    (toc2-off states))} "코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/share1plaza")
                                    (toc2-off states))} "문예"]]]]]]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :march states)} "3월"]
            [:ul.sectlevel2 {:style {:display (if (:march @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/blossom")
                                  (toc2-off states))} "벚꽃"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/analysis3")
                                  (toc2-off states))} "앵화해석"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :plaza3 states)} "광장"]
              [:ul.sectlevel3 {:style {:display (if (:plaza3 @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/coin3plaza")
                                    (toc2-off states))} "코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/share3plaza")
                                    (toc2-off states))} "문예"]]]]]]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :august states)} "8월"]
            [:ul.sectlevel2 {:style {:display (if (:august @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/moon")
                                  (toc2-off states))} "보름달"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (route/route "/analysis8")
                                  (toc2-off states))} "만월해석"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :plaza8 states)} "광장"]
              [:ul.sectlevel3 {:style {:display (if (:plaza8 @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/coin8plaza")
                                    (toc2-off states))} "코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(do
                                    (route/route "/share8plaza")
                                    (toc2-off states))} "문예"]]]]]]
           [:br]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click #(do
                                (route/route "/history")
                                (toc2-off states))} "역사"]]]]
         [:div#content.mobile {:style {:display (if @(::toc2-on? states)
                                                  "none"
                                                  "")
                                       :min-height (if @content-height
                                                     (str @content-height "px")
                                                     "")
                                       :overflow-x "hidden"}}
          [@(:papercompany.utopia-lite.core/route states) states]]
         [:div#footer.mobile {:style {:display (if @(::toc2-on? states)
                                                 "none"
                                                 "")
                                      :overflow "hidden"}}
          [:div#footer-text {:style {:display "flex"
                                     :justify-content "space-between"
                                     :width "100%"}}
           [:div {:style {:cursor "pointer"}
                  :on-click #(do
                               (.scrollTo
                                js/window
                                #js {:top 0
                                     :behavior "smooth"})
                               (@scroll-top-handler))}
            "Powered By PaperCompany"]
           [:div {:style {:cursor "pointer"}
                  :on-click #(do
                               (if @(::dark-mode? states)
                                 (set! (.-backgroundColor (.-style (.-documentElement js/document))) "white")
                                 (set! (.-backgroundColor (.-style (.-documentElement js/document))) "black"))
                               (swap! (::dark-mode? states) not))}
            "다크모드"]]]]
                                        ;--------------------------------------------------------------------------------------------------------------------------------------------------
        [(if @(::dark-mode? states)
           :div#main.book.toc2.dark
           :div#main.book.toc2) {:style {:padding-left (if @(::toc2-on? states)
                                                        ""
                                                        "0em")}}
         [:div#header
          [:h1
           [:div {:style {:display "flex"
                          :justify-content "space-between"
                          :align-items "center"
                          :overflow "hidden"}}
            [:span {:style {:cursor "pointer"}
                    :on-click @(::init states)}
             @(::name states)]
            [:img {:style {:display (if @(::toc2-on? states)
                                      "none"
                                      "")
                           :width "1em"
                           :height "1em"
                           :filter (if @(::dark-mode? states)
                                     "invert(100%)"
                                     "")
                           :cursor "pointer"}
                   :src toc2-on-image-url
                   :on-click #(toc2-on states)}]]]
          [:div#details {:style {:font-size "15px"
                                 :color (if @(::dark-mode? states)
                                          "rgba(255, 255, 255, 0.3)"
                                          "rgba(0, 0, 0, 0.3)")}}
           [:span.revnumber @(::details states)]]]
         [:div#toc.toc2 {:style {:display (if @(::toc2-on? states)
                                            ""
                                            "none")
                                 :overflow-x "hidden"}}
          [:div {:style {:display "flex"
                         :justify-content "space-between"
                         :margin-top "1em"}}
           [:div#toctitle {:style {:padding-left "0.25em"
                                   :cursor "pointer"}
                           :on-click #(route/route "/")} "유토피아-Lite"]
           [:img {:style {:width "2em"
                          :height "2em"
                          :cursor "pointer"}
                  :src toc2-off-image-url
                  :on-click #(toc2-off states)}]]
          [:br {:style {:font-size "0.8em"}}]
          [:div {:style {:display "flex"
                         :justify-content "space-between"
                         :align-items "center"}}
           (if @(::selected-user-no states)
             [:div {:style {:display "flex"
                            :flex-direction "column"
                            :align-items "center"}}
              [:img {:style {:display "inline-block"
                             :width "2.5em"
                             :height "2.5em"
                             :border-radius "50%"
                             :cursor "pointer"}
                     :src (case @(::selected-user-no states)
                            1 "images/freedom-gundam.jpg"
                            2 "images/blossom.png"
                            3 "images/destroy-pheonix.png")
                     :on-click #(do
                                  (reset! (::userprofile-params states) {:user-no @(::selected-user-no states)})
                                  (route/route "/userprofile"))}]
              [:span {:style {:font-size "0.6em"
                              :color "rgb(128, 128, 128)"}}
               "소통중"]]
             [:div {:style {:display "flex"
                            :flex-direction "column"
                            :align-items  "center"}}
              [:img {:style {:display "inline-block"
                             :width "2.5em"
                             :height "2.5em"
                             :cursor "pointer"}
                     :src "images/null-user.png"
                     :on-click #(do
                                  (reset! (::userprofile-params states) {:user-no nil})
                                  (route/route "/userprofile"))}]
              [:span {:style {:font-size "0.6em"
                              :color "rgb(128, 128, 128)"}}
               "유저검색"]])
           (if @(::selected-user-no states)
             (if @(::music-playing? states)
               [:img {:style {:width "2.5em"
                              :height "2.5em"
                              :cursor "pointer"}
                      :src "images/stop.png"
                      :on-click #(music-service/stop states)}]
               [:img {:style {:width "2.5em"
                              :height "2.5em"
                              :cursor "pointer"}
                      :src "images/play.png"
                      :on-click #(music-service/play states)}])
             [:div {:style {:width "2.5em"
                            :height "2.5em"}}])
           (if @(::login-user-no states)
             [:div {:style {:display "flex"
                            :flex-direction "column"
                            :align-items "center"}}
              [:img {:style {:width "2.5em"
                             :height "2.5em"
                             :border-radius "50%"
                             :cursor "pointer"}
                     :src (case @(::login-user-no states)
                            1 "images/freedom-gundam.jpg"
                            2 "images/blossom.png"
                            3 "images/destroy-pheonix.png")
                     :on-click #(do
                                  (reset! (::userprofile-params states) {:user-no @(::login-user-no states)})
                                  (route/route "/userprofile"))}]
              [:span {:style {:font-size "0.6em"
                              :color "rgb(128, 128, 128)"}}
               "로그인"]]
             [:div {:style {:display "flex"
                            :flex-direction "column"
                            :align-items "center"}}
              [:img {:style {:width "2.5em"
                             :height "2.5em"
                             :cursor "pointer"}
                     :src "images/null-user.png"
                     :on-click #(route/route "/login")}]
              [:span {:style {:font-size "0.6em"
                              :color "rgb(128, 128, 128)"}}
               "로그인"]])]
          [:br {:style {:font-size "0.13em"}}]
          [:ul.sectlevel1
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :account states)} "계정"]
            [:ul.sectlevel2 {:style {:display (if (:account @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/login")} "로그인"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/enroll")} "회원가입"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/userinfo")} "유저정보"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/login")} "로그아웃"]]]]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click #(route/route "/notifications")} "알림"]]
           [:br]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click #(route/route "/contracts")} "거래소"]]
           [:br]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :profile states)} "소통"]
            [:ul.sectlevel2 {:style {:display (if (:profile @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (reset! (::userprofile-params states) {:user-no @(::selected-user-no states)})
                                  (route/route "/userprofile"))} "프로필"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(do
                                  (reset! (::userplaylist-params states) {:playlist @(::music-playlist states)})
                                  (route/route "/userplaylist"))} "플레이리스트"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/userportfolio")} "포트폴리오"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :coin states)} "코인"]
              [:ul.sectlevel3 {:style {:display (if (:coin @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/usercoin1")} "백학코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/usercoin3list")} "앵화코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/usercoin8list")} "만월코인"]]]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :share states)} "문예"]
              [:ul.sectlevel3 {:style {:display (if (:share @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click (toggle-display :share1 states)} "백학문"]
                [:ul.sectlevel4 {:style {:display (if (:share1 @(::display? states)) "" "none")}}
                 [:li
                  [:div {:style {:color "rgba(184, 134, 11, 0.5)"
                                 :cursor "pointer"}
                         :on-click #(route/route "/usershare1list1")} "빛"]]
                 [:li
                  [:div {:style {:color "rgba(0, 0, 0, 0.5)"
                                 :cursor "pointer"}
                         :on-click #(route/route "/usershare1list2")} "어둠"]]
                 [:li
                  [:div {:style {:color "rgba(128, 0, 0, 0.5)"
                                 :cursor "pointer"}
                         :on-click #(route/route "/usershare1list3")} "불"]]
                 [:li
                  [:div {:style {:color "rgba(0, 0, 128, 0.5)"
                                 :cursor "pointer"}
                         :on-click #(route/route "/usershare1list4")} "물"]]
                 [:li
                  [:div {:style {:color "rgba(0, 128, 0, 0.5)"
                                 :cursor "pointer"}
                         :on-click #(route/route "/usershare1list5")} "바람"]]
                 [:li
                  [:div {:style {:color "rgba(165, 42, 42, 0.5)"
                                 :cursor "pointer"}
                         :on-click #(route/route "/usershare1list6")} "땅"]]]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/usershare3list")} "앵화문"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/usershare8list")} "만월문"]]]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :record states)} "기록"]
              [:ul.sectlevel3 {:style {:display (if (:record @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/userrecord3list")} "앵화기록"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/userrecord8list")} "만월기록"]]]]]]
           [:br]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :january states)} "1월"]
            [:ul.sectlevel2 {:style {:display (if (:january @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/crane")} "두루미"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/algebra1")} "백학대수"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :plaza1 states)} "광장"]
              [:ul.sectlevel3 {:style {:display (if (:plaza1 @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/coin1plaza")} "코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/share1plaza")} "문예"]]]]]]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :march states)} "3월"]
            [:ul.sectlevel2 {:style {:display (if (:march @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/blossom")} "벚꽃"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/analysis3")} "앵화해석"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :plaza3 states)} "광장"]
              [:ul.sectlevel3 {:style {:display (if (:plaza3 @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/coin3plaza")} "코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/share3plaza")} "문예"]]]]]]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click (toggle-display :august states)} "8월"]
            [:ul.sectlevel2 {:style {:display (if (:august @(::display? states)) "" "none")}}
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/moon")} "보름달"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click #(route/route "/analysis8")} "만월해석"]]
             [:li
              [:div {:style {:cursor "pointer"}
                     :on-click (toggle-display :plaza8 states)} "광장"]
              [:ul.sectlevel3 {:style {:display (if (:plaza8 @(::display? states)) "" "none")}}
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/coin8plaza")} "코인"]]
               [:li
                [:div {:style {:cursor "pointer"}
                       :on-click #(route/route "/share8plaza")} "문예"]]]]]]
           [:br]
           [:li
            [:div {:style {:cursor "pointer"}
                   :on-click #(route/route "/history")} "역사"]]]]
         [:div#content {:style {:min-height (if @content-height
                                              (str @content-height "px")
                                              "")
                                :overflow-x "hidden"}}
          [@(:papercompany.utopia-lite.core/route states) states]]
         [:div#footer {:style {:overflow "hidden"}}
          [:div#footer-text {:style {:display "flex"
                                     :justify-content "space-between"
                                     :width "100%"}}
           [:div {:style {:cursor "pointer"}
                  :on-click #(do
                               (.scrollTo
                                js/window
                                #js {:top 0
                                     :behavior "smooth"})
                               (@scroll-top-handler))}
            "Powered By PaperCompany"]
           [:div {:style {:cursor "pointer"}
                  :on-click #(do
                               (if @(::dark-mode? states)
                                 (set! (.-backgroundColor (.-style (.-documentElement js/document))) "white")
                                 (set! (.-backgroundColor (.-style (.-documentElement js/document))) "black"))
                               (swap! (::dark-mode? states) not))}
            "다크모드"]]]])
      ;---------------------------------------------------------------------------------------------------------------------
      [:div#main.book.toc2
       [:div#header {:style {:overflow "hidden"}}
        [:h1 "로딩중"]
        [:div#details {:style {:font-size "15px"
                               :color (if @(::dark-mode? states)
                                        "rgba(255, 255, 255, 0.3)"
                                        "rgba(0, 0, 0, 0.3)")}}
         [:span.revnumber "로딩중"]]]
       [:div#content {:style {:height (if @content-height
                                        (str @content-height "px")
                                        "")
                              :overflow-x "hidden"}} "로딩중"]
       [:div#footer {:style {:overflow "hidden"}}
        [:div#footer-text {:style {:display "flex"
                                   :justify-content "space-between"
                                   :width "100%"}}
         [:div {:style {:cursor "pointer"}
                :on-click #(do
                             (.scrollTo
                              js/window
                              #js {:top 0
                                   :behavior "smooth"})
                             (@scroll-top-handler))}
          "Powered By PaperCompany"]
         [:div {:style {:cursor "pointer"}
                :on-click #(do
                             (if @(::dark-mode? states)
                               (set! (.-backgroundColor (.-style (.-documentElement js/document))) "white")
                               (set! (.-backgroundColor (.-style (.-documentElement js/document))) "black"))
                             (swap! (::dark-mode? states) not))}
          "다크모드"]]]])))
