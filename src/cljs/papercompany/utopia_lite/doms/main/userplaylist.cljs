(ns papercompany.utopia-lite.doms.main.userplaylist
  (:require
   [papercompany.utopia-lite.services.music :as music-service]
   [papercompany.utopia-lite.route :as route]
   [reagent.core :as reagent]))

(set! *warn-on-infer* false)

(defn scroll-top-handler [states]
  (fn []
    (.scrollTo
     (.getElementById js/document "userplaylist-scroll")
     #js {:left (-
                 (* (dec @(::music-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                    60
                                                    80))
                 (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                          0.8)
                       (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                         180
                         240))
                    2))
          :behavior "smooth"})))

(defn resize-handler [states]
  (fn []
    (when-let [userplaylist-name (.getElementById js/document "userplaylist-name")]
        (reset! (::userplaylist-name-height states)
                (let [userplaylist-name-height-string (.-height (.getComputedStyle js/window userplaylist-name))]
                  (js/parseFloat
                   (subs userplaylist-name-height-string 0 (- (count userplaylist-name-height-string) 2))))))))

(defn init [states]
  (fn [_]
    (do
      (reset! (:papercompany.utopia-lite.doms.main/name states) "플레이리스트")
      (reset! (:papercompany.utopia-lite.doms.main/details states) "해당 유저가 좋아하는 음악들입니다")
      (reset! (::playlist states) (:playlist @(:papercompany.utopia-lite.doms.main/userplaylist-params states)))
      (reset! (::music-index states) (if (and
                                          (= (get-in @(:papercompany.utopia-lite.doms.main/userplaylist-params states) [:playlist :no])
                                             (:no @(:papercompany.utopia-lite.doms.main/music-playlist states)))
                                          @(:papercompany.utopia-lite.doms.main/music-playing? states))
                                       @music-service/current-audio-index
                                       1)))
    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 200)
    (js/setTimeout (scroll-top-handler states) 225)))

