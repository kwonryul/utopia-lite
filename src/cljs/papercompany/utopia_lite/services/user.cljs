(ns papercompany.utopia-lite.services.user
  (:require
   [papercompany.utopia-lite.services.music :as music-service]))

(defn select [states user-no]
  (let [selected-user-no @(:papercompany.utopia-lite.doms.main/selected-user-no states)]
    (when (not (= user-no selected-user-no))
     (reset! (:papercompany.utopia-lite.doms.main/selected-user-no states) user-no)
     (reset! (:papercompany.utopia-lite.doms.main/music-playlist states)
             (case user-no
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
                             :image-url "/images/또-다른-나.jpg"}]}))
     (when @(:papercompany.utopia-lite.doms.main/music-playlist states)
       (if selected-user-no
         (when @(:papercompany.utopia-lite.doms.main/music-playing? states)
           (music-service/stop states)
           (music-service/play states))
         (music-service/play states))))))
