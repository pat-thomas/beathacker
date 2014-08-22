(ns beathacker.core
  (:require [overtone.live     :as overtone]
            [beathacker.events :as events]))

(defn main
  []
  (events/init!))

(comment
  (main)
  (events/fire-event! :event-fired {:type :note
                                    :val  :c3})
)
