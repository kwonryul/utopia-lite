(ns papercompany.utopia-lite.doms.main.userplaylists
  (:require
   [papercompany.utopia-lite.route :as route]
   [reagent.core :as reagent]
   [clojure.string :as str]))

(set! *warn-on-infer* false)

(defn playlists [user-no]
  (case user-no
    1 [{:no 1
        :name "유토피아"
        :description "모두 검을 뽑아라"
        :user-no 1
        :index 1
        :main-image-url "/images/music-utopia.jpg"}
       {:no 4
        :name "설렘"
        :description "새로운 시작\n가벼운 발걸음으로"
        :user-no 1
        :index 2
        :main-image-url "/images/설렘.png"}
       {:no 5
        :name "희망"
        :description "힘들때 응원이 되어주는건\n꿈과 희망으로 가득했던 과거의 나"
        :user-no 1
        :index 3
        :main-image-url "/images/inuyasha.png"}]
    2 [{:no 2
        :name "설렘"
        :description "새로운 시작\n가벼운 발걸음으로"
        :user-no 2
        :index 1
        :main-image-url "/images/설렘.png"}]
    3 [{:no 3
        :name "희망"
        :description "힘들때 응원이 되어주는건\n꿈과 희망으로 가득했던 과거의 나"
        :user-no 3
        :index 1
        :main-image-url "/images/inuyasha.png"}]))

(defn load-playlist [playlist-no]
  (case playlist-no
    1 {:no 1
       :name "유토피아"
       :description "모두 검을 뽑아라"
       :user-no 1
       :index 1
       :main-image-url "/images/music-utopia.jpg"
       :content [{:name "세상에서 하나뿐인 꽃"
                  :src "/audios/세상에서-하나뿐인-꽃.mp3"
                  :image-url "/images/세상에서-하나뿐인-꽃.webp"}
                 {:name "Where is the Love"
                  :src "/audios/where-is-the-love.mp3"
                  :image-url "/images/where-is-the-love.jpeg"}
                 {:name "아름다운 세상"
                  :src "/audios/아름다운-세상.mp3"
                  :image-url "/images/아름다운-세상.jpg"}
                 {:name "You And I"
                  :src "/audios/you-and-i.mp3"
                  :image-url "/images/you-and-i.jpg"}
                 {:name "촛불하나"
                  :src "/audios/촛불하나.mp3"
                  :image-url "/images/촛불하나.jpg"}]}
    2 {:no 2
       :name "설렘"
       :description "새로운 시작\n가벼운 발걸음으로"
       :user-no 2
       :index 1
       :main-image-url "/images/설렘.png"
       :content [{:name "처음부터 너와 나"
                  :src "/audios/처음부터-너와-나.mp3"
                  :image-url "/images/처음부터-너와-나.jpeg"}
                 {:name "거북이"
                  :src "/audios/거북이.mp3"
                  :image-url "/images/거북이.jpg"}
                 {:name "고민중독"
                  :src "/audios/고민중독.mp3"
                  :image-url "/images/고민중독.png"}
                 {:name "Love is Danger"
                  :src "/audios/love-is-danger.mp3"
                  :image-url "/images/love-is-danger.webp"}
                 {:name "심장이 쿵"
                  :src "/audios/심장이-쿵.mp3"
                  :image-url "/images/심장이-쿵.jpg"}
                 {:name "Me You"
                  :src "/audios/me-you.mp3"
                  :image-url "/images/me-you.jpg"}
                 {:name "우연히 봄"
                  :src "/audios/우연히-봄.mp3"
                  :image-url "/images/우연히-봄.jpeg"}]}
    3 {:no 3
       :name "희망"
       :description "힘들때 응원이 되어주는건\n꿈과 희망으로 가득했던 과거의 나"
       :user-no 3
       :index 1
       :main-image-url "/images/inuyasha.png"
       :content [{:name "Change The World"
                  :src "/audios/change-the-world.mp3"
                  :image-url "/images/change-the-world.gif"}
                 {:name "I am"
                  :src "/audios/i-am.mp3"
                  :image-url "/images/i-am.gif"}
                 {:name "Frontier"
                  :src "/audios/frontier.mp3"
                  :image-url "/images/frontier.gif"}
                 {:name "마음의 지도"
                  :src "/audios/마음의-지도.mp3"
                  :image-url "/images/마음의-지도.gif"}
                 {:name "나의 마음을 담아"
                  :src "/audios/나의-마음을-담아.mp3"
                  :image-url "/images/나의-마음을-담아.gif"}
                 {:name "또 다른 나"
                  :src "/audios/또-다른-나.mp3"
                  :image-url "/images/또-다른-나.jpg"}]}
    4 {:no 4
       :name "설렘"
       :description "새로운 시작\n가벼운 발걸음으로"
       :user-no 1
       :index 2
       :main-image-url "/images/처음부터-너와-나.png"
       :content [{:name "처음부터 너와 나"
                  :src "/audios/처음부터-너와-나.mp3"
                  :image-url "/images/처음부터-너와-나.jpeg"}
                 {:name "거북이"
                  :src "/audios/거북이.mp3"
                  :image-url "/images/거북이.jpg"}
                 {:name "고민중독"
                  :src "/audios/고민중독.mp3"
                  :image-url "/images/고민중독.png"}
                 {:name "Love is Danger"
                  :src "/audios/love-is-danger.mp3"
                  :image-url "/images/love-is-danger.webp"}
                 {:name "심장이 쿵"
                  :src "/audios/심장이-쿵.mp3"
                  :image-url "/images/심장이-쿵.jpg"}
                 {:name "Me You"
                  :src "/audios/me-you.mp3"
                  :image-url "/images/me-you.jpg"}
                 {:name "우연히 봄"
                  :src "/audios/우연히-봄.mp3"
                  :image-url "/images/우연히-봄.jpeg"}] }
    5 {:no 5
       :name "희망"
       :description "힘들때 응원이 되어주는건\n꿈과 희망으로 가득했던 과거의 나"
       :user-no 1
       :index 3
       :main-image-url "/images/inuyasha.png"
       :content [{:name "Change The World"
                  :src "/audios/change-the-world.mp3"
                  :image-url "/images/change-the-world.gif"}
                 {:name "I am"
                  :src "/audios/i-am.mp3"
                  :image-url "/images/i-am.gif"}
                 {:name "Frontier"
                  :src "/audios/frontier.mp3"
                  :image-url "/images/frontier.gif"}
                 {:name "마음의 지도"
                  :src "/audios/마음의-지도.mp3"
                  :image-url "/images/마음의-지도.gif"}
                 {:name "나의 마음을 담아"
                  :src "/audios/나의-마음을-담아.mp3"
                  :image-url "/images/나의-마음을-담아.gif"}
                 {:name "또 다른 나"
                  :src "/audios/또-다른-나.mp3"
                  :image-url "/images/또-다른-나.jpg"}]}))

