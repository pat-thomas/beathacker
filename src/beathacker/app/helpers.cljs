(ns beathacker.app.helpers)

(defn evt->value
  [evt]
  (.-value (.-target evt)))

(defn data
  [el key]
  (.getAttribute el (str "data-" key)))
