(ns beathacker.app.core
  (:require [goog.dom     :as gdom]
            [goog.events  :as events]
            [om.core      :as om   :include-macros true]
            [om.core      :as dom  :include-macros true]
            [sablono.core :as html :include-macros true])
  (:import [goog.net XhrIo]
           goog.net.EventType
           [goog.events EventType]))

(enable-console-print!)

(def app-state (atom {:initialized false}))

(def ^:private meths
  {:get    "GET"
   :put    "PUT"
   :post   "POST"
   :delete "DELETE"})

(defn json-xhr
  [{:keys [method url data on-complete]}]
  (let [xhr (XhrIo.)]
    (events/listen xhr goog.net.EventType.COMPLETE
                   (fn [e]
                     (on-complete (fn [msg]
                                    (println msg)))))
    (. xhr
       (send url (meths method) (when data
                                  (pr-str data))
             #js {"Content-Type" "application/json"}))))

(defn beathacker-app
  [data owner opts]
  (reify
    om/IDisplayName
    (display-name [_]
      (or (:react-name opts) "App"))

   om/IRender
   (render [this]
     (html/html
      [:div {:id "om-app"} (if (:initialized data)
                             "Initialized"
                             "Not initialized")]))))

(defn initialize-app!
  []
  (let [target (gdom/getElement "app-container")]
    (om/root beathacker-app
             app-state
             {:target target})))

(initialize-app!)
