(ns beathacker.handlers
  (:require [beathacker.sounds.registry :as sounds-registry]))

(def event-handler-registry (atom {}))

(defn register-handler!
  [evt-name handler-impl]
  (swap! event-handler-registry assoc evt-name handler-impl))

(register-handler!
 :note
 (fn [{:keys [sound-type args] :as data}]
   (when sound-type
     (sounds-registry/play-sound sound-type args))))

(defn handle-sound-event
  [{:keys [sound-type args] :as data}]
  (let [sound-type (keyword sound-type)]
    (when sound-type
      (sounds-registry/play-sound sound-type args))))

(defn handle-event
  [{:keys [topic data] :as params}]
  (when-let [handler (get @event-handler-registry (keyword (:type data)))]
    (handler data)))

(comment

)
