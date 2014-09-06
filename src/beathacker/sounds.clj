(ns beathacker.sounds
  (:require [overtone.live :as overtone :refer [definst]]
            [memento.core               :refer [defregistry]]))

(defregistry sounds
  {:register-fn-alias register-sound!
   :trigger-fn-alias  trigger-sound!})

(defn play-sound
  [sound-type & args]
  (let [args (if (or (vector? args)
                     (seq? args))
               args
               [args])]
    (if (or (empty? args)
            (every? nil? args))
      (trigger-sound! sound-type)
      (apply trigger-sound! sound-type args))))

(defn handle-sound-event
  [{:keys [sound-type args] :as data}]
  (let [sound-type (keyword sound-type)]
    (when sound-type
      (play-sound sound-type args))))

(def kick (overtone/sample (overtone/freesound-path 2086)))

(definst sin-wave
  [freq 880 attack 0.1 sustain 0.4 release 0.2 vol 0.4]
  (* (overtone/env-gen (overtone/lin attack sustain release) 1 1 0 1 overtone/FREE)
     (overtone/sin-osc freq)
     vol))

(register-sound! :sin  sin-wave)
(register-sound! :kick kick)
