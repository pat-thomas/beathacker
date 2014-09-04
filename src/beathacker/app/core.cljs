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

(def base-url "http://localhost:6020/api/")

(defn json-xhr
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

(defn beathacker-app
  [data owner opts]
  (reify
    om/IDisplayName
    (display-name [_]
      (or (:react-name opts) "App"))

   om/IRender
   (render [this]
     (html/html
      [:div {:id "om-app"}
       (html/submit-button {:on-click (fn [e]
                                        (json-xhr {:method      :post
                                                   :url         "core"
                                                   :data        {:handler-name :sin
                                                                 :data         :not-sure-what-to-put-here}
                                                   :on-complete (fn [res]
                                                                  (println :wat)
                                                                  (println res))}))}
                           "Hello")
       (if (:initialized data)
         "Initialized"
         "Not initialized")]))))

(defn initialize-app!
  []
  (let [target (gdom/getElement "app-container")]
    (om/root beathacker-app
             app-state
             {:target target})))

(initialize-app!)
