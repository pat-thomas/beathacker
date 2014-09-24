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
  (let [event-is-pending? (fn [e]
                            (get-in e [:data :wait]))]
    (when-not (event-is-pending? evt)
      (do (sounds/handle-sound-event evt)
          (println (if data
                     (format "%s -> %s" sound-type data)
                     (format "%s -> {}" sound-type)))))))

(defn preprocess-event-queue
  [evts]
  (let [handle-wait (fn [evt]
                      (if-let [wait (get-in evt [:data :wait])]
                        (if (> wait 0)
                          (update-in evt [:data :wait] dec)
                          (update-in evt [:data] dissoc :wait))
                        evt))]
    (map handle-wait evts)))

(defn postprocess-event-queue
  [evts]
  (let [event-is-pending? (fn [evt]
                            (get-in evt [:data :wait]))]
    (filter event-is-pending? evts)))

(defn drain-event-queue
  [evts]
  (let [preprocessed-event-queue  (preprocess-event-queue evts)
        postprocessed-event-queue (postprocess-event-queue preprocessed-event-queue)]
    (do (doseq [evt preprocessed-event-queue]
          (handle-event evt))
        (reset! event-queue postprocessed-event-queue))))

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
