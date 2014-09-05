(ns beathacker.handlers
  (:require [beathacker.sounds :as sounds]))

(defn handle-sound-event
  [{:keys [sound-type args] :as data}]
  (let [sound-type (keyword sound-type)]
    (when sound-type
      (sounds/play-sound sound-type args))))
