(ns beathacker.app.xhr
  (:require [goog.events :as events])
  (:import [goog.net XhrIo]
           [goog.events EventType]
           goog.net.EventType))

(def ^:private meths
  {:get    "GET"
   :put    "PUT"
   :post   "POST"
   :delete "DELETE"})

(def base-url "http://localhost:5021/api/")

(defn do-xhr
  [{:keys [method url data on-complete]}]
  (let [xhr (XhrIo.)]
    (events/listen xhr
                   goog.net.EventType.COMPLETE
                   (fn [e]
                     (on-complete e)))
    (. xhr
       (send (str base-url url)
             (meths method)
             (when data
               (-> data clj->js JSON/stringify))
             #js {"Content-Type" "application/json"}))))


