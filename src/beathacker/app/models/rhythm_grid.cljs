(ns beathacker.app.models.rhythm-grid
  (:require-macros [clj-utils.maps :refer [make-map]]))

(defn clicked-data->rhythm-data
  "Marshalls data into a form that is more appropriate to ship to the server."
  [clicked-data]
  (reduce (fn [acc [k v]]
            (if v
              (let [[column row] k]
                (conj acc (make-map row column)))
              acc))
          []
          clicked-data))

