(ns beathacker.sounds
  (:require [overtone.live :as overtone :refer [definst]]))

(definst sin-wave
  [freq 440 attack 0.01 sustain 0.4 release 0.2 vol 0.4]
  (* (overtone/env-gen (overtone/lin attack sustain release) 1 1 0 1 overtone/FREE)
     (overtone/sin-osc freq)
     vol))

(def sound-dispatch (atom {}))

(defn register-sound-player!
  [sound-type handler]
  (swap! sound-dispatch assoc sound-type handler))

(register-sound-player! :sin sin-wave)

(defn play-sound
  [sound-type & args]
  (when-let [handler (-> sound-dispatch deref sound-type)]
    (apply handler args)))

(comment
  (play-sound :sin 880)
  )
