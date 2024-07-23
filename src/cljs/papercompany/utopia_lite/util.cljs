(ns papercompany.utopia-lite.util)

(set! *warn-on-infer* false)

(defn random [start end]
  (+ start (.floor js/Math
                   (* (.random js/Math) (- end start)))))
