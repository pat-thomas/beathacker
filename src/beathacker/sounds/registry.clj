(ns beathacker.sounds.registry
  (:require [beathacker.sounds.sounds :as sounds]))

(def sound-dispatch (atom {}))

(defn register-sound-player!
  [sound-type handler]
  (swap! sound-dispatch assoc sound-type handler))

(defn play-sound
  [sound-type & args]
  (when-let [handler (-> sound-dispatch deref sound-type)]
    (apply handler args)))

(register-sound-player! :sin sounds/sin-wave)

(comment
  (play-sound :sin 880)
  )