(defn scroll-top-handler [states]
  (fn []
    (.scrollTo
     (.getElementById js/document "userplaylists-scroll")
     #js {:left (-
                 (* (dec @(::playlist-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                       60
                                                       80))
                 (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                          0.8)
                       (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                         180
                         240))
                    2))})
    (.scrollTo
     (.getElementById js/document "userplaylists-content")
     #js {:top 0
          :behavior "smooth"})))

(defn resize-handler [states]
  (fn []
    (reset! (::userplaylists-name-height states)
            (when-let [userplaylists-name (.getElementById js/document "userplaylists-name")]
              (let [userplaylists-name-height-string (.-height (.getComputedStyle js/window userplaylists-name))]
                (js/parseFloat
                 (subs userplaylists-name-height-string 0 (- (count userplaylists-name-height-string) 2))))))))

(defn init [states]
  (fn [_]
    (reset! (:papercompany.utopia-lite.doms.main/name states) "플레이리스트 목록")
    (reset! (:papercompany.utopia-lite.doms.main/details states) "해당 유저의 플레이리스트 목록입니다")
    (when @(:papercompany.utopia-lite.doms.main/selected-user-no states)
      (reset! (::playlist states) (:playlist @(:papercompany.utopia-lite.doms.main/userplaylists-params states)))
      (reset! (::playlist-index states) (:index @(:papercompany.utopia-lite.doms.main/userplaylists-params states)))
      (reset! (::max-playlist-index states) (case @(:papercompany.utopia-lite.doms.main/selected-user-no states)
                                              1 3
                                              2 1
                                              3 1)))
    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 200)
    (js/setTimeout (scroll-top-handler states) 225)))

