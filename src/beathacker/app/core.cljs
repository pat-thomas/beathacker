(ns beathacker.app.core
  (:require [goog.dom     :as gdom]
            [om.core      :as om   :include-macros true]
            [om.core      :as dom  :include-macros true]
            [sablono.core :as html :include-macros true]))

(enable-console-print!)

(def app-state (atom {:initialized false}))

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
