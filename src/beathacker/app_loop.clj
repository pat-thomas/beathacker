(ns beathacker.app-loop
  (:require [overtone.live      :as overtone :refer [apply-by at]]
            [clojure.core.async :as async    :refer [go <! >!]]
            [beathacker.util                 :refer [defchan]]))

(def kick (overtone/sample (overtone/freesound-path 2086)))

(defchan event-channel)

(def metro (overtone/metronome 120))

(def debug-atom (atom [])) ;; make sure to delete this at some point

(defn fire-event!
  [msg]
  (go (>! event-channel msg)))

(defn handler
  [channel]
  (kick)
  (println "in handler")
  (go (when-let [val-from-channel (<! channel)]
        (swap! debug-atom conj val-from-channel)))
  :implement-me)

(defn run-app-loop
  [nome cb channel]
  (let [beat (nome)]
    (at (nome beat)
        (do (println "calling callback...")
            (cb channel)))
    (apply-by (nome (inc beat)) #'run-app-loop nome handler channel [])))

(comment
  (run-app-loop metro
                handler
                event-channel)
  (do (fire-event! :test-event-2)
      @debug-atom)
  )
