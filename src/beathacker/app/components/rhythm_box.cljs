(ns beathacker.app.components.rhythm-box
  (:require [om.dom  :as dom :include-macros true]
            [om.core :as om   :include-macros true]
            [beathacker.app.helpers :as helpers])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(defn build-option
  [value]
  (dom/option #js {:value value} value))

(defcomponent rhythm-box
  (render (dom/div #js {:className "rhythm-box"} (:number opts))))

(defcomponent rhythm-box-column
  (render
   (dom/div #js {:className "rhythm-box-column"}
            (om/build rhythm-box data))))

(defcomponent rhythm-box-row
  (render
   (dom/div #js {:className "rhythm-box-row"}
            (apply dom/div nil
                   (mapv #(om/build rhythm-box-column data)
                         (range (get-in data [:selected-option :columns])))))))

(defcomponent rhythm-box-container
  (render
   (dom/div
    #js {:id "rhythm-box-container"}
    (dom/div nil
             "Rows"
            (dom/select #js {:onChange (fn [e]
                                          (om/update! data [:selected-option :rows] (helpers/evt->value e)))}
                         (mapv build-option (map str (range 1 9)))))
    (dom/div nil
             "Columns"
             (dom/select #js {:onChange (fn [e]
                                          (om/update! data [:selected-option :columns] (helpers/evt->value e)))}
                         (mapv build-option (map str (range 1 9)))))
    (apply dom/div nil
           (mapv #(om/build rhythm-box-row data)
                 (range (get-in data [:selected-option :rows])))))))



