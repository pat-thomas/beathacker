(ns beathacker.app.core
  (:require [goog.dom                      :as gdom]
            [beathacker.app.state          :as state]
            [beathacker.app.components.app :as app]
            [om.core                       :as om  :include-macros true])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(enable-console-print!)

(defn initialize-app!
  []
  (let [target (gdom/getElement "app-container")]
    (om/root app/beathacker-app
             state/app-state
             {:target target})))

(initialize-app!)
