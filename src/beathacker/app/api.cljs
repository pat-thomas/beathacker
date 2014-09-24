(ns beathacker.app.api
  (:require [beathacker.app.xhr :refer [do-xhr]]))

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
