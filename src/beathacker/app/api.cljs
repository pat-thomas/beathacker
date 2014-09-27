(ns beathacker.app.api
  (:require [beathacker.app.xhr :refer [do-xhr]]
            [beathacker.app.models.rhythm-grid :as rhythm-grid]))

(defn handle-click
  [e]
  (do-xhr {:method      :post
           :url         "core"
           :data        {:handler-name :sin
                         :data         {:type       :note
                                        :sound-type :sin}}
           :on-complete (fn [res]
                          (do (println :wat)
                              (println res)))}))

(defn handle-click-again
  [e]
  (do-xhr {:method      :post
           :url         "core"
           :data        {:handler-name :kick
                         :data         {:type       :note
                                        :sound-type :kick
                                        :wait       2}}
           :on-complete (fn [res]
                          (do (println :huh)
                              (println res)))}))

(defn send-rhythm-to-server
  [clicked-data]
  (let [rhythm-data (rhythm-grid/clicked-data->rhythm-data clicked-data)]
    (when-not (empty? rhythm-data)
      (do (println "sending to server:" rhythm-data)
          (do-xhr {:method      :post
                   :url         "rhythm-seq"
                   :data        rhythm-data
                   :on-complete (fn [res]
                                  (println res))})))))
