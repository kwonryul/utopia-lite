(ns papercompany.utopia-lite.dev-middleware)

(defn wrap-no-cache [handler]
  (fn [request]
    (let [response (handler request)]
      (assoc-in response [:headers "Cache-Control"] "no-store, no-cache, must-revalidate, proxy-revalidate")
      (assoc-in response [:headers "Pragma"] "no-cache")
      (assoc-in response [:headers "Expires"] "0")
      (assoc-in response [:headers "Surrogate-Control"] "no-store"))))

(defn wrap-dev [handler _opts]
  (-> handler
      (wrap-no-cache)))
