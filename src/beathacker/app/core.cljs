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

(def app-state
  (atom
   {:initialized false
    :components {:rhythm-box {}}}))

(defn build-option
  [value]
  (dom/option #js {:value value} value))

(defn evt->value
  [evt]
  (.-value (.-target evt)))

(defcomponent rhythm-box
  (render
   (dom/div #js {:className "rhythm-box"} (str "alright... " (:number opts)))))

(defcomponent rhythm-box-container
  (render
   (dom/div
    #js {:id "rhythm-box-container"}
    (dom/select #js {:onChange (fn [e]
                                 (om/update! data [:selected-option] (evt->value e)))}
                (mapv build-option (map str (range 1 9))))
    (when-let [selected-option (:selected-option data)]
      (mapv (fn [n]
              (om/build rhythm-box data {:opts {:number n}}))
            (map inc (range 0 selected-option)))))))

(defcomponent beathacker-app
  (render
   (dom/div
    #js {:id "om-app"}
    (dom/button #js {:onClick api/handle-click}
                "Hello")
    (dom/button #js {:onClick api/handle-click-again}
                "Hello again")
    (om/build rhythm-box-container (get-in data [:components :rhythm-box]))
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
