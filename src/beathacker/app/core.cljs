(ns beathacker.app.core
  (:require [goog.dom                             :as gdom]
            [beathacker.app.api                   :as api]
            [beathacker.app.state                 :as state]
            [beathacker.app.helpers               :as helpers]
            [beathacker.app.components.rhythm-box :as rhythm-box]
            [om.core                              :as om   :include-macros true]
            [om.dom                               :as dom  :include-macros true])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(enable-console-print!)

(defcomponent beathacker-app
  (render
   (dom/div
    #js {:id "om-app"}
    (dom/button #js {:onClick api/handle-click}
                "Hello")
    (dom/button #js {:onClick api/handle-click-again}
                "Hello again")
    (om/build rhythm-box/rhythm-box-container (get-in data [:components :rhythm-box]))
    (dom/div (if (:initialized data)
               "Initialized"
               "Not initialized")))))

(defn initialize-app!
  []
  (let [target (gdom/getElement "app-container")]
    (om/root beathacker-app
             state/app-state
             {:target target})))

(initialize-app!)
