(ns beathacker.app-loop
  (:require [beathacker.handlers      :as handlers]
            [overtone.live            :as overtone :refer [apply-by at]]
            [clj-utils.maps                        :refer [make-map]]
            [beathacker.app-loop.handlers.registry :refer [trigger-handler]]
            [beathacker.util                       :refer [defchan]]))

(def metro (overtone/metronome 120))
(def event-queue (atom []))

(defn fire-event!
  [handler-name & [data]]
  (let [base-msg (make-map handler-name)
        msg      (if-not data
                   base-msg
                   (assoc base-msg :data data))]
    (swap! event-queue conj (make-map handler-name data))))

(defn handle-event
  [{:keys [data] :as evt}]
  (do (swap! event-queue rest)
      (handlers/handle-sound-event data)
      (println data)))

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
        (do (println ".")
            (cb)))
    (apply-by (nome (inc beat)) #'run-app-loop! nome handler [])))

(comment
  (run-app-loop! metro
                 handler
                 event-channel)
  (fire-event! :kick)
  )
