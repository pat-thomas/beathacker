(ns beathacker.app.core
  (:require [goog.dom           :as gdom]
            [goog.events        :as events]
            [om.core            :as om   :include-macros true]
            [om.dom             :as dom  :include-macros true]
            [sablono.core       :as html :include-macros true]
            [beathacker.app.xhr :refer [do-xhr]])
  (:require-macros [om-utils.core :refer [defcomponent]])
  (:import [goog.net XhrIo]
           goog.net.EventType
           [goog.events EventType]))

(enable-console-print!)

(def app-state (atom {:initialized false}))

(defn handle-click
  [e]
  (do-xhr {:method      :post
           :url         "core"
           :data        {:handler-name :sin
                         :data         {:type       :note
                                        :sound-type :sin}}
           :on-complete (fn [res]
                          (println :wat)
                          (println res))}))

(defcomponent beathacker-app
  (render
   (dom/div
    #js {:id "om-app"}
    (dom/button #js {:onClick handle-click}
                "Hello")
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
