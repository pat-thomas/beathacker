(ns beathacker.app.components.app
  (:require [beathacker.app.components.rhythm-box :as rhythm-box]
            [beathacker.app.api                   :as api]
            [om.core                              :as om  :include-macros true]
            [om.dom                               :as dom :include-macros true])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(defcomponent beathacker-app
  (render
   (dom/div
    #js {:id "beathacker-app"}
    (dom/button #js {:onClick api/handle-click}
                "Hello")
    (dom/button #js {:onClick api/handle-click-again}
                "Hello again")
    (om/build rhythm-box/rhythm-box-container (get-in data [:components :rhythm-box])))))
