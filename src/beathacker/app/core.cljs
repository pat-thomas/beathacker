(ns beathacker.app.core
  (:require [goog.dom           :as gdom]
            [goog.events        :as events]
            [om.core            :as om   :include-macros true]
            [om.dom             :as dom  :include-macros true]
            [beathacker.app.api :as api])
  (:require-macros [om-utils.core :refer [defcomponent]])
  (:import [goog.net XhrIo]
           goog.net.EventType
           [goog.events EventType]))

(enable-console-print!)

(def app-state (atom {:initialized false}))

(defcomponent beathacker-app
  (render
   (dom/div
    #js {:id "om-app"}
    (dom/button #js {:onClick api/handle-click}
                "Hello")
    (dom/button #js {:onClick api/handle-click-again}
                "Hello again")
    (dom/div (if (:initialized data)
               "Initialized"
               "Not initialized")))))

(defn initialize-app!
  []
  (let [target (gdom/getElement "app-container")]
    (om/root beathacker-app
             app-state
             {:target target})))

(initialize-app!)
