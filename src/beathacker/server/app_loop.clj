(ns beathacker.server.app-loop
  (:require [beathacker.server.sounds :as sounds]
            [overtone.live            :as overtone :refer [apply-by at]]
            [clj-utils.maps                        :refer [make-map]]
            [beathacker.server.util                :refer [defchan]]))

(def metro (overtone/metronome 120))
(def event-queue (atom []))

(defn fire-event!
  [sound-type & [data]]
  (let [base-msg (make-map sound-type)
        msg      (if-not data
                   base-msg
                   (assoc base-msg :data data))]
    (swap! event-queue conj msg)))

(defn handle-event
  [{:keys [sound-type data] :as evt}]
  (do (swap! event-queue rest)
      (sounds/handle-sound-event evt)
      (println (if data
                 (format "%s -> %s" sound-type data)
                 (format "%s -> {}" sound-type)))))

(defn drain-event-queue
  [evts]
  (doseq [evt evts]
    (handle-event evt)))

(defn handler
  []
  (do (drain-event-queue @event-queue)
      :not-sure-if-this-has-to-return-anything-meaningful))

(defn run-app-loop!
  [nome cb]
  (let [beat (nome)]
    (at (nome beat)
        (cb))
    (apply-by (nome (inc beat)) #'run-app-loop! nome handler [])))

(comment
  (run-app-loop! metro
                 handler
                 event-channel)
  (fire-event! :kick)
  )