(defn dom [states]
  (if @(:papercompany.utopia-lite.doms.main/selected-user-no states)
    (reagent/with-let [states (merge states {::touch-start-x (reagent/atom nil)
                                             ::transition-x (reagent/atom 0)
                                             ::userplaylists-name-height (reagent/atom 0)
                                             ::playlist (reagent/atom nil)
                                             ::playlist-index (reagent/atom 1)
                                             ::max-playlist-index (reagent/atom 0)})
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
                                   (do (swap! (::playlist-index states)
                                              (fn [index] (inc (mod (- index 2) @(::max-playlist-index states)))))
                                       (.scrollTo
                                        (.getElementById js/document "userplaylists-scroll")
                                        #js {:left (-
                                                    (* (dec @(::playlist-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                          60
                                                                                          80))
                                                    (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                             0.8)
                                                          (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                            180
                                                            240))
                                                       2))
                                             :behavior "smooth"})
                                       (reset! (::playlist states) ((playlists @(:papercompany.utopia-lite.doms.main/selected-user-no states)) (dec @(::playlist-index states))))
                                       (js/setTimeout
                                        (fn []
                                          (.scrollTo
                                           (.getElementById js/document "userplaylists-content")
                                           #js {:top 0
                                                :behavior "smooth"}))
                                        25)
                                       (reset! (::max-playlist-index states) (case @(:papercompany.utopia-lite.doms.main/selected-user-no states)
                                                                               1 3
                                                                               2 1
                                                                               3 1))
                                       (reset! (::touch-start-x states) nil)
                                       (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))
                                   (< touch-end-x (-
                                                   @(::touch-start-x states)
                                                   100))
                                   (do (swap! (::playlist-index states)
                                              (fn [index] (inc (mod index @(::max-playlist-index states)))))
                                       (.scrollTo
                                        (.getElementById js/document "userplaylists-scroll")
                                        #js {:left (-
                                                    (* (dec @(::playlist-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                          60
                                                                                          80))
                                                    (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                             0.8)
                                                          (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                            180
                                                            240))
                                                       2))
                                             :behavior "smooth"})
                                       (reset! (::playlist states) ((playlists @(:papercompany.utopia-lite.doms.main/selected-user-no states)) (dec @(::playlist-index states))))
                                       (js/setTimeout
                                        (fn []
                                          (.scrollTo
                                           (.getElementById js/document "userplaylists-content")
                                           #js {:top 0
                                                :behavior "smooth"}))
                                        25)
                                       (reset! (::max-playlist-index states) (case @(:papercompany.utopia-lite.doms.main/selected-user-no states)
                                                                               1 3
                                                                               2 1
                                                                               3 1))
                                       (reset! (::touch-start-x states) nil)
                                       (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))
                                 (reset! (::transition-x states) 0))}
          [:div.frame {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                               :height (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                               :position "absolute"
                               :top "0px"
                               :left (str (cond
                                            (> @(::transition-x states) 20)
                                            (- 50 (/ 1000 @(::transition-x states)))
                                            (< @(::transition-x states) -20)
                                            (- -50 (/ 1000 @(::transition-x states)))
                                            (= @(::transition-x states) 0)
                                            0) "px")
                               :background-color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                   "rgba(255, 255, 255, 0.1)"
                                                   "rgba(0, 0, 0, 0.1)")
                               :cursor "pointer"}
                       :on-click #(do
                                    (reset! (:papercompany.utopia-lite.doms.main/userplaylist-params states) {:playlist (load-playlist (:no @(::playlist states)))})
                                    (route/route "/userplaylist"))}
           [:img {:style {:width "100%"
                          :height "100%"
                          :border-radius "4em"
                          :padding "0.5625em 0.625em"}
                  :src (:main-image-url @(::playlist states))
                  :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]]
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
           (vec (concat [:div#userplaylists-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
                                                                                        (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                          120
                                                                                          160))]
                                                                           (if (> (* @(::max-playlist-index states)
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
                                               :color (if (= @(::playlist-index states) x)
                                                        "#ba3925"
                                                        "")
                                               :cursor "pointer"}
                                       :on-click (fn [_]
                                                   (reset! (::playlist-index states) x)
                                                   (.scrollTo
                                                    (.getElementById js/document "userplaylists-scroll")
                                                    #js {:left (-
                                                                (* (dec @(::playlist-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                                      60
                                                                                                      80))
                                                                (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                         0.8)
                                                                      (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                        180
                                                                        240))
                                                                   2))
                                                         :behavior "smooth"})
                                                   (reset! (::playlist states) ((playlists @(:papercompany.utopia-lite.doms.main/selected-user-no states)) (dec @(::playlist-index states))))
                                                   (js/setTimeout
                                                    (fn []
                                                      (.scrollTo
                                                       (.getElementById js/document "userplaylists-content")
                                                       #js {:top 0
                                                            :behavior "smooth"}))
                                                    25)
                                                   (reset! (::max-playlist-index states) (case @(:papercompany.utopia-lite.doms.main/selected-user-no states)
                                                                                           1 3
                                                                                           2 1
                                                                                           3 1))
                                                   (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))} (str x)])
                              (range 1 (inc @(::max-playlist-index states))))))
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
                                            120
                                            160)
                                    :behavior "smooth"}))} ">"]]]
         [:div#userplaylists-content {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                              :height "12em"
                                              :position "relative"
                                              :overflow-y "auto"}}
          (vec (concat
                [:div.article.user {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                            :position "relative"
                                            :top "0px"
                                            :left "0px"
                                            :padding-left "1em"
                                            :padding-right "1em"}}
                 [:h5 (:name @(::playlist states))]
                 [:hr]]
                (interpose
                 [:br]
                 (mapv
                  (fn [str]
                    [:span str])
                  (str/split (:description @(::playlist states)) #"\n")))))]]
                                        ;-------------------------------------------------------------------------------------------
        [:div {:style {:display "flex"
                       :flex-direction "column"}}
         [:div {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                        :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")
                        :position "relative"
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
                                   (do (swap! (::playlist-index states)
                                              (fn [index] (inc (mod (- index 2) @(::max-playlist-index states)))))
                                       (.scrollTo
                                        (.getElementById js/document "userplaylists-scroll")
                                        #js {:left (-
                                                    (* (dec @(::playlist-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                          60
                                                                                          80))
                                                    (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                             0.8)
                                                          (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                            180
                                                            240))
                                                       2))
                                             :behavior "smooth"})
                                       (reset! (::playlist states) ((playlists @(:papercompany.utopia-lite.doms.main/selected-user-no states)) (dec @(::playlist-index states))))
                                       (js/setTimeout
                                        (fn []
                                          (.scrollTo
                                           (.getElementById js/document "userplaylists-content")
                                           #js {:top 0
                                                :behavior "smooth"}))
                                        25)
                                       (reset! (::max-playlist-index states) (case @(:papercompany.utopia-lite.doms.main/selected-user-no states)
                                                                               1 3
                                                                               2 1
                                                                               3 1))
                                       (reset! (::touch-start-x states) nil)
                                       (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))
                                   (< touch-end-x (-
                                                   @(::touch-start-x states)
                                                   100))
                                   (do (swap! (::playlist-index states)
                                              (fn [index] (inc (mod index @(::max-playlist-index states)))))
                                       (.scrollTo
                                        (.getElementById js/document "userplaylists-scroll")
                                        #js {:left (-
                                                    (* (dec @(::playlist-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                          60
                                                                                          80))
                                                    (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                             0.8)
                                                          (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                            180
                                                            240))
                                                       2))
                                             :behavior "smooth"})
                                       (reset! (::playlist states) ((playlists @(:papercompany.utopia-lite.doms.main/selected-user-no states)) (dec @(::playlist-index states))))
                                       (js/setTimeout
                                        (fn []
                                          (.scrollTo
                                           (.getElementById js/document "userplaylists-content")
                                           #js {:top 0
                                                :behavior "smooth"}))
                                        25)
                                       (reset! (::max-playlist-index states) (case @(:papercompany.utopia-lite.doms.main/selected-user-no states)
                                                                               1 3
                                                                               2 1
                                                                               3 1))
                                       (reset! (::touch-start-x states) nil)
                                       (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))
                                 (reset! (::transition-x states) 0))}
          [:div.frame {:style {:width (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")
                               :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")
                               :position "absolute"
                               :top "0px"
                               :left (str (cond
                                            (> @(::transition-x states) 20)
                                            (- 50 (/ 1000 @(::transition-x states)))
                                            (< @(::transition-x states) -20)
                                            (- -50 (/ 1000 @(::transition-x states)))
                                            (= @(::transition-x states) 0)
                                            0) "px")
                               :background-color (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                                   "rgba(255, 255, 255, 0.1)"
                                                   "rgba(0, 0, 0, 0.1)")
                               :cursor "pointer"}
                       :on-click #(do
                                    (reset! (:papercompany.utopia-lite.doms.main/userplaylist-params states) {:playlist (load-playlist (:no @(::playlist states)))})
                                    (route/route "/userplaylist"))}
           [:img {:style {:width "100%"
                          :height "100%"
                          :border-radius "4em"
                          :padding "0.5625em 0.625em"}
                  :src (:main-image-url @(::playlist states))
                  :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]
          [:div {:style {:width (str (+ (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 2) "px")
                         :height (str @(::userplaylists-name-height states) "px")
                         :position "absolute"
                         :top "0px"
                         :left (str (+ (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 1)
                                       (cond
                                         (> @(::transition-x states) 20)
                                         (- 50 (/ 1000 @(::transition-x states)))
                                         (< @(::transition-x states) -20)
                                         (- -50 (/ 1000 @(::transition-x states)))
                                         (= @(::transition-x states) 0)
                                         0)) "px")
                         :border "1px solid rgba(0, 0, 0, 0)"
                         :border-width "0px 1px 0px 1px"}}
           [:div#userplaylists-name.article.user {:style {:width "100%"
                                                          :position "absolute"
                                                          :top "0px"
                                                          :left "-1px"
                                                          :padding-left "1em"
                                                          :padding-rigfht "1em"}}
            [:h5 (:name @(::playlist states))]
            [:hr]]]
          [:div#userplaylists-content {:style {:width (str (+ (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 2) "px")
                                               :height (str (-
                                                             (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2)
                                                             @(::userplaylists-name-height states)
                                                             -1) "px")
                                               :position "absolute"
                                               :top (str (- @(::userplaylists-name-height states) 1) "px")
                                               :left (str (+ (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 1)
                                                             (cond
                                                               (> @(::transition-x states) 20)
                                                               (- 50 (/ 1000 @(::transition-x states)))
                                                               (< @(::transition-x states) -20)
                                                               (- -50 (/ 1000 @(::transition-x states)))
                                                               (= @(::transition-x states) 0)
                                                               0)) "px")
                                               :border "1px solid rgba(0, 0, 0, 0)"
                                               :overflow-y "auto"}}
           (vec (concat
                 [:div.article.user {:style {:width "100%"
                                             :position "absolute"
                                             :top "-1px"
                                             :left "-1px"
                                             :margin "0px"
                                             :padding-left "1em"
                                             :padding-right "1em"}}]
                 (interpose
                  [:br]
                  (mapv
                   (fn [str]
                     [:span str])
                   (str/split (:description @(::playlist states)) #"\n")))))]]
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
           (vec (concat [:div#userplaylists-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
                                                                                        (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                          120
                                                                                          160))]
                                                                           (if (> (* @(::max-playlist-index states)
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
                                               :color (if (= @(::playlist-index states) x)
                                                        "#ba3925"
                                                        "")
                                               :cursor "pointer"}
                                       :on-click (fn [_]
                                                   (reset! (::playlist-index states) x)
                                                   (.scrollTo
                                                    (.getElementById js/document "userplaylists-scroll")
                                                    #js {:left (-
                                                                (* (dec @(::playlist-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                                      60
                                                                                                      80))
                                                                (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                         0.8)
                                                                      (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                        180
                                                                        240))
                                                                   2))
                                                         :behavior "smooth"})
                                                   (reset! (::playlist states) ((playlists @(:papercompany.utopia-lite.doms.main/selected-user-no states)) (dec @(::playlist-index states))))
                                                   (js/setTimeout
                                                    (fn []
                                                      (.scrollTo
                                                       (.getElementById js/document "userplaylists-content")
                                                       #js {:top 0
                                                            :behavior "smooth"}))
                                                    25)
                                                   (reset! (::max-playlist-index states) (case @(:papercompany.utopia-lite.doms.main/selected-user-no states)
                                                                                           1 3
                                                                                           2 1
                                                                                           3 1))
                                                   (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))} (str x)])
                              (range 1 (inc @(::max-playlist-index states))))))
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
                                            120
                                            160)
                                    :behavior "smooth"}))} ">"]]]])
                                        ;---------------------------------------------------------------------------------------
      (finally
        (reset! (:papercompany.utopia-lite.doms.main/resize-handler states) (fn []))
        (reset! (:papercompany.utopia-lite.doms.main/scroll-top-handler states) (fn []))))
    (do
      (reset! (:papercompany.utopia-lite.doms.main/userprofile-params states) {:user-no nil})
      (route/route "/userprofile")
      [:div "로딩중"])))
