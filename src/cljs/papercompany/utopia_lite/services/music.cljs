(ns papercompany.utopia-lite.services.music
  (:require
   [papercompany.utopia-lite.util :as util]
   [reagent.core :as reagent]))

(set! *warn-on-infer* false)

(def current-audio-index (reagent/atom nil))
(def current-audio (reagent/atom nil))
(def current-listener (reagent/atom nil))

(declare play-next-listener)

(defn stop-song [states]
  (when @current-audio
    (when @current-listener
      (.removeEventListener @current-audio "ended" @current-listener)
      (reset! current-listener nil))
    (.pause @current-audio)
    (reset! current-audio nil)))

(defn play-random-song [states]
  (stop-song states)
  (let [index (util/random 1 (inc (count (:content @(:papercompany.utopia-lite.doms.main/music-playlist states)))))]
    (reset! current-audio-index index)
    (reset! current-audio
            (js/Audio.
             (get-in @(:papercompany.utopia-lite.doms.main/music-playlist states) [:content (dec index) :src]))))
  (reset! current-listener (play-next-listener states))
  (.addEventListener @current-audio "ended" @current-listener)
  (.play @current-audio))

(defn play-prev-song [states]
  (stop-song states)
  (when @current-audio-index
    (let [index (inc (mod
                      (- @current-audio-index 2)
                      (count (:content @(:papercompany.utopia-lite.doms.main/music-playlist states)))))]
      (reset! current-audio-index index)
      (reset! current-audio
              (js/Audio.
               (get-in @(:papercompany.utopia-lite.doms.main/music-playlist states) [:content (dec index) :src]))))
    (reset! current-listener (play-next-listener states))
    (.addEventListener @current-audio "ended" @current-listener)
    (.play @current-audio)))

(defn play-next-song [states]
  (stop-song states)
  (when @current-audio-index
    (let [index (inc (mod
                      @current-audio-index
                      (count (:content @(:papercompany.utopia-lite.doms.main/music-playlist states)))))]
      (reset! current-audio-index index)
      (reset! current-audio
              (js/Audio.
               (get-in @(:papercompany.utopia-lite.doms.main/music-playlist states) [:content (dec index) :src]))))
    (reset! current-listener (play-next-listener states))
    (.addEventListener @current-audio "ended" @current-listener)
    (.play @current-audio)))

(defn play-index-song [states index]
  (stop-song states)
  (reset! current-audio-index index)
  (reset! current-audio
          (js/Audio.
           (get-in @(:papercompany.utopia-lite.doms.main/music-playlist states) [:content (dec index) :src])))
  (reset! current-listener (play-next-listener states))
  (.addEventListener @current-audio "ended" @current-listener)
  (.play @current-audio))

(defn play-random-listener [states]
  (fn [_]
    (when (compare-and-set! (:papercompany.utopia-lite.doms.main/music-pending? states) false true)
      (play-random-song states)
      (reset! (:papercompany.utopia-lite.doms.main/music-pending? states) false))))

(defn play-next-listener [states]
  (fn [_]
    (when (compare-and-set! (:papercompany.utopia-lite.doms.main/music-pending? states) false true)
      (play-next-song states)
      (reset! (:papercompany.utopia-lite.doms.main/music-pending? states) false))))

(defn play [states]
  (when (compare-and-set! (:papercompany.utopia-lite.doms.main/music-pending? states) false true)
    (when (not @(:papercompany.utopia-lite.doms.main/music-playing? states))
      (reset! (:papercompany.utopia-lite.doms.main/music-playing? states) true)
      (play-random-song states))
    (reset! (:papercompany.utopia-lite.doms.main/music-pending? states) false)))

(defn play-index [states index]
  (when (compare-and-set! (:papercompany.utopia-lite.doms.main/music-pending? states) false true)
    (when (not @(:papercompany.utopia-lite.doms.main/music-playing? states))
      (reset! (:papercompany.utopia-lite.doms.main/music-playing? states) true)
      (play-index-song states index))
    (reset! (:papercompany.utopia-lite.doms.main/music-pending? states) false)))

(defn stop [states]
  (if (compare-and-set! (:papercompany.utopia-lite.doms.main/music-pending? states) false true)
    (do (when @(:papercompany.utopia-lite.doms.main/music-playing? states)
          (reset! (:papercompany.utopia-lite.doms.main/music-playing? states) false)
          (stop-song states)
          (reset! current-audio-index nil))
        (reset! (:papercompany.utopia-lite.doms.main/music-pending? states) false))
    (js/setTimeout #(stop states) 25)))
