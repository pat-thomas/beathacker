(ns beathacker.app-loop.handlers.registry)

(def handler-registry (atom {}))

(defn register-handler!
  [handler-name handler-impl]
  (do (swap! handler-registry assoc handler-name handler-impl)
      (keys @handler-registry)))

(defn trigger-handler
  [handler-name & [data]]
  (def handler-name handler-name)
  (def data data)
  (println "running handler")
  (when-let [handler (handler-name @handler-registry)]
    (if data
      (do (println (format "running handler %s with data %s" handler data))
          (handler data))
      (do (println (format "running handler %s" handler))
          (handler)))))
