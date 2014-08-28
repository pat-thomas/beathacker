(ns beathacker.app-loop
  (:require [overtone.live      :as overtone]   
            [clojure.core.async :as async :refer [go <! >!]]
            [beathacker.util    :refer [defchan]]))

(defchan event-channel)

(def metro (overtone/metronome 120))

(def debug-atom (atom [])) ;; make sure to delete this at some point

(defn fire-event!
  [msg]
  (go (>! event-channel msg)))

(defn handler
  [channel]
  (println "calling handler")
  (go (when-let [val-from-channel (<! channel)]
        (swap! debug-atom conj val-from-channel)))
  :implement-me)

(defn run-app-loop
  [nome cb channel]
  (let [beat (nome)]
    (overtone/at (nome beat)
                 (cb channel))
    (overtone/apply-by (nome (inc beat)) #'run-app-loop nome [])))

(comment
  (run-app-loop metro
                handler
                event-channel)
  (fire-event! :test-event-2)
  )
