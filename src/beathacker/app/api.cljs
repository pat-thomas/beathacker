(ns beathacker.app.api
  (:require [beathacker.app.xhr :refer [do-xhr]]
            [beathacker.app.models.rhythm-grid :as rhythm-grid]))

(defn send-rhythm-to-server
  [data]
  (let [base-data {:rhythm-data (rhythm-grid/clicked-data->rhythm-data (:clicked data))
                   :num-columns (js/parseInt (get-in data [:selected-option :rows]))}
        req-data  (if-let [repetitions (js/parseInt (get-in data [:selected-option :repetitions]))]
                    (assoc base-data :repetitions repetitions)
                    base-data)]
    (when-not (empty? (:rhythm-data req-data))
      (do-xhr {:method      :post
               :url         "rhythm-seq"
               :data        req-data
               :on-complete (fn [res]
                              (println res))}))))
