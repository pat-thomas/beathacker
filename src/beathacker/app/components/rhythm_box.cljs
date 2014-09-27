(ns beathacker.app.components.rhythm-box
  (:require [om.dom                 :as dom :include-macros true]
            [om.core                :as om  :include-macros true]
            [beathacker.app.api     :as api]
            [beathacker.app.helpers :as helpers])
  (:require-macros [om-utils.core :refer [defcomponent]]))

(defn build-option
  [value]
  (dom/option #js {:value value} value))

(defcomponent column-select-list
  (render
   (dom/div nil
            "Columns"
            (dom/select #js {:onChange (fn [e]
                                         (om/update! data [:selected-option :columns] (helpers/evt->value e)))}
                        (mapv build-option (map str (range 1 9)))))))

(defcomponent row-select-list
  (render
   (dom/div nil
            "Rows"
            (dom/select #js {:onChange (fn [e]
                                         (om/update! data [:selected-option :rows] (helpers/evt->value e)))}
                        (mapv build-option (map str (range 1 9)))))))

(defcomponent send-to-server-button
  (render
   (dom/button #js {:id "send-to-server-button"
                    :onClick #(-> data deref :clicked api/send-rhythm-to-server)}
               "Play pattern.")))

(defn classname-for-rhythm-box
  [data opts]
  (let [row-num           (:data-row-num opts)
        column-num        (:data-column-num opts)
        matching-results  (filter (fn [click-data-map]
                                    (and (= row-num    (:row click-data-map))
                                         (= column-num (:column click-data-map))))
                                  (distinct (:clicked data)))]
    (if (get-in data [:clicked [row-num column-num]])
      "rhythm-box clicked"
      "rhythm-box")))

(defcomponent rhythm-box
  (render
   (dom/div #js {:className       (classname-for-rhythm-box data opts)
                 :data-row-num    (:data-row-num opts)
                 :data-column-num (:data-column-num opts)
                 :onClick         (fn [e]
                                    (let [clicked-row    (js/parseInt (helpers/data (.-target e) "row-num"))
                                          clicked-column (js/parseInt (helpers/data (.-target e) "column-num"))]
                                      (om/transact! data [:clicked [clicked-row clicked-column]] #(not %))))})))

(defcomponent rhythm-box-column
  (render
   (dom/div #js {:className "rhythm-box-column"}
            (om/build rhythm-box data {:opts opts}))))

(defcomponent rhythm-box-row
  (render
   (dom/div #js {:className "rhythm-box-row"}
            (apply dom/div nil
                   (mapv #(om/build rhythm-box-column data {:opts (assoc opts :data-column-num %)})
                         (range (get-in data [:selected-option :columns])))))))



(defcomponent rhythm-box-container
  (render
   (dom/div #js {:id "rhythm-box-container"}
            (om/build send-to-server-button data)
            (dom/div #js {:id "select-list-container"}
                     (om/build row-select-list data)
                     (om/build column-select-list data))
            (dom/div #js {:id "rhythm-box-main"}
                     (apply dom/div nil (mapv #(om/build rhythm-box-row data {:opts {:data-row-num %}})
                                              (range (get-in data [:selected-option :rows]))))))))
