(ns beathacker.app.components.rhythm-box.reflection
  (:require
   [om.dom                               :as dom :include-macros true]
   [om.core                              :as om  :include-macros true]
   [beathacker.app.components.rhythm-box :as rhythm-box]
   [beathacker.app.helpers               :as helpers])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(defcomponent rhythm-box-reflection-column
  (render
   (dom/div #js {:className "rhythm-box-column"}
            (om/build rhythm-box/rhythm-box data))))

(defcomponent rhythm-box-reflection-row
  (render
   (dom/div #js {:className "rhythm-box-row reflection"}
            (apply dom/div nil
                   (mapv #(om/build rhythm-box-reflection-column data)
                         (range (get-in data [:selected-option :columns])))))))

(defcomponent rhythm-box-reflection-container
  (render
   (dom/div #js {:id "rhythm-box-reflection-container"}
            (dom/div #js {:id "rhythm-box-reflection"}
                     (apply dom/div nil (mapv #(om/build rhythm-box-reflection-row data) (range (get-in data [:selected-option :rows]))))))))


