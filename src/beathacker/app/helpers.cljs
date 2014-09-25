(ns beathacker.app.helpers)

(defn evt->value
  [evt]
  (.-value (.-target evt)))