(defn dom [states]
  (if @(:papercompany.utopia-lite.doms.main/selected-user-no states)
    (reagent/with-let [states (merge states {::touch-start-x (reagent/atom nil)
                                             ::transition-x (reagent/atom 0)
                                             ::userplaylist-name-height (reagent/atom 0)
                                             ::playlist (reagent/atom nil)
                                             ::music-index (reagent/atom nil)
                                             ::button-mode (reagent/atom 0)})
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
                                   (do (swap! (::music-index states)
                                              (fn [index] (inc (mod (- index 2) (count (:content @(::playlist states)))))))
                                       (.scrollTo
                                        (.getElementById js/document "userplaylist-scroll")
                                        #js {:left (-
                                                    (* (dec @(::music-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                       60
                                                                                       80))
                                                    (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                             0.8)
                                                          (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                            180
                                                            240))
                                                       2))
                                             :behavior "smooth"})
                                       (reset! (::touch-start-x states) nil)
                                       (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))
                                   (< touch-end-x (-
                                                   @(::touch-start-x states)
                                                   100))
                                   (do (swap! (::music-index states)
                                              (fn [index] (inc (mod index (count (:content @(::playlist states)))))))
                                       (.scrollTo
                                        (.getElementById js/document "userplaylist-scroll")
                                        #js {:left (-
                                                    (* (dec @(::music-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                       60
                                                                                       80))
                                                    (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                             0.8)
                                                          (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                            180
                                                            240))
                                                       2))
                                             :behavior "smooth"})
                                       (reset! (::touch-start-x states) nil)
                                       (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))
                                 (reset! (::transition-x states) 0))}
          [:img.frame {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                               :height (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                               :position "absolute"
                               :top "0px"
                               :left (str (cond
                                            (> @(::transition-x states) 20)
                                            (- 50 (/ 1000 @(::transition-x states)))
                                            (< @(::transition-x states) -20)
                                            (- -50 (/ 1000 @(::transition-x states)))
                                            (= @(::transition-x states) 0)
                                            0) "px")}
                       :src (get-in @(::playlist states) [:content (dec @(::music-index states)) :image-url])
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
                               (.getElementById js/document "userplaylist-scroll")
                               #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                            -120
                                            -160)
                                    :behavior "smooth"}))} "<"]
           (vec (concat [:div#userplaylist-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
                                                                                       (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                         120
                                                                                         160))]
                                                                          (if (> (* (count (:content @(::playlist states)))
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
                                               :color (if (= @(::music-index states) x)
                                                        "#ba3925"
                                                        "")
                                               :cursor "pointer"}
                                       :on-click (fn [_]
                                                   (reset! (::music-index states) x)
                                                   (.scrollTo
                                                    (.getElementById js/document "userplaylist-scroll")
                                                    #js {:left (-
                                                                (* (dec @(::music-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                                   60
                                                                                                   80))
                                                                (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                         0.8)
                                                                      (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                        180
                                                                        240))
                                                                   2))
                                                         :behavior "smooth"})
                                                   (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))} (str x)])
                              (range 1 (inc (count (:content @(::playlist states))))))))
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
                               (.getElementById js/document "userplaylist-scroll")
                               #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                            120
                                            160)
                                    :behavior "smooth"}))} ">"]]]
         [:div.article.user {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                     :padding-left "1em"
                                     :padding-right "1em"}}
          [:h5 (get-in @(::playlist states) [:content (dec @(::music-index states)) :name])]
          [:hr {:style {:margin-bottom "0px"}}]]
         [:div {:style {:display "flex"
                        :justify-content "center"
                        :align-items "center"
                        :width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                        :height "12em"}}
          [:div {:style {:display "flex"
                         :justify-content "space-between"
                         :align-items "center"
                         :width "10.8em"
                         :height "6em"}}
           [:img {:style {:width "1.8em"
                          :height "1.8em"
                          :filter (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                    "invert(100%)"
                                    "")
                          :cursor "pointer"}
                  :src "/images/toc2-off.png"
                  :on-click #(swap! (::button-mode states) (fn [button-mode] (mod (dec button-mode) 2)))
                  :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]
           [:img {:style {:width "6em"
                          :height "6em"
                          :filter (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                    "invert(100%)"
                                    "")
                          :cursor "pointer"}
                  :src (case @(::button-mode states)
                         0 (if (and
                                (= (:no @(::playlist states))
                                   (:no @(:papercompany.utopia-lite.doms.main/music-playlist states)))
                                (= @(::music-index states)
                                   @music-service/current-audio-index))
                             "/images/stop.png"
                             "/images/play.png")
                         1 "/images/swap.png")
                  :on-click #(case @(::button-mode states)
                               0 (let [play? (not (and
                                                   (= (:no @(::playlist states))
                                                      (:no @(:papercompany.utopia-lite.doms.main/music-playlist states)))
                                                   (= @(::music-index states)
                                                      @music-service/current-audio-index)))]
                                   (music-service/stop states)
                                   (when play?
                                     (reset! (:papercompany.utopia-lite.doms.main/music-playlist states) @(::playlist states))
                                     (music-service/play-index states @(::music-index states))))
                               1 (do
                                   (reset! (:papercompany.utopia-lite.doms.main/userplaylists-params states) {:playlist @(::playlist states)
                                                                                                              :index (:index @(::playlist states))})
                                   (route/route "/userplaylists")))
                  :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]
           [:img {:style {:width "1.8em"
                          :height "1.8em"
                          :filter (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                    "invert(100%)"
                                    "")
                          :transform "scaleX(-1)"
                          :cursor "pointer"}
                  :src "/images/toc2-off.png"
                  :on-click #(swap! (::button-mode states) (fn [button-mode] (mod (inc button-mode) 2)))
                  :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]]]
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
                                   (do (swap! (::music-index states)
                                              (fn [index] (inc (mod (- index 2) (count (:content @(::playlist states)))))))
                                       (.scrollTo
                                        (.getElementById js/document "userplaylist-scroll")
                                        #js {:left (-
                                                    (* (dec @(::music-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                       60
                                                                                       80))
                                                    (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                             0.8)
                                                          (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                            180
                                                            240))
                                                       2))
                                             :behavior "smooth"})
                                       (reset! (::touch-start-x states) nil)
                                       (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))
                                   (< touch-end-x (-
                                                   @(::touch-start-x states)
                                                   100))
                                   (do (swap! (::music-index states)
                                              (fn [index] (inc (mod index (count (:content @(::playlist states)))))))
                                       (.scrollTo
                                        (.getElementById js/document "userplaylist-scroll")
                                        #js {:left (-
                                                    (* (dec @(::music-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                       60
                                                                                       80))
                                                    (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                             0.8)
                                                          (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                            180
                                                            240))
                                                       2))
                                             :behavior "smooth"})
                                       (reset! (::touch-start-x states) nil)
                                       (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))
                                 (reset! (::transition-x states) 0))}
          [:img.frame {:style {:width (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")
                               :height (str (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) "px")
                               :position "absolute"
                               :top "0px"
                               :left (str (cond
                                            (> @(::transition-x states) 20)
                                            (- 50 (/ 1000 @(::transition-x states)))
                                            (< @(::transition-x states) -20)
                                            (- -50 (/ 1000 @(::transition-x states)))
                                            (= @(::transition-x states) 0)
                                            0) "px")}
                       :src (get-in @(::playlist states) [:content (dec @(::music-index states)) :image-url])
                       :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]
          [:div {:style {:width (str (+ (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 2) "px")
                         :height (str @(::userplaylist-name-height states) "px")
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
           [:div#userplaylist-name.article.user {:style {:width "100%"
                                                         :position "absolute"
                                                         :top "0px"
                                                         :left "-1px"
                                                         :padding-left "1em"
                                                         :padding-right "1em"}}
            [:h5 (get-in @(::playlist states) [:content (dec @(::music-index states)) :name])]
            [:hr {:style {:margin-bottom "0px"}}]]]
          [:div {:style {:display "flex"
                         :justify-content "center"
                         :align-items "center"
                         :width (str (+ (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 2) "px")
                         :height (str (-
                                       (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2)
                                       @(::userplaylist-name-height states)
                                       -1) "px")
                         :position "absolute"
                         :top (str (- @(::userplaylist-name-height states) 1) "px")
                         :left (str (+ (- (/ @(:papercompany.utopia-lite.doms.main/content-width states) 2) 1)
                                       (cond
                                         (> @(::transition-x states) 20)
                                         (- 50 (/ 1000 @(::transition-x states)))
                                         (< @(::transition-x states) -20)
                                         (- -50 (/ 1000 @(::transition-x states)))
                                         (= @(::transition-x states) 0)
                                         0)) "px")
                         :border "1px solid rgba(0, 0, 0, 0)"}}
           [:div {:style {:display "flex"
                          :justify-content "space-between"
                          :align-items "center"
                          :width "18em"
                          :height "10em"}}
            [:img {:style {:width "3em"
                           :height "3em"
                           :filter (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                     "invert(100%)"
                                     "")
                           :cursor "pointer"}
                   :src "/images/toc2-off.png"
                   :on-click #(swap! (::button-mode states) (fn [button-mode] (mod (dec button-mode) 2)))
                   :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]
            [:img {:style {:width "10em"
                           :height "10em"
                           :filter (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                     "invert(100%)"
                                     "")
                           :cursor "pointer"}
                   :src (case @(::button-mode states)
                          0 (if (and
                                 (= (:no @(::playlist states))
                                    (:no @(:papercompany.utopia-lite.doms.main/music-playlist states)))
                                 (= @(::music-index states)
                                    @music-service/current-audio-index))
                              "/images/stop.png"
                              "/images/play.png")
                          1 "/images/swap.png")
                   :on-click #(case @(::button-mode states)
                                0 (let [play? (not (and
                                                    (= (:no @(::playlist states))
                                                       (:no @(:papercompany.utopia-lite.doms.main/music-playlist states)))
                                                    (= @(::music-index states)
                                                       @music-service/current-audio-index)))]
                                    (music-service/stop states)
                                    (when play?
                                      (reset! (:papercompany.utopia-lite.doms.main/music-playlist states) @(::playlist states))
                                      (music-service/play-index states @(::music-index states))))
                                1 (do
                                    (reset! (:papercompany.utopia-lite.doms.main/userplaylists-params states) {:playlist @(::playlist states)
                                                                                                               :index (:index @(::playlist states))})
                                    (route/route "/userplaylists")))
                   :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]
            [:img {:style {:width "3em"
                           :height "3em"
                           :filter (if @(:papercompany.utopia-lite.doms.main/dark-mode? states)
                                     "invert(100%)"
                                     "")
                           :transform "scaleX(-1)"
                           :cursor "pointer"}
                   :src "/images/toc2-off.png"
                   :on-click #(swap! (::button-mode states) (fn [button-mode] (mod (inc button-mode) 2)))
                   :on-load #(js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)}]]]]
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
                               (.getElementById js/document "userplaylist-scroll")
                               #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                            -120
                                            -160)
                                    :behavior "smooth"}))} "<"]
           (vec (concat [:div#userplaylist-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
                                                                                       (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                         120
                                                                                         160))]
                                                                          (if (> (* (count (:content @(::playlist states)))
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
                                               :color (if (= @(::music-index states) x)
                                                        "#ba3925"
                                                        "")
                                               :cursor "pointer"}
                                       :on-click (fn [_]
                                                   (reset! (::music-index states) x)
                                                   (.scrollTo
                                                    (.getElementById js/document "userplaylist-scroll")
                                                    #js {:left (-
                                                                (* (dec @(::music-index states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                                   60
                                                                                                   80))
                                                                (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                         0.8)
                                                                      (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                        180
                                                                        240))
                                                                   2))
                                                         :behavior "smooth"})
                                                   (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))} (str x)])
                              (range 1 (inc (count (:content @(::playlist states))))))))
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
                               (.getElementById js/document "userplaylist-scroll")
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
